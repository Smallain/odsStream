package com.smallain.utils.jsonparse.company

import org.scalatest.FunSuite
import com.smallain.utils.jsonparse.company.UpdateDataJsonParse._

class updateDataJsonParseTest extends FunSuite {

  test("测试更新json UpdateDataParse: update json语句解析函数，输入参数为主键配置表信息，和待解析处理的json字符串，输出返回(根据联合主键拼接的字符串，表名，字段名，字段值)") {
    val pklist = List("wuyuhang=id,sex", "users=name,city")
    val str = "{\"database\":\"wuyuhang\",\"table\":\"users\",\"type\":\"update\",\"ts\":1530860956,\"xid\":70543,\"commit\":true,\"data\":{\"id\":2222,\"name\":\"sdzfsdfsdf\",\"city\":\"shenyang\"},\"old\":{\"city\":\"liaoyuan\"}}"

    val udp = updateDataParse(pklist, str)

    val rep = udp.mkString(",")

    assert(rep === "(sdzfsdfsdfshenyang,users,id,2222),(sdzfsdfsdfshenyang,users,name,sdzfsdfsdf),(sdzfsdfsdfshenyang,users,city,shenyang)")
  }

}
