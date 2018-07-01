package com.smallain.hbase_utils.dao

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.HColumnDescriptor
import org.apache.hadoop.hbase.HTableDescriptor
import org.apache.hadoop.hbase.client.HBaseAdmin

object CreateTable {
	def createT(tableName: String, columnFamily: String, maxVersion: Int) = {
		val conf = HBaseConfiguration.create()
		val admin = new HBaseAdmin(conf)
		
		val desc = new HTableDescriptor(tableName)
		val cf = new HColumnDescriptor(columnFamily)
		cf.setMaxVersions(1)
		desc.addFamily(cf)
		admin.createTable(desc)
	}
}
