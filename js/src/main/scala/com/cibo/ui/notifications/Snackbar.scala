package com.cibo.ui.notifications

import java.util.UUID

import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import org.scalajs.dom.raw._
import com.cibo.ui.Logging._
import com.cibo.ui.notifications.Severity.Info

import scala.scalajs.js
import scala.scalajs.js.timers.setTimeout

trait Alert {
  def message: HTMLElement
  val durationMs: Double
  val severity: Severity
}

sealed trait Severity {
  def repr: String
}

object Severity {
  case object Success extends Severity {
    def repr = "bd-success"
  }

  case object Warning extends Severity {
    def repr = "bd-warning"
  }

  case object Error extends Severity {
    def repr = "bd-error"
  }

  case object Info extends Severity {
    def repr = "bd-info"
  }
}


case class Snack(text: String, durationMs: Double = 3000, severity: Severity = Info) extends Alert {

  def message = {
    val element = dom.document.createElement("span").asInstanceOf[HTMLSpanElement]
    element.innerHTML = text
    element
  }
}

case class LinkSnack(text: String, link: String, durationMs: Double, severity: Severity = Info) extends Alert {

  def message = {
    val element = dom.document.createElement("a").asInstanceOf[HTMLAnchorElement]
    element.href = link
    element.innerHTML = text
    element.rel = "noopener noreferrer"
    element
  }
}

object GlobalAlertSystem {

  case class Props()
  case class State()

  val bottomLeftAlertId = "bottom-left-alerts"
  val bottomRightAlertId = "bottom-right-alerts"

  val fadeBuffer = 300

  def createExitButton(onClick: MouseEvent => Unit) = {
    val exitButton = dom.document.createElement("button").asInstanceOf[HTMLButtonElement]
    exitButton.setAttribute("class", "small round repressed")
    exitButton.innerHTML = """<i class="material-icons icon medium">close</i>"""
    exitButton.onclick = onClick
    exitButton
  }

  def pushAlertToTarget(alert: Alert, target: String) = {
    val alertId = UUID.randomUUID().toString
    val bottomLeftElement = dom.document.getElementById(target)

    // scalastyle:off
    // null is necessary for js craziness
    if(bottomLeftElement != null){
    // scalastyle:on
      
      val alertInner = alert.message

      val alertContainer = dom.document.createElement("div").asInstanceOf[HTMLDivElement]
      alertContainer.id = alertId
      alertContainer.setAttribute("class", s"alert-container ${alert.severity.repr}")
      alertContainer.appendChild(alertInner)

      val exitButton = createExitButton({ _: MouseEvent => bottomLeftElement.removeChild(alertContainer) })
      alertContainer.appendChild(exitButton)

      bottomLeftElement.appendChild(alertContainer)

      setTimeout(alert.durationMs){
        alertContainer.setAttribute("class", s"alert-container ${alert.severity.repr} fade-out")
      }

      setTimeout(alert.durationMs + fadeBuffer){
        bottomLeftElement.removeChild(alertContainer)
      }
    } else {
      logger.error("Alert system not present in app, try adding GlobalAlertSystem() to root component")
    }

    Callback.empty
  }

  def pushToBtmLeft(alert: Alert) = {
    pushAlertToTarget(alert, bottomLeftAlertId)
  }

  def pushToBtmRight(alert: Alert) = {
    pushAlertToTarget(alert, bottomRightAlertId)
  }

  class Backend($: BackendScope[Props, State]) {

    def render(state: State, props: Props) = {
      <.div( ^.cls := "global-alert-system",
        <.div( ^.id := bottomLeftAlertId),
        <.div( ^.id := bottomRightAlertId)
      )
    }
  }

  val component = ScalaComponent.builder[Props]("LayerPickerElement")
    .initialStateFromProps( p => State())
    .renderBackend[Backend]
    .build


  def apply() = component(Props())
}
