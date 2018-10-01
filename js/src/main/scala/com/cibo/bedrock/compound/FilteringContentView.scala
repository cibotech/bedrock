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

package com.cibo.bedrock.compound

import com.cibo.bedrock.Padding
import com.cibo.bedrock.ReactGridStrict.{column, row}
import com.cibo.bedrock.input.Form.form
import com.cibo.bedrock.input.TextInput
import com.cibo.bedrock.list.{ItemBinding, StringFilteringList}
import com.cibo.bedrock.pane.Pane
import japgolly.scalajs.react.{Callback, ScalaComponent}
import japgolly.scalajs.react.component.builder.Lifecycle.RenderScope
import japgolly.scalajs.react.vdom.html_<^._


abstract class FilteringContentView[T]{

  case class FilteringContentState(selected: Option[T])

  val stringFilteringList: StringFilteringList[T]

  def activeContentRenderer(element: Option[T]): TagMod

  def listItemRenderer(ib: ItemBinding[T]): TagMod

  protected def render(p: Seq[T], s: Option[T], backend: RenderScope[Seq[T], Option[T], Unit]) = {
    row(
      column(3)(stringFilteringList(p, listItemRenderer, x => backend.setState(Some(x)))),
      column(9)(activeContentRenderer(s))
    )
  }

  val component = ScalaComponent.builder[Seq[T]]("FilteringContentView")
    .initialState(Option.empty[T])
    .render( x => render(x.props, x.state, x))
    .build

  def apply(seq: Seq[T]) = component(seq)

}
