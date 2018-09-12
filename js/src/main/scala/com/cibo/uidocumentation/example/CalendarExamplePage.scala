package com.cibo.uidocumentation.example

import com.cibo.ui.elements.Calendar
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object CalendarExamplePage {

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Calendar"))),
      <.div(^.cls := "main-view", <.div(^.cls := "example-block", Calendar()))
    )
  }

  val component = ScalaComponent.static("CalendarExample")(render())

}
