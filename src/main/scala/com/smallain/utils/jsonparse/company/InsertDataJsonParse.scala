package com.smallain.utils.jsonparse.company

import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse
import com.smallain.company.model.InsertDataModule._

object InsertDataJsonParse {
  def insertDataParse(str: String): Unit = {
    val json = parse(str)
    val alterTableTransJson = json.extract[InsertDatas]

    val datas = alterTableTransJson.data
    val tableName = alterTableTransJson.table
    //datas.map(x => x.head).map(x => x._1).getOrElse("column")
  }
}
