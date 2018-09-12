package com.cibo.ui.elements

import java.time.format.TextStyle

import com.cibo.ui.Util
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.BackendScope
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._

import scala.language.existentials

case class TableKey[T: Ordering](name: Text,
                                 key: String,
                                 order: Int,
                                 defaultDecending: Boolean = true,
                                 sorting: Boolean = true) {
  val ordering = implicitly[Ordering[T]]
  def compareAny(v1: Any, v2: Any) = {
    ordering.compare(v1.asInstanceOf[T], v2.asInstanceOf[T]) * { if (defaultDecending) -1 else 1 }
  }
}
case class TableValue(sortValue: Any, value: TagMod)
case class TableRow(values: Map[TableKey[_], TableValue], modifiers: TagMod = EmptyVdom)
case class SortableTable(headers: Seq[TableKey[_]], rows: Seq[TableRow], defaultSort: TableKey[_]) {

  def sortBy(header: TableKey[_]) = {
    val sorted = rows.sortWith(
      (row1, row2) =>
        header.compareAny(row1.values.get(header).get.sortValue,
                          row2.values.get(header).get.sortValue) > 0)
    this.copy(rows = sorted)
  }
}

object SortableTableRenderer {

  case class Props(table: SortableTable, headerTextStyle: Text => Text = _.bold.large.upperCase)
  case class State(table: SortableTable, currentSort: TableKey[_], decending: Boolean)

  class Backend($ : BackendScope[Props, State]) {

    def displayDirectionHeader(active: Boolean, decending: Boolean) = {
      <.div(
        ^.cls := s"sort-direction ${if (active) {
          if (decending) "active decending" else "active ascending"
        } else ""}",
        <.span(^.cls := "up", Icon.arrowDropUp),
        <.span(^.cls := "down", Icon.arrowDropDown)
      )
    }

    def render(state: State, props: Props) = {
      val table = state.table.sortBy(state.currentSort)

      val headers = table.headers.sortBy(_.order).map { header =>
        <.th(
          props.headerTextStyle(header.name),
          if (header.sorting)
            displayDirectionHeader(header.key == state.currentSort.key, state.decending)
          else EmptyVdom,
          ^.onClick --> {
            if (state.currentSort.key == header.key) {
              $.modState(_.copy(decending = !state.decending))
            } else {
              $.modState(_.copy(currentSort = header, decending = header.defaultDecending))
            }
          }
        )
      }

      val rows = { if (state.decending) table.rows else table.rows.reverse }.map { row =>
        <.tr(row.values.toSeq
          .sortBy(_._1.order)
          .map { ele =>
            ele._2.value
          }
          .toVector.toTagMod,
          row.modifiers
        )

      }

      <.div(^.cls := "field-table table-responsive",
            <.table(
              <.thead(
                <.tr(headers.toTagMod)
              ),
              <.tbody(
                rows.toTagMod
              )
            ))
    }
  }

  private val component = ScalaComponent
    .builder[Props]("SortableTable")
    .initialStateFromProps(p => State(p.table, p.table.defaultSort, false))
    .renderBackend[Backend]
    .componentDidUpdate(backend => {
      if (backend.prevProps.table.rows != backend.currentProps.table.rows) {
        backend.modState { x =>
          x.copy(table = x.table.copy(rows = backend.currentProps.table.rows))
        }
      } else Callback((): Unit)
    })
    .build

  def apply(sortableTable: SortableTable) = component(Props(sortableTable))
}
