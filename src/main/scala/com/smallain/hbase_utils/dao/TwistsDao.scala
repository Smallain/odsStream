package com.smallain.hbase_utils.dao

import org.apache.hadoop.hbase.client.HTablePool
import org.apache.log4j.Logger
import java.lang.{Long=>JLong}
case class TwistsDao(poolPara: HTablePool) {
	private val pool = poolPara
	private val log = Logger.getLogger(UsersDao.getClass)
	
	val longLength = JLong.SIZE / 8
}