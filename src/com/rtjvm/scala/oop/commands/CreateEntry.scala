package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command {
  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }


  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
    if (path.isEmpty) currentDirectory.addEntry(newEntry)
    else {
      val oldEntry = currentDirectory.findEntry(path.head).asDirectory
      currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
    }
  }

  def createSpecificEntry(state: State): DirEntry

  def doCreateEntry(state: State, name: String): State = {
    val wd = state.wd

    val allDirsInPath = wd.getAllFoldersInPath

    val newEntry: DirEntry = createSpecificEntry(state: State): DirEntry

    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)

  }

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage(s"Entry $name already exists!")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(s"$name must not contain separators")
    } else if (checkIllegal(name)) {
      state.setMessage(s"$name : illegal entry name!")
    } else {
      doCreateEntry(state, name)
    }
  }

}
