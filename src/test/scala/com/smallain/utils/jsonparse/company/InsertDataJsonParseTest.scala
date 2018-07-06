package com.smallain.utils.jsonparse.company

import org.scalatest.FunSuite
import com.smallain.utils.jsonparse.company.InsertDataJsonParse._
import org.apache.spark.sql.SparkSession
import com.smallain.utils.jsonparse.company.InsertDataJsonParse._

class InsertDataJsonParseTest extends FunSuite {

  test("测试新增json InsertDataParse: 识别insert json转换函数,获取预期的转换结果") {
    val pklist = List("users=name,city", "wuyuhang=id,sex")
    val str = "{\"database\":\"wuyuhang\",\"table\":\"users\",\"type\":\"insert\",\"ts\":1530516170,\"xid\":668,\"commit\":true,\"data\":{\"name\":\"wuyuhang\",\"id\":\"19760624\",\"city\":\"liaoyuan\"}}"
    val rep = insertDataParse(pklist, str).head._1
    assert(rep === "wuyuhangliaoyuan")

  }

}
