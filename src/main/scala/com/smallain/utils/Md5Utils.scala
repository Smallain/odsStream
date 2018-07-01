package com.smallain.utils

object Md5Utils {
	
	import org.apache.hadoop.hbase.util.Bytes
	import java.security.MessageDigest
	import java.security.NoSuchAlgorithmException
	
	val MD5_LENGTH = 16 // bytes
	
	
	def md5sum(s: String): Array[Byte] = {
		
		var d = MessageDigest.getInstance("MD5")
		println(d.toString)
		d.digest(Bytes.toBytes(s))
	}
}
