package com.smallain.hbase_utils.sink

import org.scalatest.FunSuite
import com.smallain.hbase_utils.sink.HbaseSink

class HbaseSinkTest extends FunSuite {

  test("testProcess") {
    val str = "{\"database\":\"wuyuhang\",\"table\":\"users\",\"type\":\"insert\",\"ts\":1530847707,\"xid\":67753,\"commit\":true,\"data\":{\"id\":888,\"name\":\"guolei\",\"city\":\"liaoyuan\"}}"

    val hs = new HbaseSink(List("users=name,city"), "hdfs://10.200.48.67:8020/odsStream/table/pkconfig.txt", "iz2zea86z2leonw09hpjijz:2181,iz2zea86z2leonw09hpjimz:2181,iz2zea86z2leonw09hpjilz:2181,iz2zea86z2leonw09hpjikz:2181")
    hs.process(str)

  }

}
