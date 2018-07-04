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

	def md5HashString(s: String): String = {
		import java.security.MessageDigest
		import java.math.BigInteger
		val md = MessageDigest.getInstance("MD5")
		val digest = md.digest(s.getBytes)
		val bigInt = new BigInteger(1,digest)
		val hashedString = bigInt.toString(16)
		hashedString
	}
}
