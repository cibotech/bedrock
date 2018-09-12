package com.cibo.ui.input

import com.cibo.ui.elements.Icon
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}
import org.scalajs.dom.html

class SelectInput[T] {

  case class Props(values: Seq[T], initialValue: T, visualizer: T => String, onChange: T => Callback)

  case class State(current: T)

  class Backend(val $: BackendScope[Props, State]) {

    def handleChange(changePassthrough: T => Callback)(e: ReactEventFrom[html.Select]) = {
      e.persist()
      val props = $.props.runNow()
      $.modState(_.copy(current = props.values(e.target.selectedIndex))).runNow()
      changePassthrough(props.values(e.target.selectedIndex))
    }

    def render(p: Props, s: State) = {

      val values = p.values.map { v =>
        <.option(p.visualizer(v), ^.value := p.visualizer(v))
      }

      <.div( ^.cls := "select-wrapper",
        <.select(
        ^.value := p.visualizer(s.current),
        ^.onChange ==> handleChange(p.onChange),
        values.toTagMod
        ),
        Icon.arrowDropDown.toDom.apply(^.cls := "select-arrow")
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("SelectInput")
    .initialStateFromProps(p => State(p.initialValue))
    .renderBackend[Backend]
    .build

  def newComponent(values: Seq[T], initialValue: T, visualizer: T=> String, onChange: T => Callback) =
    component(this.Props(values,initialValue,visualizer,onChange))

}

object SelectInput {
  val stringInput = new SelectInput[String]
  def apply(values: Seq[String], initialValue: String, onChange: String => Callback) =
    stringInput.newComponent(values, initialValue, identity, onChange)
}
