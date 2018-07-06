package com.smallain.hbase_utils.dao

import java.io.IOException

import com.smallain.company.model.User
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.HColumnDescriptor
import org.apache.hadoop.hbase.HTableDescriptor
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.log4j.Logger

class TableDao(poolPara: HTablePool) extends Serializable {

  val pool = poolPara

  /**
    * hbase表创建函数
    *
    * @param tableName    hbase表名
    * @param columnFamily hbase列簇名
    * @param maxVersion   hbase单元版本数量
    */
  def createTable(tableName: String, columnFamily: String, maxVersion: Int) = {
    val conf = HBaseConfiguration.create()
    val admin = new HBaseAdmin(conf) with Serializable

    val desc = new HTableDescriptor(tableName) with Serializable
    val cf = new HColumnDescriptor(columnFamily) with Serializable
    cf.setMaxVersions(maxVersion)
    desc.addFamily(cf)
    admin.createTable(desc)
  }

  /**
    * 获取Hbase表的get句柄，指定单位到列簇
    *
    * @param rowKey       hbase行键
    * @param columnFamily hbase列簇名
    *                     throws 异常有可能获取不到
    * @return 返回Hbase API的Get句柄
    */
  @throws[IOException]
  private def mkGet(rowKey: String, columnFamily: String) = {
    val g = new Get(Bytes.toBytes(rowKey)) with Serializable
    g.addFamily(Bytes.toBytes(columnFamily))
    g
  }

  @throws[IOException]
  private def mkPut(rowKey: String, columnFamily: String, columns: String, value: String) = {
    val p = new Put(Bytes.toBytes(rowKey)) with Serializable
    p.add(Bytes.toBytes(columnFamily),
      Bytes.toBytes(columns),
      Bytes.toBytes(value)
    )
    p
  }

  @throws[IOException]
  private def mkDel(rowKey: String): Delete = {
    val del = new Delete(Bytes.toBytes(rowKey))
    del
  }

  @throws[IOException]
  def putTable(tableName: String, rowKey: String, columnFamily: String, columns: String, value: String): Unit = {
    val users = pool.getTable(tableName)
    val p = mkPut(rowKey: String, columnFamily: String, columns: String, value: String)
    users.put(p)
    users.close()
  }

  @throws[IOException]
  def deleteUser(tableName: String, rowKey: String): Unit = {
    val users = pool.getTable(tableName)
    val del = mkDel(rowKey)
    users.delete(del)
    users.close()
  }
}
