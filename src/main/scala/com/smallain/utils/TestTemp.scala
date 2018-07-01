package com.smallain.utils

import org.apache.hadoop.hbase.util.Bytes

object TestTemp extends App {
	val userHash = Md5Utils.md5sum("guolei")
	println(Bytes.toString(userHash))
}

//[B@543c6f6d
//[B@543c6f6d