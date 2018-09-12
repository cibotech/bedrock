package com.cibo.uidocumentation.example

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.elements.{Button, Tabs}
import com.cibo.ui.elements.Tabs.Tab
import com.cibo.ui.notifications.{GlobalAlertSystem, Severity, Snack}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object NotificationExamplePage {

  def render() = {

    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Navigation"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
            column(12)(
              <.h4("Global Alert System"),
              CodeExample(
                "scala",
                s"""
                   |GlobalAlertSystem() // put this component in the root of your app
                """.stripMargin
              )
            ),
            column(12)(
              <.h4("Snackbar"),
              row(
                column(12)(
                  Button("Info alert left").apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmLeft(Snack(s"Info alert"))),
                  Button("Error alert left").error.apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmLeft(Snack("Error alert", severity = Severity.Error))),
                  Button("Success alert left").success.apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmLeft(Snack("Success alert", severity = Severity.Success)))
                )
              ),
              row(
                column(12)(
                  Button("Info alert right").apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmRight(Snack(s"Info alert"))),
                  Button("Error alert right").error.apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmRight(Snack("Error alert", severity = Severity.Error))),
                  Button("Success alert right").success.apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmRight(Snack("Success alert", severity = Severity.Success)))
                )
              ),
              row(
                column(12)(
                  Button("Alert with lots of text, avoid this").apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmLeft(Snack(s"Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                      s"sed do eiusmod tempor incididunt ut " +
                      s"labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                      s"ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                      s" Duis aute irure dolor in reprehenderit in voluptate velit " +
                      s"esse cillum dolore eu fugiat nulla pariatur."))),
                )
              ),
              CodeExample(
                "scala",
                s"""
                   |  Button("Info alert").apply(^.onClick -->
                   |    GlobalAlertSystem.pushToSnackbar(Snack(s"Info alert ${System.currentTimeMillis()}"))),
                   |  Button("Error alert").error.apply(^.onClick -->
                   |    GlobalAlertSystem.pushToSnackbar(Snack("Error alert", severity = Severity.Error))),
                   |  Button("Success alert").success.apply(^.onClick -->
                   |    GlobalAlertSystem.pushToSnackbar(Snack("Success alert", severity = Severity.Success)))
                      """.stripMargin
              )
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("NavigationExample")(render())
}
