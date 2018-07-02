package com.smallain.hbase_utils.dao

import java.io.IOException

import com.smallain.company.model.User
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.HColumnDescriptor
import org.apache.hadoop.hbase.HTableDescriptor
import org.apache.hadoop.hbase.client.{Get, HBaseAdmin, HTablePool, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.log4j.Logger

class TableDao(poolPara: HTablePool) {

  val log = Logger.getLogger(UsersDao.getClass)
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
    val admin = new HBaseAdmin(conf)

    val desc = new HTableDescriptor(tableName)
    val cf = new HColumnDescriptor(columnFamily)
    cf.setMaxVersions(3)
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
    log.debug(String.format("Creating Get for %s", rowKey))
    val g = new Get(Bytes.toBytes(rowKey))
    g.addFamily(Bytes.toBytes(columnFamily))
    g
  }


  private def mkPut(rowKey: String, columnFamily: String, columns: String, value: String) = {
    log.debug(String.format("Creating Put for %s", rowKey))
    val p = new Put(Bytes.toBytes(rowKey))
    p.add(Bytes.toBytes(columnFamily),
      Bytes.toBytes(columns),
      Bytes.toBytes(value)
    )
    p
  }

  @throws[IOException]
  def putTable(tableName: String, rowKey: String, columnFamily: String, columns: String, value: String): Unit = {
    val users = pool.getTable(tableName)
    val p = mkPut(rowKey: String, columnFamily: String, columns: String, value: String)
    users.put(p)
    users.close()
  }

//  @throws[IOException]
//  def getFamilColumn(tableName: String, rowKey: String): String = {
//    val users = pool.getTable(tableName)
//    val g = new Get(Bytes.toBytes(rowKey))
//
//    val result = users.get(g)
//    val fc_rep = result.listCells()
//    val fc = Bytes.toString(fc_rep)
//    users.close()
//    fc
//  }

}
