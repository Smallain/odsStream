package com.smallain.hbase_utils.util

import java.util.{List => JList}
import scala.collection.immutable.List
import org.apache.spark.sql.SparkSession

object GetSourceTablePK {


  /**
    * 获取联合主键的的定义字符串，例如如果主键定义文件的主键定义为user=name,city那么需要获取的就是字符串："name,city"
    *
    * @param pkList    主键配置文件中定义的每一行，形式如 表名=联合主键1,联合主键2   users=name,city
    * @param tableName hbase数据表名
    * @return 返回结果就是获取联合主键的list即List(name,city)
    */

  def getPK(pkList: List[String], tableName: String): List[String] = pkList match {
    case head :: tail => {
      if (head.contains(tableName)) {
        val str = head.split("=").tail.toList
        str
      } else {
        getPK(tail, tableName)
      }
    }
    case _ => List()
  }
}
