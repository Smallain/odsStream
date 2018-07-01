package com.smallain.company.model

object RdbmsModel {

	//关系型数据库AlterJson格式定义(大类)
	case class DataBaseAlterJson(
																`type`: Option[String]
																, database: Option[String]
																, table: Option[String]
																, old: Option[DataBaseAlterJsonOld]
																, `def`: Option[DataBaseAlterJsonDef]
																, ts: Option[String]
																, sql: Option[String]
															)


	//关系型数据库AlterJson格式定义(old原始数据结构定义)
	case class DataBaseAlterJsonOld(
																	 database: Option[String]
																	 , charset: Option[String]
																	 , table: Option[String]
																	 , primary_key: Option[List[String]]
																	 , columns: Option[List[DataBaseAlterJsonOldColumn]]
																 )


	//关系型数据库AlterJson格式定义(old原始数据结构--数据字段定义)
	case class DataBaseAlterJsonOldColumn(
																				 `type`: Option[String]
																				 , name: Option[String]
																				 , charset: Option[String]
																			 )


	//关系型数据库AlterJson格式定义(def新数据结构定义结构定义)
	case class DataBaseAlterJsonDef(
																	 database: Option[String]
																	 , charset: Option[String]
																	 , table: Option[String]
																	 , primary_key: Option[List[String]]
																	 , columns: Option[List[DataBaseAlterJsonDefColumn]]
																 )


	//关系型数据库AlterJson格式定义(def新数据结构--数据字段定义)
	case class DataBaseAlterJsonDefColumn(
																				 `type`: Option[String]
																				 , name: Option[String]
																				 , charset: Option[String]
																			 )


}
		


