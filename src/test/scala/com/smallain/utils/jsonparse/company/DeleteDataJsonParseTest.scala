package com.smallain.utils.jsonparse.company

import com.smallain.utils.jsonparse.company.DeleteDataJsonParse._
import org.scalatest.FunSuite

class DeleteDataJsonParseTest extends FunSuite {

  test("测试删除json DeleteDataParse: delete json语句解析函数，输入参数为主键配置表信息，和待解析处理的json字符串，输出返回(根据联合主键拼接的字符串，表名，字段名，字段值)") {
    val pklist = List("wuyuhang=id,sex", "users=name,city")
    val str = "{\"database\":\"wuyuhang\",\"table\":\"users\",\"type\":\"delete\",\"ts\":1530864264,\"xid\":71406,\"commit\":true,\"data\":{\"id\":55556666,\"name\":\"wuyuhang\",\"city\":\"beijing\"}}"

    val udp = deleteDataParse(pklist, str)

    val rep = udp.mkString(",")

    assert(rep === "(wuyuhangbeijing,users,id,55556666),(wuyuhangbeijing,users,name,wuyuhang),(wuyuhangbeijing,users,city,beijing)")
  }

}
