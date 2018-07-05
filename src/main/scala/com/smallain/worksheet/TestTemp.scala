package com.smallain.worksheet

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

object TestTemp extends App {
  def transLongTimeStampToString(timestamp: String): String = {
    val formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatTime.format(new Date(timestamp.toLong))
  }

  val millisecond = Instant.now().toEpochMilli; // 精确到毫秒 13 位
  println("毫秒"+millisecond)
  println(new Integer(millisecond.hashCode()).shortValue())
  val salt = new Integer(millisecond.hashCode()).shortValue() % 46
  val ddd = new Integer(millisecond.hashCode()).shortValue()
  println(ddd)
  println(salt)
  val time = transLongTimeStampToString((millisecond * (-1) * (-1)).toString)





  println(time)



}

//[B@543c6f6d
//[B@543c6f6d