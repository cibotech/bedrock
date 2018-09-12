package com.cibo.ui.elements

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Statistic {

  sealed trait StatisticStatus { val className: String }
  case object Success extends StatisticStatus { val className = "success" }
  case object Default extends StatisticStatus { val className = "" }
  case object Warning extends StatisticStatus { val className = "warning" }
  case object Danger extends StatisticStatus { val className = "danger" }

  final case class Props(
      label: Text,
      value: String,
      units: Option[String],
      status: StatisticStatus
  )

  private val component = ScalaComponent
    .builder[Props]("Statistic")
    .render_P(props =>
      <.div(
        ^.cls := "statistic",
        <.div(
          ^.cls := "label",
          props.label
        ),
        <.div(
          ^.cls := s"value ${props.status.className}",
          props.value,
          if (props.units.isDefined)
            <.span(
              ^.cls := "units",
              props.units.get
            )
          else EmptyVdom
        )
    ))
    .build

  def apply(
      label: String,
      value: String,
      units: String = "",
      status: StatisticStatus = Default
  ) =
    component(
      Props(
        label = label,
        value = value,
        units = if (units != "") Some(units) else None,
        status = status
      ))
}
