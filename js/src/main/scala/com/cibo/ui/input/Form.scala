package com.cibo.ui.input

import com.cibo.ui.elements.Text
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._


object Form extends Form

trait Form {
  def form(title: Text, element: TagMod*) =
    <.div(^.cls := "form", <.div(^.cls := "title", title), element.toTagMod(identity))

  def spreadForm(title: Text, element: TagMod*) =
    <.div(^.cls := "form spread", <.div(^.cls := "title", title), element.toTagMod(identity))

  def formElement(element: TagMod*) = <.div(^.cls := "form-element", element.toTagMod(identity))
}
