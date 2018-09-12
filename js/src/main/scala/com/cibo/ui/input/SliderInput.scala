package com.cibo.ui.input

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scala.math.Ordering.Implicits._

final class SliderInput[T: Numeric] private[SliderInput] () {

  case class Props(min: T, max: T, step: T, initialValue: T, onChange: T => Callback)
  case class State(current: T)

  class Backend($ : BackendScope[Props, State]) {

    def handleChange(changePassthrough: T => Callback)(e: ReactEventFromInput) = {
      e.persist()
      val newValue = e.target.value.toDouble.asInstanceOf[T]
      $.modState(_.copy(current = newValue)).runNow()
      changePassthrough(newValue)
    }

    def render(props: Props, state: State) = {
      <.div(
        ^.cls := "slider-input-wrapper",
        <.input(
          ^.cls := "slider-input",
          ^.`type` := "range",
          ^.min := props.min.toString,
          ^.max := props.max.toString,
          ^.step := props.step.toString,
          ^.value := state.current.toString,
          ^.onChange ==> handleChange(props.onChange)
        )
      )
    }
  }

  val component = ScalaComponent
    .builder[Props]("SliderInput")
    .initialStateFromProps(p => State(p.initialValue))
    .renderBackend[Backend]
    .build

  def apply(min: T, max: T, step: T, initialValue: T, onChange: T => Callback) = {
    assert(initialValue >= min && initialValue <= max, s"initial value $initialValue should be between min($min) and max($max)")
    assert(min < max, s"min($min) should be between larger than max($max)")
    val difference = implicitly[Numeric[T]].minus(max,min)
    assert( difference >= step, s"step ($step) should not be larger than the difference between min($min) and max($max)")
    component(Props(min, max, step, initialValue, onChange))
  }
}
object SliderInput {
  val Int = new SliderInput[Int]
  val Long = new SliderInput[Long]
  val Double = new SliderInput[Double]
}
