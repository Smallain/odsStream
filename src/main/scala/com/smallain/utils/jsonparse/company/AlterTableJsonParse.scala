package com.smallain.utils.jsonparse.company

import com.smallain.utils.TransLongTimeStamp._
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse
import com.smallain.company.model.AlterTableModel._

object AlterTableJsonParse {
  implicit val formats = DefaultFormats

  def alterJsonParse(str: String): String = {
    val json = parse(str)
    val alterTableTransJson = json.extract[DataBaseAlterJson]

    val database = alterTableTransJson.database.getOrElse("Empty database Data")
    val evevt_type = alterTableTransJson.`type`.getOrElse("Empty type Data")
    val table = alterTableTransJson.table.getOrElse("Empty table Data")
    val timestamp = transLongTimeStampToString(alterTableTransJson.ts.getOrElse("Empty timestamp Data"))
    val sql = alterTableTransJson.sql.getOrElse("Empty sql Data")
    timestamp
  }


  //		val oldColumns = alterTableTransJson match {
  //			case DataBaseAlterJson(_, _, _, old, _, _, _) => old.flatMap(x => x match {
  //				case DataBaseAlterJsonOld(_, _, _, _, columns) => columns.map {
  //					case list: List[DataBaseAlterJsonOldColumn] => list.map(x =>
  //						(x.`type`.getOrElse("Empty oldColumnType Data"), x.name.getOrElse("Empty oldColumnName Data")))
  //				}
  //			})
  //		}

  def alterJsonParseOldColumns(str: String): List[(String, String)] = {
    val json = parse(str)
    val alterTableTransJson = json.extract[DataBaseAlterJson]

    val oldColumns = alterTableTransJson.old
    val column = oldColumns.flatMap(x => x.columns)
    column.map(x => {
      x.map(y => {
        (y.`type`.getOrElse("Empty oldColumnType Data"),
          y.name.getOrElse("Empty oldColumnName Data"))
      })
    }).getOrElse(List())

  }

  def alterJsonParseNewColumns(str: String): List[(String, String,String)] = {
    val json = parse(str)
    val alterTableTransJson = json.extract[DataBaseAlterJson]

    val newColumns = alterTableTransJson.`def`
    val tableName = newColumns.flatMap(x => x.table)
    val column = newColumns.flatMap(x => x.columns)
    column.map(x => {
      x.map(y => {
        (y.`type`.getOrElse("Empty newColumnType Data"),
          y.name.getOrElse("Empty newColumnName Data"),
          tableName.getOrElse("Empty tableName Data"))
      })
    }).getOrElse(List())
  }
}
