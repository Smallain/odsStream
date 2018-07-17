package com.smallain.utils

import scala.collection.immutable.{SortedSet, TreeMap}

object ArgsParse {


  /**
    * 获取输入参数，将输入的参数转换为map格式，这样可以根据map指定的key获取输入参数
    *
    * 例如：
    *
    * 输入参数是：
    * --kafka kafka:8888 --file filepath:8888 --zookeeper zookepper:9192
    *
    * 可以通过如下方式获取 "--zookeeper" 对应的参数
    * val tempmap = getArgsMap(args)
    * println(tempmap.getOrElse("--zookeeper", ""))
    *
    * @param para 输入行参数
    * @return 返回参数对应的map
    */
  def getArgsMap(para: Array[String]): Map[String, String] = {


    var keyArray: List[String] = List.empty
    var valueArray: List[String] = List.empty
    for (i <- para) {
      if (para.indexOf(i) % 2 == 0) {
        keyArray = keyArray :+ i
      } else if (para.indexOf(i) % 2 == 1) {
        valueArray = valueArray :+ i
      }
    }
    val kvMap: Map[String, String] = keyArray.zip(valueArray).toMap

    kvMap
  }
}
