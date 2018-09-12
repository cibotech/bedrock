package com.cibo.ui.elements

import com.cibo.ui.elements.Indicator.Neutral
import com.cibo.ui.elements.Size.Medium
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

abstract class IndicatorStatus(val className: String)

case class Indicator(status: IndicatorStatus = Neutral,
                     size: Size = Medium,
                     attached: Boolean = false,
                     onClick: Callback = Callback.empty) extends StandardSize[Indicator] {

  def toDom() = {
    <.div(
      ^.onClick --> onClick,
      ^.cls := s"status-indicator ${status.className} ${size.cssName}",
      ^.classSet(
        "attached" -> attached,
        "clickable" -> (onClick != Callback.empty),
      )
    )
  }

  def changeSize(size: Size) = copy(size = size)
}

object Indicator {

  case object Success extends IndicatorStatus("success")
  case object Neutral extends IndicatorStatus("neutral")
  case object Warning extends IndicatorStatus("warning")
  case object Danger extends IndicatorStatus("danger")

  implicit def iconToTagMod(i: Indicator): TagMod = i.toDom()

}
