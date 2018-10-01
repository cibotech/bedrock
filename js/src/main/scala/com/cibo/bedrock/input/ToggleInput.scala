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

import com.cibo.bedrock.Util
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}

object BooleanInput
trait BooleanInput{
  case class Props(toggled: Boolean, onChange: Boolean => Callback, inline: Boolean = false, label: Option[String] = None)
  case class State(toggled: Option[Boolean])
}

object ToggleInput extends BooleanInput {

  class Backend(val $: BackendScope[Props, State]) {
    def toggle(state: State, props: Props) = {
      props.onChange(!state.toggled.getOrElse(props.toggled)).runNow()
      $.modState(_.copy(toggled = Some(!state.toggled.getOrElse(props.toggled))))
    }
    def render(p: Props, s: State) = {
      val status = s.toggled match {
        case Some(e) => e
        case None if p.toggled => "initial-true"
        case None if !p.toggled => "initial-false"
      }

      <.div( ^.cls := s"toggle ${if(p.inline) "inline" else ""} $status",
        <.div( ^.cls := "toggle-indicator", ^.onClick --> toggle(s, p)),
        <.div( ^.cls := "interior-wrapper", ^.onClick --> toggle(s, p),
          <.div( ^.cls := "interior-indicator")
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("ToggleInput")
    .initialStateFromProps(p => State(None))
    .renderBackend[Backend]
    .build

  def apply(toggled: Boolean, onChange: Boolean => Callback) =
    component(Props(toggled, onChange))

}

object CheckBoxInput extends BooleanInput {

  class Backend(val $: BackendScope[Props, State]) {
    def toggle(state: State, props: Props) = {
      props.onChange(!state.toggled.getOrElse(props.toggled)).runNow()
      $.modState(_.copy(toggled = Some(!state.toggled.getOrElse(props.toggled))))
    }
    def render(p: Props, s: State) = {
      val status = s.toggled match {
        case Some(e) => e
        case None if p.toggled => true //"initial-true"
        case None if !p.toggled => false //"initial-false"
      }


      val inputTagMod = <.input( ^.`type` := "checkbox", ^.checked := status, ^.onChange --> toggle(s, p))

      p.label match {
        case Some(label) => Util.withLabel(label, inputTagMod)
        case None => inputTagMod
      }
    }
  }

  private val component = ScalaComponent.builder[Props]("CheckboxInput")
    .initialStateFromProps(p => State(None))
    .renderBackend[Backend]
    .build

  def apply(checked: Boolean, onChange: Boolean => Callback) =
    component(Props(checked, onChange))

  def withLabel(checked: Boolean, label: String, onChange: Boolean => Callback) = {
    component(Props(checked, onChange, false, Some(label)))
  }

}