package com.cibo.ui.plots

import com.cibo.evilplot.geometry._
import com.cibo.evilplot.plot._
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.BackendScope
import japgolly.scalajs.react.vdom.VdomElement
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.{Event, html}
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.CanvasRenderingContext2D
import com.cibo.evilplot.plot.aesthetics._
import org.scalajs.dom

import scala.scalajs.js

object PlotComponent {
  case class Props(r: Plot,
                   size: Extent,
                   theme: Theme,
                   fillX: Boolean)

  case class State()

  class Backend($ : BackendScope[Props, State]) {
    val canvasResolutionScaleHack = 2

    var canvasRef: html.Canvas = _ // scalastyle:ignore
    var containerRef: html.Div = _ // scalastyle:ignore

    def render(props: Props, state: State): VdomElement = {
      <.div( ^.cls := "bd-plot-container",
        <.canvas().ref(canvasRef = _)
      ).ref(containerRef = _)
    }

    def drawPlot(props: Props) = Callback {

      val scaledWidth = if(props.fillX){
        containerRef.clientWidth.toDouble
      } else {
        props.size.width
      }

      val paddingHack: Double = 20
      val scene = props.r.render(props.size.copy(width = scaledWidth))(props.theme)
      val paddedSize = Extent(scene.extent.width - paddingHack, scene.extent.height - paddingHack)
      val paddedScene = fit(scene padAll paddingHack / 2, paddedSize)
      val renderCtx = resizeCanvas(canvasRef, scene.extent, paddingHack)
      paddedScene.draw(renderCtx)
    }

    def resizeCanvas(canvas: Canvas,
                     extent: Extent,
                     padding: Double): CanvasRenderContext = {
      val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

      ctx.canvas.width = extent.width.toInt * canvasResolutionScaleHack
      ctx.canvas.height = extent.height.toInt * canvasResolutionScaleHack

      ctx.scale(canvasResolutionScaleHack, canvasResolutionScaleHack)
      CanvasRenderContext(ctx)
    }

    val onResize: js.Function1[Event, _] = { x: Event =>
      if(canvasRef != null){
        drawPlot($.props.runNow()).runNow()
      } else {
        // just incase the component doesn't unmount
        dom.window.removeEventListener("resize", onResize)
      }
    }
  }

  private val component = ScalaComponent
    .builder[Props]("Plot Component")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount($ => {

      dom.window.addEventListener("resize", $.backend.onResize)

      $.raw.backend.drawPlot($.props)
    })
    .componentDidUpdate($ => $.raw.backend.drawPlot($.currentProps))
    .componentWillUnmount{ $ =>

      dom.window.removeEventListener("resize", $.backend.onResize)

      Callback.empty
    }
    .build

  /**
    * A component that wraps and displays EvilPlot plot.
    * Using this component requires the existence of a canvas element with id "measureBuffer".
    * @see PlotContext
    * @param plot an EvilPlot plot object.
    * @param size the size to render this plot object into.
    **/
  def apply(plot: Plot,
            size: Extent = Plot.defaultExtent,
            theme: Theme = DefaultTheme.defaultTheme,
            fillX: Boolean = false) = {
    component(Props(plot, size, theme, fillX))
  }
}
