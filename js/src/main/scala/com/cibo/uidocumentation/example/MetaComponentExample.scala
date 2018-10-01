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

package com.cibo.uidocumentation.example

import com.cibo.bedrock.Padding
import com.cibo.bedrock.ReactGridStrict.{column, row}
import com.cibo.bedrock.elements.Button
import com.cibo.bedrock.input.Form._
import com.cibo.bedrock.input.{CheckBoxInput, TextInput, ToggleInput}
import com.cibo.bedrock.pane.{Pane, PaneHeader}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import com.cibo.bedrock._
import com.cibo.uidocumentation.SourceLink
object MetaComponentExample {

  case class Props()
  case class State(name: String, likesCats: Boolean, isABoss: Boolean)

  class Backend(val $ : BackendScope[Props, State]) {

    def render(p: Props, s: State) = {
      Pane(
        Some(PaneHeader(Some("Form Example".bold)))
      )(
        row(
          Padding(10),
          column(12)(
            spreadForm("I'm a boss",
                       ToggleInput(
                         toggled = s.isABoss,
                         onChange = { aBoss =>
                           $.modState(_.copy(isABoss = aBoss))
                         }
                       ))
          ),
          column(12)(
            spreadForm("Name", TextInput(initialValue = s.name, onChange = { name =>
              $.modState(_.copy(name = name))
            }))
          ),
          column(12)(
            spreadForm("I like cats",
                       CheckBoxInput(
                         checked = s.likesCats,
                         onChange = { likesCats =>
                           $.modState(_.copy(likesCats = likesCats))
                         }
                       ))
          ),
          column(12)(
            formElement(
              Button("click me".medium).small.margin(Some(Margin(5, 0, 0, 0)))
                .onClick(Callback { println("clicked") })
            )
          ),
          SourceLink("MetaComponentExample.scala")

        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("EnterpriseReport")
    .initialStateFromProps(p => State("", true, true))
    .renderBackend[Backend]
    .build

  def apply() =
    component(Props())

}
