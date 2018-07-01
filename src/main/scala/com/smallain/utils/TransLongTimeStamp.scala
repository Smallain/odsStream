package com.smallain.utils

import java.text.SimpleDateFormat
import java.util.Date

object TransLongTimeStamp {
	def transLongTimeStampToString(timestamp: String): String = {
		val formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		formatTime.format(new Date(timestamp.toLong))
	}
}
