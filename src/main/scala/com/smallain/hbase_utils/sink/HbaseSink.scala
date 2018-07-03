package com.smallain.hbase_utils.sink

import java.util.Date

import com.smallain.hbase_utils.dao.TableDao
import com.smallain.utils.jsonparse.company.InsertDataJsonParse._
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.HTablePool
import org.apache.spark.sql.ForeachWriter


/**
  * 自定义ForeachWriter,因为Structed Streaming的built-in output sinks只有以下几种：
  *
  * 1.File sink - Stores the output to a directory.
  * 2.Kafka sink - Stores the output to one or more topics in Kafka.
  * 3.Foreach sink - Runs arbitrary computation on the records in the output. See later in the section for more details.
  * 4.Console sink (for debugging) - Prints the output to the console/stdout every time there is a trigger. Both, Append and
  * Complete output modes, are supported. This should be used for debugging purposes on low data volumes as the entire output is
  * collected and stored in the driver’s memory after every trigger.
  * 5.Memory sink (for debugging) - The output is stored in memory as an in-memory table. Both, Append and Complete output modes, are
  *   supported. This should be used for debugging purposes on low data volumes as the entire output is collected and stored in the
  * driver’s memory. Hence, use it with caution.
  *
  * 所以针对于写入Hbase需要通过Foreach sink实现ForeachWriter
  */
class HbaseSink() extends ForeachWriter[String] {

  //初始化HTablePool连接池
  var pool = new HTablePool with Serializable


  /**
    * ForeachWriter抽象类默认需要实现的open函数,初始化HTablePool连接池,因为spark是运行在内存中的计算模型,
    * 所以spark引用的所有外部对象都要被序列化Serializable。
    *
    * @param partitionId 默认参数
    * @param version     默认参数
    * @return 返回boolean
    */
  override def open(partitionId: Long, version: Long): Boolean = {
    val myConf = HBaseConfiguration.create()
    myConf.set("hbase.zookeeper.quorum", "iz2zea86z2leonw09hpjijz:2181,iz2zea86z2leonw09hpjimz:2181,iz2zea86z2leonw09hpjilz:2181,iz2zea86z2leonw09hpjikz:2181")
    pool = new HTablePool(myConf, 2147483647) with Serializable
    true
  }


  /**
    * ForeachWriter抽象类默认需要实现的process函数,函数中实现具体数据处理的逻辑，例如写入数据到hbase。
    *
    * @param value dataset中包裹的数据值，也就是需要真正处理的位于rdd中的数据值。
    */
  override def process(value: String): Unit = {
    val td = new TableDao(pool)
    val now = new Date()

    //生产当前时间戳
    def getCurrent_time: Long = {
      val a = now.getTime
      var str = a + ""
      str.substring(0, 10).toLong
    }

    val timestamp = getCurrent_time


    //将dataset中的数据值,根据模式匹配去处理不同类型的json数据,包括insert,update,delete等,返回的dataset包含处理后的json字符串结果
    val resp = value match {
      case x if x.contains("insert") => insertDataParse(x)
    }

    //然后将处理后的结果通过hbase写入函数写入数据库,注意其中列簇因为无法明确获取，因为并不知道列簇所位于的具体位置,所以此处的列簇指定为“info”,因为binlog中并不会有列簇信息,
    //只有解析目标表hbase去获取列簇信息,但是binlog中的列无法动态识别如何与目标hbase表的列簇对应
    resp.foreach(column => td.putTable(column._1, timestamp.toString + column._1, "info", column._2, column._3))

  }

  /**
    * ForeachWriter抽象类默认需要实现的close函数,函数中实现处理完数据之后关闭数据连接。
    *
    * @param errorOrNull
    */
  override def close(errorOrNull: Throwable): Unit = {
    pool.close()
  }
}
