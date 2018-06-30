package com.smallain.company

import com.smallain.hbase_utils.dao.UsersDao
import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client.HTablePool

object TestHbase {
  def main(args: Array[String]): Unit = {
    val myConf = HBaseConfiguration.create()
    myConf.set("hbase.zookeeper.quorum", "iz2zea86z2leonw09hpjijz:2181,iz2zea86z2leonw09hpjimz:2181,iz2zea86z2leonw09hpjilz:2181,iz2zea86z2leonw09hpjikz:2181")

    val pool = new HTablePool(myConf, 2147483647)


    val dao = UsersDao(pool)

    val user = dao.getUsers
    user.foreach(x => println("用户是：" + x.user + " 姓名是：" + x.name + " 邮箱是：" + x.email + " 密码是：" + x.password))

    //val user = dao.addUser("xuxin","XuXin","xuxin@163.com","567789")

    //user.foreach(x => println(x.name))


    //    //val userstable = new HTable(myConf, "wuyuhang")
    //    val userstable = pool.getTable("wuyuhang")
    //    val p = new Put(Bytes.toBytes("000000002"))
    //    p.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("liyang"))
    //
    //    userstable.put(p)
    //    println("hello,world")
    //  pool.closeTablePool("wuyuhang")
  }

}
