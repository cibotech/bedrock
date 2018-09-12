package com.cibo.uidocumentation.example

import java.time.LocalDate

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.elements._
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
