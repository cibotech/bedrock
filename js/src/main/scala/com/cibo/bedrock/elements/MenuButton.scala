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

package com.cibo.bedrock.elements


import com.cibo.bedrock.Padding
import com.cibo.bedrock.list.Listing
import com.cibo.bedrock.pane.Pane
import com.cibo.bedrock.state.QuickStateWrapper
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import org.scalajs.dom.MouseEvent

import scala.scalajs.js

case class MenuButton(button: Button, menu: TagMod*) {

  def toDom() = {
    MenuButton.stateWrapper.wrap(false) { case (state, stateUpdate) =>

      <.div(^.cls := "menu-button",
        button.toDom.apply(
          ^.onBlur --> stateUpdate{_ => false},
          ^.onMouseUp ==> { e =>
            e.persist()
            val statePersist = if(state) true else false
            stateUpdate{_ =>
              e.target.asInstanceOf[js.Dynamic].focus() // because safari and firefox are dumb
              !statePersist}
          }
        ),
        <.div(^.classSet1("absolute-menu-list", "show" -> state, "hide" -> !state),
          Pane(padding = Some(Padding(0)))(menu: _*)
        )
      )
    }.vdomElement
  }

}

object MenuButton {

  private[elements] val stateWrapper = new QuickStateWrapper[Boolean]
  implicit def toTagMod(b: MenuButton): TagMod = b.toDom

}