package com.smallain.hbase_utils.dao

import org.scalatest.FunSuite
import com.smallain.hbase_utils.dao.TableDao
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.HTablePool

class TableDaoTest extends FunSuite {

  test("testputTable") {
    val myConf = HBaseConfiguration.create()
    myConf.set("hbase.zookeeper.quorum", "iz2zea86z2leonw09hpjijz:2181,iz2zea86z2leonw09hpjimz:2181,iz2zea86z2leonw09hpjilz:2181,iz2zea86z2leonw09hpjikz:2181")
    val pool = new HTablePool(myConf, 2147483647)
    val td = new TableDao(pool)
    td.putTable("wuyuhang", "000000001", "info", "age", "12")
  }

}
