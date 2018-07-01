package com.smallain.company

import org.apache.spark.sql.SparkSession

object StructuredStreaming {
	def main(args: Array[String]): Unit = {
		
		val spark = SparkSession
			.builder
			.appName("StructuredNetworkWordCount")
			.master("local[2]")
			.getOrCreate()
		
		import spark.implicits._
		
		//		val df = spark
		//			.readStream
		//			.format("kafka")
		//			.option("kafka.bootstrap.servers", "iz2zea86z2leonw09hpjijz:9092,iz2zea86z2leonw09hpjimz:9092,iz2zea86z2leonw09hpjilz:9092,iz2zea86z2leonw09hpjikz:9092")
		//			.option("subscribe", "maxwell")
		//			.load()
		
		val df = spark
			.readStream
			.format("kafka")
			.option("kafka.bootstrap.servers", "192.168.31.101:9092,192.168.31.102:9092,192.168.31.103:9092")
			.option("subscribe", "maxwell")
			.load()
		
		
		
		val ds = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
			.as[(String, String)]
		
		val dss = ds.map(x => x._2)
		
		//    val query = dss.writeStream
		//      .format("csv")
		//      .outputMode("append")
		//      .option("checkpointLocation", "C:\\work_Some\\spark_stream_checkpoint")
		//      .option("path", "C:\\work_Some\\file_dir")
		//      .start()
		
		val query = dss.writeStream
			.format("console")
			.outputMode("append")
			.start()
		
		query.awaitTermination()
	}
}
