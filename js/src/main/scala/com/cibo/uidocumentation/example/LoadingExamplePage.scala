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

import com.cibo.bedrock.ReactGridStrict.{column, row}
import com.cibo.bedrock.elements.Size.{ExtraLarge, Large, Medium, Small}
import com.cibo.bedrock.list.Listing
import com.cibo.uidocumentation.{CodeExample, LoadingSVG, LoadingSpinner}
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import com.cibo.bedrock._
object LoadingExamplePage {

  val loadingSpinners = Seq(Small, Medium, Large, ExtraLarge).map(LoadingSpinner(_))

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Panes"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
            column(12)(
              <.div(
                <.h4(^.cls := "title", "Primary Loading Indicator")
              ),
              LoadingSVG.primary
            )
          ),
          CodeExample("scala",
                      s"""
               | <.div(
               |   LoadingSVG.primary
               | )
               |
               |""".stripMargin),
          row(
            column(12)(
              <.div(
                <.h4(^.cls := "title", "Loading spinners")
              ),
              row(
                loadingSpinners.map{ spinner =>
                  column(3)(
                    row(
                      column(12)(spinner.size.cssName.bold),
                      column(12)(spinner)
                    )
                  )
                }.toTagMod(identity)
              )
            )
          ),
          CodeExample("scala",
            s"""
               | LoadingSpinner().small
               | LoadingSpinner().medium
               | LoadingSpinner().large
               | LoadingSpinner().extraLarge
               |""".stripMargin)
        )
      )
    )
  }

  val component = ScalaComponent.static("InputExample")(render())

}
