package com.smallain.utils.jsonparse.company

import org.scalatest.FunSuite

class DataBaseJsonParseTest extends FunSuite {
	
	test("testAlterJsonParse") {
		val str = "{\"type\":\"table-alter\",\"database\":\"wuyuhang\",\"table\":\"user_test\",\"old\":{\"database\":\"wuyuhang\",\"charset\":\"utf8\",\"table\":\"user_test\",\"primary-key\":[],\"columns\":[{\"type\":\"varchar\",\"name\":\"name\",\"charset\":\"utf8\"},{\"type\":\"int\",\"name\":\"age\",\"signed\":true},{\"type\":\"int\",\"name\":\"id\",\"signed\":true}]},\"def\":{\"database\":\"wuyuhang\",\"charset\":\"utf8\",\"table\":\"user_test\",\"primary-key\":[],\"columns\":[{\"type\":\"varchar\",\"name\":\"name\",\"charset\":\"utf8\"},{\"type\":\"int\",\"name\":\"age\",\"signed\":true},{\"type\":\"int\",\"name\":\"id\",\"signed\":true},{\"type\":\"varchar\",\"name\":\"comment\",\"charset\":\"utf8\"}]},\"ts\":1530437707000,\"sql\":\"/* ApplicationName=DataGrip 2017.2.2 */ alter table user_test add COLUMN comment VARCHAR(32)\"}"
		val str_rep = DataBaseJsonParse.alterJsonParse(str)
		assert(str_rep === "table-alter")
	}
	
}
