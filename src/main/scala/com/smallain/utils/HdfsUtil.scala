package com.smallain.utils

import java.io.IOException
import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs._

object HdfsUtil {

  object HDFSUtil {
    val conf: Configuration = new Configuration()
    var fs: FileSystem = _
    var files: RemoteIterator[LocatedFileStatus] = null
    var hdfsInStream: FSDataInputStream = null

    def getFiles(HDFSPath: String) = {
      try {
        fs = FileSystem.get(new URI(HDFSPath), conf)
      } catch {
        case e: IOException => {
          e.printStackTrace
        }
      }
      files
    }

    def getFiles(HDFSPath: String, targetPath: String) = {
      try {
        fs = FileSystem.get(new URI(HDFSPath), conf)
        // 返回指定路径下所有的文件
        files = fs.listFiles(new Path(targetPath), false)
      } catch {
        case e: IOException => {
          e.printStackTrace
        }
      }
      files
    }

    def getFSDataInputStream(path: String): FSDataInputStream = {
      try {
        fs = FileSystem.get(URI.create(path), conf)
        hdfsInStream = fs.open(new Path(path))
      } catch {
        case e: IOException => {
          e.printStackTrace
        }
      }
      return hdfsInStream
    }


    def mkdir(finalPath: String) = {
      fs.create(new Path(finalPath))
    }

    def rename(oldPath: String, finalPath: String) = {
      fs.rename(new Path(oldPath), new Path(finalPath))
    }

    def exist(existPath: String): Boolean = {
      fs.exists(new Path(existPath))
    }

    def delete(deletePath: String) = {
      fs.delete(new Path(deletePath), true)
    }

    def read(readPath: String) = {
      fs.open(new Path(readPath))
    }

    def close() = {
      try {
        if (fs != null) {
          fs.close()
        }
      } catch {
        case e: IOException => {
          e.printStackTrace
        }
      }
    }

  }

}
