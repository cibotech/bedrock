package com.cibo.uidocumentation.example

import com.cibo.ui.elements.Statistic
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object StatisticExamplePage {

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Statistic"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          <.div(
            <.h4("Basic Statistic"),
            Statistic(
              label = "Total",
              value = "1000"
            ),
            CodeExample(
              "scala",
              s"""
                 |    // "label" and "value" are required properties
                 |    Statistic(
                 |        label = "Total",
                 |        value = "1000"
                 |    )
                 |
                 |""".stripMargin
            )
          )
        ),
        <.div(
          ^.cls := "example-block",
          <.div(
            <.h4("Statistic with Units"),
            Statistic(
              label = "temperature",
              value = "98.0",
              units = "°C"
            ),
            CodeExample(
              "scala",
              s"""
                 |    Statistic(
                 |        label = "temperature",
                 |        value = "98.0",
                 |        units = "°C"
                 |    )
                 |
                 |""".stripMargin
            )
          )
        ),
        <.div(
          ^.cls := "example-block",
          <.div(
            <.h4("Multiple Statistics"),
            Statistic(label = "Humidity", value = "74", units = "%"),
            Statistic(label = "temp.", value = "98.0", units = "°C"),
            Statistic(label = "Carbon Dioxide", value = "350", units = "ppm"),
            CodeExample(
              "scala",
              s"""
                 |    // Statistic components are set to "display: inline-block;" by default.
                 |    Statistic(label = "Humidity", value = "98", units = "%"),
                 |    Statistic(label = "temp.", value = "98.0", units = "°C"),
                 |    Statistic(label = "Carbon Dioxide", value = "350", units = "ppm"),
                 |
                 |""".stripMargin
            )
          )
        ),
        <.div(
          ^.cls := "example-block",
          <.div(
            <.h4("Statistics with Statuses"),
            Statistic(label = "success", value = "100.0", status = Statistic.Success, units = "°C"),
            Statistic(label = "default", value = "75.01", status = Statistic.Default, units = "°C"),
            Statistic(label = "warning", value = "48.87", status = Statistic.Warning, units = "°C"),
            Statistic(label = "danger", value = "1.84", status = Statistic.Danger, units = "°C"),
            CodeExample(
              "scala",
              s"""
                 |    Statistic(label = "success", value = "100.0", status=Statistic.Success, units="°C"),
                 |    Statistic(label = "default", value = "75.01", status=Statistic.Default, units="°C"),
                 |    Statistic(label = "warning", value = "48.87", status=Statistic.Warning, units="°C"),
                 |    Statistic(label = "danger", value = "1.84", status=Statistic.Danger, units="°C"),
                 |
                 |""".stripMargin
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("StatisticExample")(render())
}
