package com.smallain.utils.jsonparse.company

import org.scalatest.FunSuite
import com.smallain.utils.jsonparse.company.InsertDataJsonParse._

class InsertDataJsonParseTest extends FunSuite {

  test("testInsertDataParse") {
    val str = "{\"database\":\"wuyuhang\",\"table\":\"wuyuhang_test\",\"type\":\"insert\",\"ts\":1530516170,\"xid\":668,\"commit\":true,\"data\":{\"name\":\"a\",\"age\":\"23\",\"city\":\"c\"}}"
    val rep = insertDataParse(str)
    assert(rep===("name","a"))
  }

}
