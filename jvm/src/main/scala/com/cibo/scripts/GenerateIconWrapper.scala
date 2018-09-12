package com.cibo.scripts

import java.io.PrintWriter
import java.nio.file.{Files, Paths}

import scala.collection.JavaConverters._

//first argument should be the path to the checked out material design icon repository
//second argument should be the output file to write the generated code to
object GenerateIconWrapper extends App {
  assert(args.size == 2, "Must supply two arguments")
  val repoLocation = args(0)
  val iconPath = Paths.get(repoLocation)
  val outFile = Paths.get(args(1))
  val iconSuffix = "_48px.svg"
  val iconPrefix = "ic_"

  validateInputs

  val writer = new PrintWriter(outFile.toFile)
  val fileWalker = Files.walk(iconPath)
  val fileIter = fileWalker.iterator().asScala

  fileIter
    .map(_.toFile)
    .filter(!_.isDirectory)
    .map(_.getName)
    .filter(_.endsWith(iconSuffix))
    .map(_.replace(iconSuffix, "")
      .replace(iconPrefix, ""))
    .map(methodFromIconName)
    .toSeq
    .distinct
    .foreach(writer.println)

  fileWalker.close()
  writer.close()

  def validateInputs: Unit = {
    if (Files.notExists(iconPath)) {
      throw new IllegalArgumentException("Must supply a valid path to the material icon directory.")
    } else if (!Files.isWritable(outFile.getParent)) {
      throw new IllegalArgumentException("Must supply a writable output file")
    }
  }

  def underscoreToCamel(arg: String): String = {
    arg
      .foldLeft(Seq[Char]()) {
        case (accum, x) =>
          accum :+ {
            if (accum.lastOption.contains('_')) x.toUpper
            else x
          }
      }
      .mkString
      .replace("_", "")
  }

  def methodFromIconName(name: String): String = {
    s"""def ${underscoreToCamel(name)} = Icon("$name")""".stripMargin.stripLineEnd
  }
}
