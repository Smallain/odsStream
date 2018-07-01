package com.smallain.hbase_utils.dao

import java.io.IOException
import java.util

import com.smallain.company.model.User
import org.apache.hadoop.hbase.client.Delete
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.client.HTableInterface
import org.apache.hadoop.hbase.client.HTablePool
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.client.ResultScanner
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.util.Bytes
import org.apache.log4j.Logger

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.MutableList

case class UsersDao(poolPara: HTablePool) {
  
  
  val TABLE_NAME: Array[Byte] = Bytes.toBytes("users")
  val INFO_FAM: Array[Byte] = Bytes.toBytes("info")
  val USER_COL: Array[Byte] = Bytes.toBytes("user")
  val NAME_COL: Array[Byte] = Bytes.toBytes("name")
  val EMAIL_COL: Array[Byte] = Bytes.toBytes("email")
  val PASS_COL: Array[Byte] = Bytes.toBytes("password")
  val TWEETS_COL: Array[Byte] = Bytes.toBytes("tweet_count")
  val HAMLET_COL: Array[Byte] = Bytes.toBytes("hamlet_tag")

  private val pool = poolPara
  private val log = Logger.getLogger(UsersDao.getClass)

  import java.io.IOException

  import org.apache.hadoop.hbase.util.Bytes

  @throws[IOException]
  private def mkGet(user: String) = {
    log.debug(String.format("Creating Get for %s", user))
    val g = new Get(Bytes.toBytes(user))
    g.addFamily(INFO_FAM)
    g
  }

  import org.apache.hadoop.hbase.util.Bytes

  private def mkPut(u: User) = {
    log.debug(String.format("Creating Put for %s", u))
    val p = new Put(Bytes.toBytes(u.user))
    p.add(INFO_FAM, USER_COL, Bytes.toBytes(u.user))
    p.add(INFO_FAM, NAME_COL, Bytes.toBytes(u.name))
    p.add(INFO_FAM, EMAIL_COL, Bytes.toBytes(u.email))
    p.add(INFO_FAM, PASS_COL, Bytes.toBytes(u.password))
    p
  }

  import org.apache.hadoop.hbase.util.Bytes

  def mkPut(username: String, fam: Array[Byte], qual: Array[Byte], value: Array[Byte]): Put = {
    val p = new Put(Bytes.toBytes(username))
    p.add(fam, qual, value)
    p
  }

  import org.apache.hadoop.hbase.util.Bytes

  private def mkDel(user: String): Delete = {
    log.debug(String.format("Creating Delete for %s", user))
    val d = new Delete(Bytes.toBytes(user))
    d
  }

  private def mkScan: Scan = {
    val s = new Scan()
    s.addFamily(INFO_FAM)
    s
  }


  @throws[IOException]
  def addUser(user: String, name: String, email: String, password: String): Unit = {
    val users = pool.getTable(TABLE_NAME)
    val p = mkPut(User(user, name, email, password))
    users.put(p)
    users.close()
  }


  @throws[IOException]
  def getUser(user: String): User = {
    val users = pool.getTable(TABLE_NAME)
    val g = mkGet(user)
    val result = users.get(g)
    if (result.isEmpty) {
      log.info(String.format("user %s not found.", user))
    }

    val byte_name = result.getValue(
      Bytes.toBytes("info"),
      Bytes.toBytes("name")
    )
    val name_col = Bytes.toString(byte_name)

    val byte_email = result.getValue(
      Bytes.toBytes("info"),
      Bytes.toBytes("email")
    )
    val email_col = Bytes.toString(byte_email)


    val byte_password = result.getValue(
      Bytes.toBytes("info"),
      Bytes.toBytes("password")
    )
    val password_col = Bytes.toString(byte_password)


    val u = User(user, name_col, email_col, password_col)
    users.close()
    u
  }


  @throws[IOException]
  def deleteUser(user: String): Unit = {
    val users = pool.getTable(TABLE_NAME)
    val d = mkDel(user)
    users.delete(d)
    users.close()
  }


  @throws[IOException]
  def getUsers: ArrayBuffer[User] = {
    log.info(String.format("now is getting users"))
    val users = pool.getTable(TABLE_NAME)
    val results = users.getScanner(mkScan)
    val ret = ArrayBuffer[User]()


    val it: util.Iterator[Result] = results.iterator()
    while (it.hasNext) {
      val next: Result = it.next()

      val user_byte = next.getValue(Bytes.toBytes("info"), Bytes.toBytes("user"))
      val user_col = Bytes.toString(user_byte)

      val name_byte = next.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))
      val name_col = Bytes.toString(name_byte)

      val email_byte = next.getValue(Bytes.toBytes("info"), Bytes.toBytes("email"))
      val email_col = Bytes.toString(email_byte)

      val password_byte = next.getValue(Bytes.toBytes("info"), Bytes.toBytes("password"))
      val password_col = Bytes.toString(password_byte)

      ret += User(user_col, name_col, email_col, password_col)

    }
    users.close()
    ret
  }
}



