/*
 * Copyright (c) 2018, CiBO Technologies, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
