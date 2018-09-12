package com.cibo.ui.plots
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.html_<^._

/**
  * PlotContext creates a hidden canvas element with id "measureBuffer" for use
  * with EvilPlot. Rendering this once at the entry point of your app suffices
  * for displaying plots throughout it.
  */
object PlotContext {
  private val hidden = VdomAttr("hidden")
  private def measureBuffer = {
    <.canvas(^.id := "measureBuffer", hidden := true)
  }
  private val component = ScalaComponent.static("PlotContext")(measureBuffer)
  def apply(): Unmounted[Unit, Unit, Unit] = component()
}
