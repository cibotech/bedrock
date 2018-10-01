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

package com.cibo.bedrock.list

import com.cibo.bedrock.Padding
import com.cibo.bedrock.input.Form.form
import com.cibo.bedrock.input.TextInput
import japgolly.scalajs.react.component.builder.Lifecycle.RenderScope
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._
import com.cibo.bedrock.ReactGridStrict._
import com.cibo.bedrock.pane.Pane
import com.cibo.bedrock.state.{AsyncData, AsyncEmpty}
import org.scalajs.dom.ext.KeyCode

abstract class ElementList[T] {

  def renderListItem(item: T): TagMod

  def renderListItems(items: Seq[T]): VdomElement = {
    <.ul(
      items.toTagMod(renderListItem)
    )
  }

  protected def render(p: Seq[T]) = renderListItems(p)

  val component = ScalaComponent.builder[Seq[T]]("Listing")
    .render( x => render(x.props))
    .build

  def apply(seq: Seq[T]) = component(seq)

}

case class ItemBinding[T](select: T => Callback, element: T, isFocused: Boolean)

abstract class StringFilteringList[T]{


  case class State(filterString: String, focusedElement: Option[Int], currentElement: Option[T], selecting: Boolean)
  case class Props(seq: Seq[T], renderer: ItemBinding[T] => TagMod, onSelect: T => Callback)

  def filterFunction(item: T, filterString: String): Boolean

  def listWrapper(selecting: Boolean, inner: TagMod): TagMod = <.ul(inner)

  protected def renderListItems(filteredItems: Seq[T], p: Props, s: State, backend: RenderScope[Props, State, Unit]): TagMod = {

    listWrapper(
      selecting = s.selecting,
      filteredItems.zipWithIndex.toTagMod{ case (x, idx) => p.renderer(ItemBinding({ x =>
        p.onSelect(x).thenRun(backend.modState(_.copy(focusedElement = None))).runNow()
      }, x, s.focusedElement.getOrElse(-1) == idx))}
    )
  }

  protected def render(p: Props, s: State, backend: RenderScope[Props, State, Unit]) = {
    val filteredItems = p.seq.filter(item => filterFunction(item, s.filterString))

    val filterInput = {
      form("Filter",
        TextInput(s.filterString,
          onChange = { text => backend.modState(_.copy(text)) },
          onKeypress = Some( keypress => backend.modState{ state =>
            val keycode = keypress.keyCode
            if(keycode == KeyCode.Down || keycode == KeyCode.Up) {
              val focusedElementIdx = state.focusedElement.map { idx =>
                if (keycode == KeyCode.Down) {
                  (idx + 1).min(filteredItems.seq.length - 1)
                } else if (keycode == KeyCode.Up) {
                  (idx - 1).max(0)
                } else idx
              }.getOrElse(0)
              state.copy(focusedElement = Some(focusedElementIdx))
            } else if(keycode == KeyCode.Enter){
              val element = state.focusedElement.map(filteredItems.seq(_))
              element.foreach(p.onSelect(_).runNow())
              state.copy(currentElement = element)
            } else state
          })
        ))
    }
    row(
      ^.onBlur --> backend.modState(_.copy(selecting = false)),
      ^.onFocus --> backend.modState(_.copy(selecting = true)),
      column(12)(filterInput),
      column(12)(
        renderListItems(filteredItems, p, s, backend)
      )
    )
  }

  val component = ScalaComponent.builder[Props]("Listing")
    .initialState(State("", None, None, false))
    .render( x => render(x.props, x.state, x))
    .build

  def apply(seq: Seq[T], renderer: ItemBinding[T] => TagMod, onSelect: T => Callback) = component(Props(seq, renderer, onSelect))

}

abstract class BasicList[T] extends ElementList[T] {

  val listingWrapper = Listing()_

  def renderListItem(item: T): TagMod

  override def renderListItems(items: Seq[T]): VdomElement = {
    listingWrapper(
      items.map(renderListItem)
    )
  }

}