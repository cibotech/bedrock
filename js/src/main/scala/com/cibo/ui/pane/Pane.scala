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

package com.cibo.ui.pane

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.elements.{Button, Icon, Text}
import com.cibo.uidocumentation.LoadingSVG
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import Text._
import com.cibo.ui._
object Pane {
  case class Props(header: Option[PaneHeader], lowProfile: Boolean, padding: Option[Padding], content: TagMod*)
  class Backend(val $ : BackendScope[Props, Unit]) {

    def render(p: Props) = {


      <.div(^.classSet1("card", "with-header" -> p.header.isDefined),
        p.header.map(_.toTagMod).getOrElse(EmptyVdom),
        <.div( ^.cls := "card-wrapper",
            p.padding.map(_.tagMod).getOrElse(EmptyVdom),
            ^.classSet("low-profile" -> p.lowProfile),
            <.div( ^.cls := "card-content-container",
              p.content.toTagMod(identity)
            )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("Pane")
    .renderBackend[Backend]
    .build

  def apply(header: Option[PaneHeader] = None, lowProfile: Boolean = false, padding: Option[Padding] = None)(content: TagMod*) = {
    component(Props(header, lowProfile, padding, content: _*))
  }

}

case class PaneHeader(title: Option[Text] = None,
                      interactions: Seq[Button] = Seq(),
                      textStyle: Text => Text = _.bold.medium){

  def toTagMod: TagMod = {
    <.div(^.cls := "card-header",
      <.span(^.cls := "title", textStyle(title.getOrElse(Text(""))), Padding(10)),
      <.div(^.cls := "controls", interactions.map(_.toDom).toTagMod(identity)))
  }
}

object LoadablePane {

  case class Props(thing: Option[String])

  class Backend(val $ : BackendScope[Props, Unit]) {

    val loading = row(vcenter = true, center = true, fill = true)(
      column(12)(
        <.div(^.cls := "pane-loading-indicator", LoadingSVG.primary)
      )
    )

    def render(p: Props) = {

      val state = if (!p.thing.isDefined) "loading" else ""
      <.div(
        ^.cls := s"card text-center ${state}",
        ^.height := "300px",
        if (p.thing.isDefined) <.h4(^.cls := "title", "Finished Loading")
        else loading
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("LoadablePane")
    .renderBackend[Backend]
    .build

  def apply(size: Int)(thing: Option[String]) =
    column(size)(component(Props(thing)))

}
