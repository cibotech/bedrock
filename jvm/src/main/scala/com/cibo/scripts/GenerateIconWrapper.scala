/*
 * Copyright (c) 2018, CiBO Technologies, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
