package com.smallain.utils.jsonparse.company

import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse
import com.smallain.company.model.RdbmsModel._

object DataBaseJsonParse {
	implicit val formats = DefaultFormats
	
	def alterJsonParse(str: String): String = {
		//获取文件内容并转换为json格式
		val json = parse(str)
		//抽取问题(包括子问题结构)
		val transjson = json.extract[DataBaseAlterJson]
		transjson.`type`.getOrElse("Empty Data")
	}
}
