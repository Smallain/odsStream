package com.smallain.utils.jsonparse.company

import com.smallain.hbase_utils.util.GetSourceTablePK.getPK
import org.json4s
import org.json4s.DefaultFormats
import org.json4s.JsonAST.{JObject, JString}
import org.json4s.JsonAST
import org.json4s.jackson.JsonMethods.parse

object UpdateDataJsonParse {


  /**
    * binlog解析的update元信息处理函数
    *
    * 因为解析的binlog信息如{"data":{"name":"guolei","age":23,"id":2}这里希望
    * 自动获取任意的column字段数据比如自动获取字段信息有两个name和age如果之后再包含
    * city也会自动解析。为什么不用json.extract[DataBaseAlterJson]去解析，因为
    * case class定义直接固定字段，无法做到动态
    *
    * @param pkList 源表主键信息文件中所有表主键的list信息
    * @param str    传入待待解析待json字符串(此处对应处理binlog解析的insert元信息)
    * @return 返回tuple  主键值,表名，字段与数据值的元组信息
    */
  def updateDataParse(pkList: List[String], str: String): List[(String, String, String, String)] = {

    implicit val formats: DefaultFormats.type = DefaultFormats

    val json = parse(str)

    //获取table表名
    val tableData = json match {
      case JObject(x) => x match {
        case List(_, y, _, _, _, _, _, _) => y
        case _ => Tuple2("", JString(""))
      }
      case _ => Tuple2("", JString(""))
    }
    val tableName = tableData._2.extract[String]

    //以下逻辑为了匹配到{"data":{"name":"guolei","age":23,"id":2}类型的数据，包括最终转化为tuple
    val columnData = json match {
      case JObject(x) => x match {
        case List(_, _, _, _, _, _, y, _) => y._2 match {
          case JObject(z) => z.map(w => (w._1, w._2))
          case _ => List.empty
        }
        case _ => List.empty
      }
      case _ => List.empty
    }

    //获取外部定义的源表数据的主键
    val primaryKeyList = getPK(pkList, tableName).flatMap(_.split(","))

    /**
      * 将获取到的联合主键列表拼接成一个大的字符串，然后应用于md5加密后生成离散量的数据，
      * 这样既能保证数据的离散型也能保证通过相同加密后数据能够以相同的方式访问到指定数据
      *
      * @param pks 联合主键List
      * @return 拼接的联合主键string
      */
    def unionPK(pks: List[String]): String = pks match {
      case head :: tail =>
        val mapcv = columnData.toMap
        val mapscv = mapcv.map(x => (x._1, x._2.extract[String]))
        mapscv.getOrElse(head, "") + unionPK(tail)
      case _ => ""
    }

    //拼装成用于统一加密的主键字符串
    val pk = unionPK(primaryKeyList)

    //处理成List[(String,String,String)] 数据表名--数据字段名--数据字段值
    columnData.map(x => (pk, tableName, x._1, x._2.extract[String]))
  }
}
