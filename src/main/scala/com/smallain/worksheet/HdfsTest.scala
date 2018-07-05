package com.smallain.worksheet

import java.io.IOException

import com.smallain.utils.HdfsUtil.HDFSUtil

object HdfsTest {
  def main(args: Array[String]): Unit = {
    val HDFSPath: String = "hdfs://10.200.48.67:8020/"
    val newTargetPath: String = HDFSPath + "spark/hdfs_opration"


    //这里添加一个变量， 用来变化输入文件名称
    var n = 1

    try {
      //获取spark conf
      HDFSUtil.getFiles(HDFSPath)
      //判断路径是否存在 ， 如果存在就删除
      if (HDFSUtil.exist(newTargetPath) == true) {
        HDFSUtil.delete(newTargetPath)
        println("删除 " + newTargetPath + " 成功")
      } else {
        println("未发现 " + newTargetPath + " ,准备开始创建!")
      }

      //创建一个空文件
      val filename: String = "wxk_" + n + ".txt"
      val final_filePath: String = newTargetPath + "/" + filename
      HDFSUtil.mkdir(final_filePath)
      println("创建 " + final_filePath + " 成功!")

      //修改一个文件名
      val after_name = "qsp" + n + ".txt"
      val after_rename_path = newTargetPath + "/" + after_name
      HDFSUtil.rename(final_filePath, after_rename_path)
      println("将 " + final_filePath + " 修改为 " + after_rename_path + "成功!")

      //同时也能移动文件
      HDFSUtil.rename(after_rename_path, HDFSPath + "spark/" + after_name)
      println("改变目录成功!")

      //读取一个文件的名称，这里输入如果是目录的话也会读取目录下所有的文件名称,但是不会获取文件夹名称
      val target_file = HDFSPath + "spark/" + after_name
      val allfile = HDFSUtil.getFiles(HDFSPath, target_file)
      while (allfile.hasNext) {
        val file = allfile.next()
        println(file.getPath.getName)
      }

    } catch {
      case e: IOException => {
        e.printStackTrace
      }
    } finally {
      HDFSUtil.close()
    }
  }

}
