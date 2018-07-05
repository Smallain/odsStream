package com.smallain.hbase_utils.util

import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite
import com.smallain.hbase_utils.util.GetSourceTablePK._

class GetSourceTablePKTest extends FunSuite {

  test("testGetPK") {
    val pks = List("users=name,city", "wuyuhang=id,sex")
    val ss = getPK(pks, "users").toString()
    assert(ss === "users=name,city")
  }

}
