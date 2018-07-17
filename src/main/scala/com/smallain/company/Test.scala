package com.smallain.company

import com.smallain.utils.Md5Utils._
import com.smallain.utils.TransLongTimeStamp._

import scala.collection.immutable.{SortedSet, TreeMap}

object Test {

  def main(args: Array[String]): Unit = {
    //-zookeeper someip:9190 -finepath /user/hdfs


    def getArgsMap(para: Array[String]): Map[String, String] = {

      val mapp: TreeMap[String, String] = TreeMap()

      var keyArray: SortedSet[String] = SortedSet()
      var valueArray: SortedSet[String] = SortedSet()
      for (i <- para) {
        if (para.indexOf(i) % 2 == 0) {
          keyArray += i
        } else if (para.indexOf(i) % 2 == 1) {
          valueArray += i
        }
      }
      val sstemp: Map[String, String] = keyArray.zip(valueArray).toMap

      sstemp
    }


    val tempmap = getArgsMap(args)

    println(tempmap.getOrElse("--zookeeper", ""))

    val tm = "1530437707000"
    val sss = md5HashString("wuyuhang666")
    val a = transLongTimeStampToString(tm)
    println(sss)
  }

}


