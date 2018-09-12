package com.cibo.ui.compound

import com.cibo.ui.Padding
import com.cibo.ui.ReactGridStrict.{column, row}
import com.cibo.ui.input.Form.form
import com.cibo.ui.input.TextInput
import com.cibo.ui.list.{ItemBinding, StringFilteringList}
import com.cibo.ui.pane.Pane
import japgolly.scalajs.react.{Callback, ScalaComponent}
import japgolly.scalajs.react.component.builder.Lifecycle.RenderScope
import japgolly.scalajs.react.vdom.html_<^._


abstract class FilteringContentView[T]{

  case class FilteringContentState(selected: Option[T])

  val stringFilteringList: StringFilteringList[T]

  def activeContentRenderer(element: Option[T]): TagMod

  def listItemRenderer(ib: ItemBinding[T]): TagMod

  protected def render(p: Seq[T], s: Option[T], backend: RenderScope[Seq[T], Option[T], Unit]) = {
    row(
      column(3)(stringFilteringList(p, listItemRenderer, x => backend.setState(Some(x)))),
      column(9)(activeContentRenderer(s))
    )
  }

  val component = ScalaComponent.builder[Seq[T]]("FilteringContentView")
    .initialState(Option.empty[T])
    .render( x => render(x.props, x.state, x))
    .build

  def apply(seq: Seq[T]) = component(seq)

}
