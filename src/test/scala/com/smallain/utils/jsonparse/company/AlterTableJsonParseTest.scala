package com.smallain.utils.jsonparse.company

import org.scalatest.FunSuite

class AlterTableJsonParseTest extends FunSuite {
	
	test("testAlterJsonParse") {
		val str = "{\"type\":\"table-alter\",\"database\":\"wuyuhang\",\"table\":\"user_test\",\"old\":{\"database\":\"wuyuhang\",\"charset\":\"utf8\",\"table\":\"user_test\",\"primary-key\":[],\"columns\":[{\"type\":\"varchar\",\"name\":\"name\",\"charset\":\"utf8\"},{\"type\":\"int\",\"name\":\"age\",\"signed\":true},{\"type\":\"int\",\"name\":\"id\",\"signed\":true}]},\"def\":{\"database\":\"wuyuhang\",\"charset\":\"utf8\",\"table\":\"user_test\",\"primary-key\":[],\"columns\":[{\"type\":\"varchar\",\"name\":\"name\",\"charset\":\"utf8\"},{\"type\":\"int\",\"name\":\"age\",\"signed\":true},{\"type\":\"int\",\"name\":\"id\",\"signed\":true},{\"type\":\"varchar\",\"name\":\"comment\",\"charset\":\"utf8\"}]},\"ts\":1530437707000,\"sql\":\"/* ApplicationName=DataGrip 2017.2.2 */ alter table user_test add COLUMN comment VARCHAR(32)\"}"
		val str_rep = AlterTableJsonParse.alterJsonParse(str)
		//		assert(str_rep.map(x=>x._2).sorted.mkString(",") === "age,id,name")
		assert(str_rep === "2018-07-01 17:35:07")
	}
	
	test("alterJsonParseOldColumns") {
		val str = "{\"type\":\"table-alter\",\"database\":\"wuyuhang\",\"table\":\"user_test\",\"old\":{\"database\":\"wuyuhang\",\"charset\":\"utf8\",\"table\":\"user_test\",\"primary-key\":[],\"columns\":[{\"type\":\"varchar\",\"name\":\"name\",\"charset\":\"utf8\"},{\"type\":\"int\",\"name\":\"age\",\"signed\":true},{\"type\":\"int\",\"name\":\"id\",\"signed\":true}]},\"def\":{\"database\":\"wuyuhang\",\"charset\":\"utf8\",\"table\":\"user_test\",\"primary-key\":[],\"columns\":[{\"type\":\"varchar\",\"name\":\"name\",\"charset\":\"utf8\"},{\"type\":\"int\",\"name\":\"age\",\"signed\":true},{\"type\":\"int\",\"name\":\"id\",\"signed\":true},{\"type\":\"varchar\",\"name\":\"comment\",\"charset\":\"utf8\"}]},\"ts\":1530437707000,\"sql\":\"/* ApplicationName=DataGrip 2017.2.2 */ alter table user_test add COLUMN comment VARCHAR(32)\"}"
		val str_rep = AlterTableJsonParse.alterJsonParseOldColumns(str)
		assert(str_rep.map(x => x._2).sorted.mkString(",") === "age,id,name")
		
	}
	
	test("alterJsonParseNewColumns") {
		val str = "{\"type\":\"table-alter\",\"database\":\"wuyuhang\",\"table\":\"user_test\",\"old\":{\"database\":\"wuyuhang\",\"charset\":\"utf8\",\"table\":\"user_test\",\"primary-key\":[],\"columns\":[{\"type\":\"varchar\",\"name\":\"name\",\"charset\":\"utf8\"},{\"type\":\"int\",\"name\":\"age\",\"signed\":true},{\"type\":\"int\",\"name\":\"id\",\"signed\":true}]},\"def\":{\"database\":\"wuyuhang\",\"charset\":\"utf8\",\"table\":\"user_test\",\"primary-key\":[],\"columns\":[{\"type\":\"varchar\",\"name\":\"name\",\"charset\":\"utf8\"},{\"type\":\"int\",\"name\":\"age\",\"signed\":true},{\"type\":\"int\",\"name\":\"id\",\"signed\":true},{\"type\":\"varchar\",\"name\":\"comment\",\"charset\":\"utf8\"}]},\"ts\":1530437707000,\"sql\":\"/* ApplicationName=DataGrip 2017.2.2 */ alter table user_test add COLUMN comment VARCHAR(32)\"}"
		val str_rep = AlterTableJsonParse.alterJsonParseNewColumns(str)
		assert(str_rep.map(x => x._2).sorted.mkString(",") === "age,comment,id,name")
		assert(str_rep.map(x => x._3).sorted.mkString(",") === "user_test,user_test,user_test,user_test")
		
	}
	
}
