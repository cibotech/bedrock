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

import com.cibo.ui.pane.{Pane, PaneHeader}
import japgolly.scalajs.react.{Callback, CallbackTo}
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^.{<, EmptyVdom, ^}
import japgolly.scalajs.react.vdom.html_<^._

object Modals {

  def wrapWithRawModal(show: Boolean = true, onBackgroundClick: Option[CallbackTo[Unit]] = None)(tagMod: TagMod*) = {
    <.div( ^.classSet1("modal-wrapper","show" -> show, "hide" -> !show),
      <.div( ^.cls := "modal-view",
        <.div( ^.cls := "modal",
          tagMod.toTagMod
        )
      ),
      <.div( ^.cls := "modal-background",
        onBackgroundClick.map( x => ^.onClick --> x ).getOrElse(EmptyVdom)
      )
    )
  }

  def wrapWithClosingPane(show: Boolean, onCloseClick: CallbackTo[Unit], tagMod: TagMod*) = {
    val button = Button(Icon.close, click = onCloseClick).small.rounded.secondary

    wrapWithRawModal(show, Some(
      onCloseClick
    ))(
      Pane(header = Some(PaneHeader(interactions = Seq(button))))(
        tagMod :_*
      )
    )
  }

}

