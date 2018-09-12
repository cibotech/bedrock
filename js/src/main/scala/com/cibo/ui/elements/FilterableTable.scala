package com.cibo.ui.elements

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.input.{Form, TextInput}
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.component.Scala.BackendScope
import japgolly.scalajs.react.vdom.html_<^._

object FilterableTable extends Form {

  case class Props(headers: Seq[TableKey[_]], rows: Seq[TableRow], defaultSort: TableKey[_])
  case class State(filter: String)
  class Backend($ : BackendScope[Props, State]) {

    def render(props: Props, state: State) = {
      val filteredRows = props.rows.filter { row =>
        row.values.exists(x => x._2.sortValue.toString.contains(state.filter))
      }

      row(
        column(12)(
          form("Filter", TextInput("", (x: String) => {
            $.modState(_.copy(x))
          }))
        ),
        column(12)(
          SortableTableRenderer(SortableTable(props.headers, filteredRows, props.defaultSort))
        )
      )
    }
  }

  def apply(headers: Seq[TableKey[_]], rows: Seq[TableRow], defaultSort: TableKey[_]) = {
    component(Props(headers, rows, defaultSort))
  }

  private val component = ScalaComponent
    .builder[Props]("FilterableTable")
    .initialStateFromProps(p => State(""))
    .renderBackend[Backend]
    .build

}
