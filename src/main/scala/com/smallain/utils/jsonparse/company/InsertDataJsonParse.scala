package com.smallain.utils.jsonparse.company


import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.DefaultFormats

object InsertDataJsonParse {

  /**
    * binlog解析的insert元信息处理函数
    *
    * 因为解析的binlog信息如{"data":{"name":"guolei","age":23,"id":2}这里希望
    * 自动获取任意的column字段数据比如自动获取字段信息有两个name和age如果之后再包含
    * city也会自动解析。为什么不用json.extract[DataBaseAlterJson]去解析，因为
    * case class定义直接固定字段，无法做到动态
    *
    * @param str 传入待待解析待json字符串(此处对应处理binlog解析的insert元信息)
    * @return 返回tuple  表明，字段与数据值的元组信息
    */
  def insertDataParse(str: String): List[(String, String, String)] = {

    implicit val formats: DefaultFormats.type = DefaultFormats

    val json = parse(str)

    //获取table表名
    val tableData = json match {
      case JObject(x) => x match {
        case List(_, y, _, _, _, _, _) => y
      }
    }
    val tableName = tableData._2.extract[String]


    //TODO row_key行键要根据表名去查找（表名--主键 关系对照表）然后确认主键，对主键加密后写入到hbase中作为rowkey

    //以下逻辑为了匹配到{"data":{"name":"guolei","age":23,"id":2}类型的数据，包括最终转化为tuple
    //添加sorted根据字典排序,这样在读入column-->value键值对时,可以做到相对有序
    val columnData = json match {
      case JObject(x) => x match {
        case List(_, _, _, _, _, _, y) => y._2 match {
          case JObject(z) => z.map(w => (w._1, w._2))
        }
      }
    }
    //处理成List[(String,String,String)] 数据表名--数据字段名--数据字段值
    columnData.map(x => (tableName, x._1, x._2.extract[String]))




    //以下是处理成(表名+列数据的json数据信息)
    //    val columnData: JsonAST.JValue = json match {
    //      case JObject(x) => x match {
    //        case List(_, _, _, _, _, _, y) => y._2
    //      }
    //    }
    //
    //    val columns = compact(columnData)
    //    (tableName,columns)

  }
}
