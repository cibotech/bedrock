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

package com.cibo.ui.elements

import com.cibo.ui.ReactGridStrict._
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, ScalaComponent}

object Tabs {

  case class Tab(name: String, dom: TagMod*)
  case class State(currentTab: Tab)
  case class Props(initialTab: Tab, tabs: Seq[Tab])

  class Backend($ : BackendScope[Props, State]) {

    def render(props: Props, state: State) = {
      row(
        column(12) {
          <.div(
            ^.cls := "tabs",
            props.tabs.toTagMod { tab =>
              <.div(^.cls := s"tab ${if (tab == state.currentTab) "current" else ""}",
                    tab.name,
                    ^.onClick --> $.modState(x => x.copy(currentTab = tab)))
            }
          )
        },
        column(12) {
          row(state.currentTab.dom.toTagMod)
        }
      )
    }
  }

  val component = ScalaComponent
    .builder[Props]("Tabs")
    .initialStateFromProps(p => State(p.initialTab))
    .renderBackend[Backend]
    .build

  def apply(initialTab: Tab, tabs: Seq[Tab]) =
    component(Props(initialTab, tabs))

}
