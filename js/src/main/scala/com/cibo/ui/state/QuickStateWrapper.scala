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

package com.cibo.ui.state

import com.cibo.ui.elements.Icon
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}
import org.scalajs.dom.html

class QuickStateWrapper[T]{

  type RenderArgs = (T, (T => T)=> CallbackTo[Unit])
  case class Props(render: RenderArgs => VdomElement, initialState: T)

  class Backend(val $: BackendScope[Props, T]) {

    def modState(t: T => T): CallbackTo[Unit] = {
      $.modState(t)
    }

    def render(p: Props, s: T) = {
      p.render((s, modState))
    }
  }

  private val component = ScalaComponent
    .builder[Props]("SelectInput")
    .initialStateFromProps(p => p.initialState)
    .renderBackend[Backend]
    .build

  def wrap(intitialState: T)(fn: RenderArgs => VdomElement) = {
    component(Props(fn, intitialState))
  }

}

object ShowHideWrapper {

  case class ShowHideArgs(isVisible: Boolean,
                          setVisible: CallbackTo[Unit],
                          setHidden: CallbackTo[Unit]
                         ){
    def isHidden: Boolean = !isVisible
  }

  case class State(visible: Boolean)
  case class Props(visible: Boolean, contents: ShowHideArgs => VdomElement)

  class Backend(val $: BackendScope[Props, State]) {

    def setVisible(): CallbackTo[Unit] = $.setState(State(true))
    def setHidden(): CallbackTo[Unit] = $.setState(State(false))

    def render(p: Props, s: State) = {
        p.contents(ShowHideArgs(
          s.visible,
          setVisible,
          setHidden
        ))
    }
  }

  private val component = ScalaComponent
    .builder[Props]("ShowHideWrapper")
    .initialStateFromProps(p => State(p.visible))
    .renderBackend[Backend]
    .build

  def apply(visible: Boolean)(fn: ShowHideArgs => VdomElement) = component(Props(visible, fn))

}