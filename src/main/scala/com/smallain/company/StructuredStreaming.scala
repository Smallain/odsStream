package com.smallain.company

import com.smallain.hbase_utils.sink.HbaseSink
import com.smallain.utils.jsonparse.company.CanDoDataType._
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

/**
	* ODS层数据实时同步方案
	*/
object StructuredStreaming {
	def main(args: Array[String]): Unit = {
		
		//val zookeeperPath = "iz2zea86z2leonw09hpjijz:2181,iz2zea86z2leonw09hpjimz:2181,iz2zea86z2leonw09hpjilz:2181,iz2zea86z2leonw09hpjikz:2181"
		//val tablePkConfigPath = "hdfs://10.200.48.67:8020/odsStream/table/pkconfig.txt"
		
		val zookeeperPath = "192.168.31.101:2181,192.168.31.102:2181,192.168.31.103:2181"
		val tablePkConfigPath = "hdfs://192.168.31.102:8020/odsStream/table/pkconfig.txt"
		
		//创建sparkSession入口,也是spark集群入口,切记local线程数一定要大于1,因为一个线程要用来作为监听listener
		val spark = SparkSession
			.builder
			.appName("StructuredNetworkWordCount")
			.master("local[2]")
			.getOrCreate()
		
		//引用spark必要的隐式转换参数
		import spark.implicits._
		//iz2zea86z2leonw09hpjijz:9092,iz2zea86z2leonw09hpjimz:9092,iz2zea86z2leonw09hpjilz:9092,iz2zea86z2leonw09hpjikz:9092
		//spark订阅kafka指定的topic信息
		val dataFrame = spark
			.readStream
			.format("kafka")
			.option("kafka.bootstrap.servers", "192.168.31.101:9092,192.168.31.102:9092,192.168.31.103:9092")
			.option("subscribe", "maxwell")
			.option("StorageLevel", "MEMORY_AND_DISK_2")
			.option("failOnDataLoss", "true")
			.load()
		
		//读取的kafka 数据的存储级别，为了应对容错性，将数据源存储在内存和硬盘中，存储在硬盘中是为了在work崩溃时，重启work恢复数据处理。
		//dataFrame.persist(StorageLevel.MEMORY_AND_DISK_2)
		//获取主键List文件
		val pkList = spark.read.textFile(tablePkConfigPath).collect().toList
		
		
		//分析kafka流过来的binlog数据,因为只关心dml语句和ddl语句的binlog信息,所以只取tuple的第二个元素即dml和ddl语句的json数据信息
		//并且只处理"table-alter","insert","update","delete"类型的json数据
		val dataSet = dataFrame.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").as[(String, String)].map(x => x._2).filter(x => jsonDataFilter(x))
		
		//数据源表配置文件，其中包含数据源表的主键信息
		
		//通过自定义的sink即HbaseSink将获取到的dml语句和ddl语句的json-binlog信息以append追加的形式输出到hbase中
		//设置checkpoint保证driver崩溃后在重启时数据不会丢失
		//所以保持容错性：
		//1.worker 的容错性--设置存储级别为dataFrame.persist(StorageLevel.MEMORY_AND_DISK_2)
		//2.driver 的容错性--设置检查点（基于hdfs）option("checkpointLocation", "hdfs://192.168.31.102:8020/odsStream/checkpoint")
		val query = dataSet.writeStream
			.foreach(new HbaseSink(pkList, tablePkConfigPath, zookeeperPath))
			.outputMode("append")
			.option("checkpointLocation", "hdfs://192.168.31.102:8020/odsStream/checkpoint")
			.start()
		
		//启动Structed Streaming结构化Stream流
		query.awaitTermination()
		
		
		//TODO 测试 insert  update  delete的各种场景
		
	}
}
