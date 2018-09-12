package com.cibo.uidocumentation.example

import com.cibo.ui.ReactGridStrict.{column, row}
import com.cibo.ui.elements.Size.{ExtraLarge, Large, Medium, Small}
import com.cibo.ui.list.Listing
import com.cibo.uidocumentation.{CodeExample, LoadingSVG, LoadingSpinner}
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import com.cibo.ui._
object LoadingExamplePage {

  val loadingSpinners = Seq(Small, Medium, Large, ExtraLarge).map(LoadingSpinner(_))

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Panes"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
            column(12)(
              <.div(
                <.h4(^.cls := "title", "Primary Loading Indicator")
              ),
              LoadingSVG.primary
            )
          ),
          CodeExample("scala",
                      s"""
               | <.div(
               |   LoadingSVG.primary
               | )
               |
               |""".stripMargin),
          row(
            column(12)(
              <.div(
                <.h4(^.cls := "title", "Loading spinners")
              ),
              row(
                loadingSpinners.map{ spinner =>
                  column(3)(
                    row(
                      column(12)(spinner.size.cssName.bold),
                      column(12)(spinner)
                    )
                  )
                }.toTagMod(identity)
              )
            )
          ),
          CodeExample("scala",
            s"""
               | LoadingSpinner().small
               | LoadingSpinner().medium
               | LoadingSpinner().large
               | LoadingSpinner().extraLarge
               |""".stripMargin)
        )
      )
    )
  }

  val component = ScalaComponent.static("InputExample")(render())

}
