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

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}

object TextInput {

  case class Props(
    initialValue: String,
    placeHolder: Option[String],
    onChange: String => Callback,
    onKeypress: Option[ReactKeyboardEvent => Callback],
    onFocus: Option[Boolean => Callback]
  )
  case class State(current: String)

  class Backend(val $ : BackendScope[Props, State]) {

    def handleChange(changePassthrough: String => Callback)(e: ReactEventFromInput): Callback  = {
      e.persist()
      $.modState(_.copy(current = e.target.value)).runNow()
      changePassthrough(e.target.value)
    }

    def handleKeyboardEvent(eventPassthrough: ReactKeyboardEvent => Callback)(e: ReactKeyboardEvent): Callback  = {
      e.persist()
      eventPassthrough(e)
    }

    def render(p: Props, s: State) = {
      val focusMod = p.onFocus.map(e => Seq(^.onFocus --> e(true), ^.onBlur --> e(false))
                      .toTagMod(identity)).getOrElse(EmptyVdom)
      <.input(
        ^.placeholder := placeholderText(p),
        ^.value := s.current,
        ^.onChange ==> handleChange(p.onChange),
        focusMod,
        p.onKeypress.map(^.onKeyDown ==> handleKeyboardEvent(_)).getOrElse(EmptyVdom),
        ^.`type` := "text"
      )
    }

    private def placeholderText(p: Props) = {
      p.placeHolder match {
        case Some(text) => text
        case None => ""
      }
    }
  }

  private val component = ScalaComponent
    .builder[Props]("TextInput")
    .initialStateFromProps(p => State(p.initialValue))
    .renderBackend[Backend]
    .build

  def apply(initialValue: String, onChange: String => Callback,
            onKeypress: Option[ReactKeyboardEvent => Callback] = None,
            onFocus: Option[Boolean => Callback] = None) =
    component(Props(initialValue, None, onChange, onKeypress, onFocus))

  def withPlaceHolder(placeHolder: String, onChange: String => Callback,
                      onKeypress: Option[ReactKeyboardEvent => Callback] = None,
                      onFocus: Option[Boolean => Callback] = None) =
    component(Props("", Some(placeHolder), onChange, onKeypress, onFocus))
}
