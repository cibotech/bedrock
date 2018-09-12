package com.cibo.uidocumentation.example

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.elements.Icon
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object IconExamplePage {

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Icons"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
            column(12)(
              <.h4("Basic Icon Usage"),
              CodeExample("scala",
                          s"""
                   |Icon.iconName()
                   |Icon.iconName.size()
                 """.stripMargin)
            )
          ),
          row(
            column(12)(
              <.h4("Available Sizes"),
              row(Icon.keyboardArrowRight.small),
              row(CodeExample("scala", "Icon.keyboardArrowRight.small")),
              row(Icon.keyboardArrowRight()),
              row(CodeExample("scala", "Icon.keyboardArrowRight")),
              row(Icon.keyboardArrowRight.large),
              row(CodeExample("scala", "Icon.keyboardArrowRight.large")),
              row(Icon.keyboardArrowRight.extraLarge),
              row(CodeExample("scala", "Icon.keyboardArrowRight.extraLarge"))
            )
          ),
          row(
            column(12)(
              <.h4("Available Icons"),
              <.p(
                "Icons from Google's Material Design Icon Set. Library can be found ",
                <.a(^.href := "https://material.io/icons/", "here"),
                "."
              )
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("IconExample")(render())
}
