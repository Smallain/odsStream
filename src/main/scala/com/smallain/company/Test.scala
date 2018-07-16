package com.smallain.company
import com.smallain.utils.Md5Utils._
import com.smallain.utils.TransLongTimeStamp._
object Test extends App {
	

	val tm = "1530437707000"
	val sss = md5HashString("wuyuhang666")
	val a = transLongTimeStampToString(tm)
	println(sss)
}


