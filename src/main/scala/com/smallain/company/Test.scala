package com.smallain.company

import com.smallain.utils.TransLongTimeStamp._
object Test extends App {
	

	val tm = "1530437707000"
	val a = transLongTimeStampToString(tm)
	println(a)
}
