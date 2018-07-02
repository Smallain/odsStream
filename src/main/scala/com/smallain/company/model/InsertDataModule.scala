package com.smallain.company.model

object InsertDataModule {

  case class InsertDatas(
                          database: Option[String]
                          , table: Option[String]
                          , `type`: Option[String]
                          , ts: Option[String]
                          , xid: Option[String]
                          , commit: Option[String]
                          , data: List[Map[String,String]])

  case class InsertDatasItem(list:Int)

}
