package com.smallain.utils.jsonparse.company

object CanDoDataType {
  def jsonDataFilter(str: String): Boolean = {
    str.contains("table-alter") || str.contains("insert") || str.contains("update") || str.contains("delete")
  }
}
