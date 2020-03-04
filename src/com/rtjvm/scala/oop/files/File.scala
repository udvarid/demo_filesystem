package com.rtjvm.scala.oop.files

import com.rtjvm.scala.oop.filesystem.FilesytemException

class File(override val parentPath: String, override val name: String, val contents: String)
  extends DirEntry(parentPath, name) {

  override def asDirectory: Directory = throw new FilesytemException("File can not converted to directory")

  override def getType: String = "File"

  override def asFile: File = this

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true
}

object File {

  def empty(parenthPath: String, nameOfFile: String): File =
    new File(parenthPath, nameOfFile, "")
}