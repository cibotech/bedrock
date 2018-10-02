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


import com.cibo.bedrock.ReactGridStrict._
import com.cibo.bedrock.elements.{Button, Tabs}
import com.cibo.bedrock.elements.Tabs.Tab
import com.cibo.bedrock.state.{MultiWatcher, QuickStateWrapper, WatchingWrapper}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object StateExamplePage {

  val store = new MultiWatcher[Int](0)

  val WatchingWrapper = new WatchingWrapper[Int]

  class Backend($: BackendScope[Unit, Int]) {
    def render() = {


      <.div(
        ^.cls := "style-guide",
        <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("State"))),
        <.div(
          ^.cls := "main-view",
          <.div(
            ^.cls := "example-block",
            row(
              column(12)(
                testComponentTwo(),
                WatchingWrapper(store){ x =>
                  <.div(
                    (0 to x).map{ _ =>
                      <.div(WatchingWrapper(store)(x => testComponentOne(x)))
                    }.toTagMod
                  )
                }
              )
            )
          )
        )
      )
    }
  }



  private val testComponentOne = ScalaComponent
    .builder[Int]("ButtonExamples")
    .render( $ => <.div(s"Value: ${$.props}"))
    .build



  private val testComponentTwo = ScalaComponent
    .builder[Unit]("StateButtonExamples")
    .render{ $ =>
      <.div(
        Button("Add wrapped watcher").onClick(Callback(store.modState(x => x + 1))),
        Button("Add 10 watchers").onClick(Callback(store.modState(x => x + 10))),
        Button("Remove a wrapped watcher").onClick(Callback(store.modState(x => x - 1))),
        Button("Clear wrapped watchers").onClick(Callback(store.setState(0)))
      )
    }.build


  private val component = ScalaComponent
    .builder[Unit]("StateExample")
    .initialState(0)
    .renderBackend[Backend]
    .build

  def apply() = component()
}

