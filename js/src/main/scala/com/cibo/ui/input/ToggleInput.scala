package com.cibo.ui.input

import com.cibo.ui.Util
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}

object BooleanInput
trait BooleanInput{
  case class Props(toggled: Boolean, onChange: Boolean => Callback, inline: Boolean = false, label: Option[String] = None)
  case class State(toggled: Option[Boolean])
}

object ToggleInput extends BooleanInput {

  class Backend(val $: BackendScope[Props, State]) {
    def toggle(state: State, props: Props) = {
      props.onChange(!state.toggled.getOrElse(props.toggled)).runNow()
      $.modState(_.copy(toggled = Some(!state.toggled.getOrElse(props.toggled))))
    }
    def render(p: Props, s: State) = {
      val status = s.toggled match {
        case Some(e) => e
        case None if p.toggled => "initial-true"
        case None if !p.toggled => "initial-false"
      }

      <.div( ^.cls := s"toggle ${if(p.inline) "inline" else ""} $status",
        <.div( ^.cls := "toggle-indicator", ^.onClick --> toggle(s, p)),
        <.div( ^.cls := "interior-wrapper", ^.onClick --> toggle(s, p),
          <.div( ^.cls := "interior-indicator")
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("ToggleInput")
    .initialStateFromProps(p => State(None))
    .renderBackend[Backend]
    .build

  def apply(toggled: Boolean, onChange: Boolean => Callback) =
    component(Props(toggled, onChange))

}

object CheckBoxInput extends BooleanInput {

  class Backend(val $: BackendScope[Props, State]) {
    def toggle(state: State, props: Props) = {
      props.onChange(!state.toggled.getOrElse(props.toggled)).runNow()
      $.modState(_.copy(toggled = Some(!state.toggled.getOrElse(props.toggled))))
    }
    def render(p: Props, s: State) = {
      val status = s.toggled match {
        case Some(e) => e
        case None if p.toggled => true //"initial-true"
        case None if !p.toggled => false //"initial-false"
      }


      val inputTagMod = <.input( ^.`type` := "checkbox", ^.checked := status, ^.onChange --> toggle(s, p))

      p.label match {
        case Some(label) => Util.withLabel(label, inputTagMod)
        case None => inputTagMod
      }
    }
  }

  private val component = ScalaComponent.builder[Props]("CheckboxInput")
    .initialStateFromProps(p => State(None))
    .renderBackend[Backend]
    .build

  def apply(checked: Boolean, onChange: Boolean => Callback) =
    component(Props(checked, onChange))

  def withLabel(checked: Boolean, label: String, onChange: Boolean => Callback) = {
    component(Props(checked, onChange, false, Some(label)))
  }

}