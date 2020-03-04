package com.rtjvm.scala.oop.files

abstract class DirEntry(val parentPath: String, val name: String) {
  def isDirectory: Boolean
  def isFile: Boolean

  def path: String = {
    val seperatorIfNecessery =
      if (Directory.ROOT_PATH.equals(parentPath)) ""
      else Directory.SEPARATOR
    parentPath + seperatorIfNecessery + name
  }

  def asDirectory: Directory

  def asFile: File

  def getType: String
}
