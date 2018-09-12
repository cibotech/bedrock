package com.cibo.uidocumentation.example

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.elements.{Button, Icon, Text}
import com.cibo.ui.pane.{LoadablePane, Pane, PaneHeader}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import com.cibo.ui.elements.Text._
import com.cibo.uidocumentation.CodeExample
import com.cibo.ui._

object PaneExamplePage {

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
              Pane()(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 1"))
              )
            ),
            column(6)(
              Pane()(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 2"))
              )
            ),
            column(6)(
              Pane()(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 3"))
              )
            ),
            column(12)(CodeExample( "scala",
              """
                | column(12)(
                |   Pane()(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 1"))
                |   )
                | ),
                | column(6)(
                |   Pane()(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 2"))
                |   )
                | ),
                | column(6)(
                |   Pane()(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 3"))
                |   )
                | ),
              """.stripMargin
            )),
            column(6)(
              Pane(
                Some(PaneHeader(title = Some(Text("Pane With Header"))))
              )(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Content"))
              )
            ),
            column(6)(
              Pane(
                Some(PaneHeader(
                  title = Some("Pane With Controls"),
                  Seq(
                    Button(Icon.add).secondary.small.rounded.onClick(Callback(println("Add Hit"))),
                    Button(Icon.close).secondary.small.rounded.onClick(Callback(println("Close Hit")))
                  )
                ))
              )(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Content"))
              )
            ),
            column(12)(CodeExample( "scala",
              """
                | column(6)(
                |   Pane(
                |     Some(PaneHeader(title = Some(Text("Pane With Header"))))
                |   )(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Content"))
                |   )
                | ),
                | column(6)(
                |   Pane(
                |     Some(PaneHeader(
                |       title = Some("Pane With Controls"),
                |       Seq(
                |         Control(Callback(println("Control Add Hit")), Icon.add),
                |         Control(Callback(println("Control Close Hit")), Icon.close)
                |       )
                |     ))
                |   )(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Content"))
                |   )
                | ),
              """.stripMargin
            )),
            column(12)(
              Pane(lowProfile = true)(
                <.div(<.h4(^.cls := "title", "Normal Pane")).centerText
              )
            ),
            column(12)(CodeExample( "scala",
              """
                |column(12)(
                |  Pane(lowProfile = true)(
                |    <.div(^.cls := "text-center", <.h4(^.cls := "title", "Normal Pane"))
                |  )
                |),
              """.stripMargin
            )),
            LoadablePane(6)(Some("Finished")),
            LoadablePane(6)(None)
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("InputExample")(render())
}
