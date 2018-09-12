package com.cibo.ui.input

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}

object TextInput {

  case class Props(
    initialValue: String,
    placeHolder: Option[String],
    onChange: String => Callback,
    onKeypress: Option[ReactKeyboardEvent => Callback],
    onFocus: Option[Boolean => Callback]
  )
  case class State(current: String)

  class Backend(val $ : BackendScope[Props, State]) {

    def handleChange(changePassthrough: String => Callback)(e: ReactEventFromInput): Callback  = {
      e.persist()
      $.modState(_.copy(current = e.target.value)).runNow()
      changePassthrough(e.target.value)
    }

    def handleKeyboardEvent(eventPassthrough: ReactKeyboardEvent => Callback)(e: ReactKeyboardEvent): Callback  = {
      e.persist()
      eventPassthrough(e)
    }

    def render(p: Props, s: State) = {
      val focusMod = p.onFocus.map(e => Seq(^.onFocus --> e(true), ^.onBlur --> e(false))
                      .toTagMod(identity)).getOrElse(EmptyVdom)
      <.input(
        ^.placeholder := placeholderText(p),
        ^.value := s.current,
        ^.onChange ==> handleChange(p.onChange),
        focusMod,
        p.onKeypress.map(^.onKeyDown ==> handleKeyboardEvent(_)).getOrElse(EmptyVdom),
        ^.`type` := "text"
      )
    }

    private def placeholderText(p: Props) = {
      p.placeHolder match {
        case Some(text) => text
        case None => ""
      }
    }
  }

  private val component = ScalaComponent
    .builder[Props]("TextInput")
    .initialStateFromProps(p => State(p.initialValue))
    .renderBackend[Backend]
    .build

  def apply(initialValue: String, onChange: String => Callback,
            onKeypress: Option[ReactKeyboardEvent => Callback] = None,
            onFocus: Option[Boolean => Callback] = None) =
    component(Props(initialValue, None, onChange, onKeypress, onFocus))

  def withPlaceHolder(placeHolder: String, onChange: String => Callback,
                      onKeypress: Option[ReactKeyboardEvent => Callback] = None,
                      onFocus: Option[Boolean => Callback] = None) =
    component(Props("", Some(placeHolder), onChange, onKeypress, onFocus))
}
