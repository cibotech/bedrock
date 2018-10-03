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

package com.cibo.bedrock.input

import com.cibo.bedrock.elements.Icon
import com.cibo.bedrock.notifications.Severity
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}
import org.scalajs.dom.html

class SelectInput[T] {

  case class Props(values: Seq[T],
                   initialValue: T,
                   visualizer: T => String,
                   onChange: T => Callback,
                   status: Option[Severity]
                  )

  case class State(current: T)

  class Backend(val $: BackendScope[Props, State]) {

    def handleChange(changePassthrough: T => Callback)(e: ReactEventFrom[html.Select]) = {
      e.persist()
      val props = $.props.runNow()
      $.modState(_.copy(current = props.values(e.target.selectedIndex))).runNow()
      changePassthrough(props.values(e.target.selectedIndex))
    }

    def render(p: Props, s: State) = {

      val values = p.values.map { v =>
        <.option(p.visualizer(v), ^.value := p.visualizer(v))
      }

      <.div( ^.cls := "select-wrapper",
        <.select(
          ^.cls := s"${p.status.map(_.repr).getOrElse("")}",
        ^.value := p.visualizer(s.current),
        ^.onChange ==> handleChange(p.onChange),
        values.toTagMod
        ),
        Icon.arrowDropDown.toDom.apply(^.cls := "select-arrow")
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("SelectInput")
    .initialStateFromProps(p => State(p.initialValue))
    .renderBackend[Backend]
    .build

  def newComponent(values: Seq[T], initialValue: T, visualizer: T=> String, onChange: T => Callback, status: Option[Severity] = None) =
    component(this.Props(values, initialValue, visualizer, onChange, status))

}

object SelectInput {
  val stringInput = new SelectInput[String]
  def apply(values: Seq[String], initialValue: String, onChange: String => Callback, status: Option[Severity] = None) =
    stringInput.newComponent(values, initialValue, identity, onChange, status)
}
