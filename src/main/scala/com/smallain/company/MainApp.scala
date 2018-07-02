package com.smallain.company

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
//import org.apache.spark.streaming.kafka._

object MainApp {
//  def main(args: Array[String]): Unit = {
//    val sparkConf = new SparkConf().setAppName("odsStream").setMaster("local[2]")
//    val ssc = new StreamingContext(sparkConf, Seconds(5))
//    println("hello,world")
//
//    val topicsSet = Set("maxwell")
//    //val kafkaParams = Map[String, String]("metadata.broker.list" -> "iz2zea86z2leonw09hpjijz:9092,iz2zea86z2leonw09hpjimz:9092,iz2zea86z2leonw09hpjilz:9092,iz2zea86z2leonw09hpjikz:9092")
//    val kafkaParams = Map[String, String]("metadata.broker.list" -> "smallain01.com:9092,smallain02.com:9092,smallain03.com:9092")
//    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
//
//    val ss = messages.map(x=>x._2)
//    ss.print()
//    ssc.start()
//    ssc.awaitTermination()
//
//
//  }
}
