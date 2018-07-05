package com.smallain.company

import com.smallain.hbase_utils.sink.HbaseSink
import com.smallain.utils.jsonparse.company.CanDoDataType._
import org.apache.spark.sql.SparkSession

/**
  * ODS层数据实时同步方案
  */
object StructuredStreaming {
  def main(args: Array[String]): Unit = {

    val zookeeperPath = "iz2zea86z2leonw09hpjijz:2181,iz2zea86z2leonw09hpjimz:2181,iz2zea86z2leonw09hpjilz:2181,iz2zea86z2leonw09hpjikz:2181"
    val tablePkConfigPath = "hdfs://10.200.48.67:8020/odsStream/table/pkconfig.txt"


    //创建sparkSession入口,也是spark集群入口,切记local线程数一定要大于1,因为一个线程要用来作为监听listener
    val spark = SparkSession
      .builder
      .appName("StructuredNetworkWordCount")
      .master("local[2]")
      .getOrCreate()

    //引用spark必要的隐式转换参数
    import spark.implicits._

    //spark订阅kafka指定的topic信息
    val dataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "iz2zea86z2leonw09hpjijz:9092,iz2zea86z2leonw09hpjimz:9092,iz2zea86z2leonw09hpjilz:9092,iz2zea86z2leonw09hpjikz:9092")
      .option("subscribe", "maxwell")
      .load()

    //获取主键List文件
    val pkList = spark.read.textFile(tablePkConfigPath).collect().toList


    //分析kafka流过来的binlog数据,因为只关心dml语句和ddl语句的binlog信息,所以只取tuple的第二个元素即dml和ddl语句的json数据信息
    //并且只处理"table-alter","insert","update","delete"类型的json数据
    val dataSet = dataFrame.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").as[(String, String)].map(x => x._2).filter(x => jsonDataFilter(x))

    //数据源表配置文件，其中包含数据源表的主键信息

    //通过自定义的sink即HbaseSink将获取到的dml语句和ddl语句的json-binlog信息以append追加的形式输出到hbase中
    val query = dataSet.writeStream.foreach(new HbaseSink(pkList, tablePkConfigPath, zookeeperPath)).outputMode("append").start()

    //启动Structed Streaming结构化Stream流
    query.awaitTermination()


    //TODO 测试 insert  update  delete的各种场景

  }
}
