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

import com.cibo.bedrock.{Margin, Padding}
import com.cibo.bedrock.elements.Size.{Large, Medium, Small}
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.vdom.html_<^._
import com.cibo.bedrock._
import com.cibo.bedrock.Margin._
sealed abstract class ButtonSignificance(val cssName: String)

case class Button(content: Either[Text, TagMod],
                  click: Callback,
                  size: Size,
                  significance: ButtonSignificance,
                  round: Boolean,
                  margin: Option[Margin]) extends StandardSize[Button]{

  import Button._
  def toDom: TagMod = {

    val inner: TagMod = content.fold(
      x => x.copy(size = size),
      x => x
    )

    <.button(
      ^.classSet(
        size.cssName -> true,
        "round" -> round,
        significance.cssName -> true
      ),
      margin.map(toTagMod).getOrElse(EmptyVdom),
      inner,
      ^.onClick --> click
    )
  }

  def changeSize(size: Size) = copy(size = size)

  def margin(margin: Option[Margin]) = copy(margin = margin)

  def primary = copy(significance = Primary)
  def secondary = copy(significance = Secondary)
  def mild = copy(significance = Mild)
  def repressed = copy(significance = Repressed)
  def error = copy(significance = Error)
  def success = copy(significance = Success)

  def rounded = copy(round = true)
  def rectangular = copy(round = false)

  def onClick(cb: Callback) = copy(click = cb)
}


object Button {

  def apply(content: TagMod,
            click: Callback = Callback.empty,
            size: Size = Medium,
            significance: ButtonSignificance = Button.Primary,
            round: Boolean = false,
            buttonSizedText: Boolean = true,
            margin: Option[Margin] = None): Button = {

    new Button(Right(content), click, size, significance, round, margin)
  }

  def apply(content: TagMod*): Button = {
    new Button(Right(content.toTagMod), Callback.empty, Medium, Button.Primary, false, None)
  }

  def text(text: Text,
            click: Callback = Callback.empty,
            size: Size = Medium,
            significance: ButtonSignificance = Button.Primary,
            round: Boolean = false,
            margin: Option[Margin] = None): Button = {

    new Button(Left(text), click, size, significance, round, margin)
  }

  case object Primary extends ButtonSignificance("")
  case object Secondary extends ButtonSignificance("secondary")
  case object Mild extends ButtonSignificance("mild")
  case object Repressed extends ButtonSignificance("repressed")
  case object Error extends ButtonSignificance("error")
  case object Success extends ButtonSignificance("success")

  implicit def buttonToTagMod(b: Button): TagMod = b.toDom
}
