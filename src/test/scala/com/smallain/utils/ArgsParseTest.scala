package com.smallain.utils

import org.scalatest.FunSuite
import com.smallain.utils.ArgsParse._

class ArgsParseTest extends FunSuite {

  test("testGetArgsMap 测试应该返回参数对应的参数列表") {
    val argsMap = getArgsMap(Array("--zookeeper", "iz2zea86z2leonw09hpjijz:2181,iz2zea86z2leonw09hpjimz:2181,iz2zea86z2leonw09hpjilz:2181,iz2zea86z2leonw09hpjikz:2181", "--table-pk-config", "hdfs://10.200.48.67:8020/odsStream/table/pkconfig.txt", "--kafka-bootstrap-servers", "iz2zea86z2leonw09hpjijz:9092,iz2zea86z2leonw09hpjimz:9092,iz2zea86z2leonw09hpjilz:9092,iz2zea86z2leonw09hpjikz:9092", "--kfktopic", "testkafka", "--spark-check-point", "hdfs://10.200.48.67:8020/odsStream/checkpoint"))
    val zookeeperPath = argsMap.getOrElse("--zookeeper", "") //iz2zea86z2leonw09hpjijz:2181,iz2zea86z2leonw09hpjimz:2181,iz2zea86z2leonw09hpjilz:2181,iz2zea86z2leonw09hpjikz:2181
    val tablePkConfigPath = argsMap.getOrElse("--table-pk-config", "") //hdfs://10.200.48.67:8020/odsStream/table/pkconfig.txt
    val kafka = argsMap.getOrElse("--kafka-bootstrap-servers", "") //iz2zea86z2leonw09hpjijz:9092,iz2zea86z2leonw09hpjimz:9092,iz2zea86z2leonw09hpjilz:9092,iz2zea86z2leonw09hpjikz:9092
    val kafkaTopic = argsMap.getOrElse("--kfktopic", "") //testkafka
    val sparkCheckPoint = argsMap.getOrElse("--spark-check-point", "") //hdfs://10.200.48.67:8020/odsStream/checkpoint

    println(argsMap.toString())

    println(s"zookeeperPath: $zookeeperPath")
    println(s"tablePkConfigPath: $tablePkConfigPath")
    println(s"kafka: $kafka")
    println(s"kafkaTopic: $kafkaTopic")
    println(s"sparkCheckPoint: $sparkCheckPoint")


    assert(zookeeperPath === "iz2zea86z2leonw09hpjijz:2181,iz2zea86z2leonw09hpjimz:2181,iz2zea86z2leonw09hpjilz:2181,iz2zea86z2leonw09hpjikz:2181")
    assert(tablePkConfigPath === "hdfs://10.200.48.67:8020/odsStream/table/pkconfig.txt")
    assert(kafka === "iz2zea86z2leonw09hpjijz:9092,iz2zea86z2leonw09hpjimz:9092,iz2zea86z2leonw09hpjilz:9092,iz2zea86z2leonw09hpjikz:9092")
    assert(kafkaTopic === "testkafka")
    assert(sparkCheckPoint === "hdfs://10.200.48.67:8020/odsStream/checkpoint")
  }

}
