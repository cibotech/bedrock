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

package com.cibo.uidocumentation.example

import java.time.LocalDate

import com.cibo.bedrock.ReactGridStrict._
import com.cibo.bedrock.elements._
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.document
import org.scalajs.dom.ext.PimpedNodeList

object TableExamplePage {

  implicit val localDateOrdering = new Ordering[LocalDate] {
    def compare(x: LocalDate, y: LocalDate) = (x.toEpochDay - y.toEpochDay).toInt
  }

  object TableKeys {
    val Date = TableKey[LocalDate]("Time", "Time", 0)
    val User = TableKey[String]("User", "User", 1)
    val Fields = TableKey[Double]("Fields", "Fields", 2)
    val headers = Seq(Date, User, Fields)
  }

  case class DummyRecord(time: LocalDate, user: String, fields: Int)
  val dummyRecords = Seq(
    DummyRecord(LocalDate.now(), "cshafer", 674),
    DummyRecord(LocalDate.now().minusDays(1), "mdewulf", 567),
    DummyRecord(LocalDate.now().minusDays(2), "rricht", 345),
    DummyRecord(LocalDate.now(), "pberryhill", 786)
  )

  val rows = dummyRecords.map { position =>
    val elements: Map[TableKey[_], TableValue] = Map(
      TableKeys.Date -> TableValue(position.time, <.td(position.time.toString)),
      TableKeys.User -> TableValue(position.user, <.td(position.user)),
      TableKeys.Fields -> TableValue(position.fields, <.td(position.fields))
    )
    TableRow(elements)
  }

  class Backend(val $ : BackendScope[Unit, Unit]) {

    def applySyntaxHighlight = Callback {
      import scala.scalajs.js.Dynamic.{global => g}
      val nodeList = document.querySelectorAll("pre code").toArray
      nodeList.foreach(g.window.hljs highlightBlock _)
    }

    def render() = {
      <.div(
        ^.cls := "style-guide",
        <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Tables"))),
        <.div(
          ^.cls := "main-view",
          <.div(
            ^.cls := "example-block",
            row(
              column(12)(
                <.h4("Example"),
                SortableTableRenderer(
                  SortableTable(headers = TableKeys.headers, rows, TableKeys.Fields))
              ),
              column(12)(
                <.h4("Example Code"),
                CodeExample(
                  "scala",
                  s"""
                     |  import com.cibo.ui.elements._
                     |
                     |  object TableKeys {
                     |    val Date     = TableKey[Int]("Time", "Time", 0)
                     |    val User     = TableKey[String]("User", "User", 1)
                     |    val Fields   = TableKey[Double]("Fields", "Fields", 2)
                     |    val headers  = Seq(Date, User, Fields)
                     |  }
                     |
                     |  case class DummyRecord(time: LocalDate, user: String, fields: Int)
                     |  val dummyRecords = Seq(
                     |    DummyRecord(LocalDate.now(), "cshafer", 674),
                     |    DummyRecord(LocalDate.now(), "mdewulf", 567),
                     |    DummyRecord(LocalDate.now(), "rricht", 345),
                     |    DummyRecord(LocalDate.now(), "pberryhill", 786)
                     |  )
                     |
                     |  val rows = dummyRecords.map{ position =>
                     |    val elements: Map[TableKey[_], TableValue] = Map(
                     |      TableKeys.Date     -> TableValue( position.time.toEpochDay.toInt, <.td(position.time.toString)),
                     |      TableKeys.User     -> TableValue( position.user, <.td(position.user)),
                     |      TableKeys.Fields   -> TableValue( position.fields, <.td(position.fields))
                     |    )
                     |    TableRow(elements)
                     |  }
                     |
                     |  SortableTableRenderer(SortableTable(headers = TableKeys.headers, rows, TableKeys.Fields ))
                     |
                      """.stripMargin
                )
              ),
              column(12)(
                <.h4("Filtering Table Example"),
                FilterableTable(headers = TableKeys.headers, rows, TableKeys.Fields),
                CodeExample(
                  "scala",
                  s"""
                     |
                     | FilterableTable(headers = TableKeys.headers, rows, TableKeys.Fields)
                     |
                     |
                      """.stripMargin
                )
              )
            )
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Unit]("TableExample")
    .renderBackend[Backend]
    .componentDidMount(_.backend.applySyntaxHighlight)
    .build

  def apply() = component()

}
