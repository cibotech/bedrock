package com.cibo.ui.state

import com.cibo.ui.elements.Icon
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}
import org.scalajs.dom.html

class QuickStateWrapper[T]{

  type RenderArgs = (T, (T => T)=> CallbackTo[Unit])
  case class Props(render: RenderArgs => VdomElement, initialState: T)

  class Backend(val $: BackendScope[Props, T]) {

    def modState(t: T => T): CallbackTo[Unit] = {
      $.modState(t)
    }

    def render(p: Props, s: T) = {
      p.render((s, modState))
    }
  }

  private val component = ScalaComponent
    .builder[Props]("SelectInput")
    .initialStateFromProps(p => p.initialState)
    .renderBackend[Backend]
    .build

  def wrap(intitialState: T)(fn: RenderArgs => VdomElement) = {
    component(Props(fn, intitialState))
  }

}

