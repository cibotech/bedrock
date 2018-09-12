package com.cibo.ui.list

import com.cibo.ui.Padding
import com.cibo.ui.input.Form.form
import com.cibo.ui.input.TextInput
import japgolly.scalajs.react.component.builder.Lifecycle.RenderScope
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._
import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.pane.Pane
import com.cibo.ui.state.{AsyncData, AsyncEmpty}
import org.scalajs.dom.ext.KeyCode

abstract class ElementList[T] {

  def renderListItem(item: T): TagMod

  def renderListItems(items: Seq[T]): VdomElement = {
    <.ul(
      items.toTagMod(renderListItem)
    )
  }

  protected def render(p: Seq[T]) = renderListItems(p)

  val component = ScalaComponent.builder[Seq[T]]("Listing")
    .render( x => render(x.props))
    .build

  def apply(seq: Seq[T]) = component(seq)

}

case class ItemBinding[T](select: T => Callback, element: T, isFocused: Boolean)

abstract class StringFilteringList[T]{


  case class State(filterString: String, focusedElement: Option[Int], currentElement: Option[T], selecting: Boolean)
  case class Props(seq: Seq[T], renderer: ItemBinding[T] => TagMod, onSelect: T => Callback)

  def filterFunction(item: T, filterString: String): Boolean

  def listWrapper(selecting: Boolean, inner: TagMod): TagMod = <.ul(inner)

  protected def renderListItems(filteredItems: Seq[T], p: Props, s: State, backend: RenderScope[Props, State, Unit]): TagMod = {

    listWrapper(
      selecting = s.selecting,
      filteredItems.zipWithIndex.toTagMod{ case (x, idx) => p.renderer(ItemBinding({ x =>
        p.onSelect(x).thenRun(backend.modState(_.copy(focusedElement = None))).runNow()
      }, x, s.focusedElement.getOrElse(-1) == idx))}
    )
  }

  protected def render(p: Props, s: State, backend: RenderScope[Props, State, Unit]) = {
    val filteredItems = p.seq.filter(item => filterFunction(item, s.filterString))

    val filterInput = {
      form("Filter",
        TextInput(s.filterString,
          onChange = { text => backend.modState(_.copy(text)) },
          onKeypress = Some( keypress => backend.modState{ state =>
            val keycode = keypress.keyCode
            if(keycode == KeyCode.Down || keycode == KeyCode.Up) {
              val focusedElementIdx = state.focusedElement.map { idx =>
                if (keycode == KeyCode.Down) {
                  (idx + 1).min(filteredItems.seq.length - 1)
                } else if (keycode == KeyCode.Up) {
                  (idx - 1).max(0)
                } else idx
              }.getOrElse(0)
              state.copy(focusedElement = Some(focusedElementIdx))
            } else if(keycode == KeyCode.Enter){
              val element = state.focusedElement.map(filteredItems.seq(_))
              element.foreach(p.onSelect(_).runNow())
              state.copy(currentElement = element)
            } else state
          })
        ))
    }
    row(
      ^.onBlur --> backend.modState(_.copy(selecting = false)),
      ^.onFocus --> backend.modState(_.copy(selecting = true)),
      column(12)(filterInput),
      column(12)(
        renderListItems(filteredItems, p, s, backend)
      )
    )
  }

  val component = ScalaComponent.builder[Props]("Listing")
    .initialState(State("", None, None, false))
    .render( x => render(x.props, x.state, x))
    .build

  def apply(seq: Seq[T], renderer: ItemBinding[T] => TagMod, onSelect: T => Callback) = component(Props(seq, renderer, onSelect))

}

abstract class BasicList[T] extends ElementList[T] {

  val listingWrapper = Listing()_

  def renderListItem(item: T): TagMod

  override def renderListItems(items: Seq[T]): VdomElement = {
    listingWrapper(
      items.map(renderListItem)
    )
  }

}