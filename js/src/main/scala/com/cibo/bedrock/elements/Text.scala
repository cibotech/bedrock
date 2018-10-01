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

import com.cibo.bedrock.elements.Size.{ExtraLarge, Large, Medium, Small}
import com.cibo.bedrock.elements.Text._
import japgolly.scalajs.react.vdom.html_<^.TagMod
import japgolly.scalajs.react.vdom.html_<^._

sealed abstract class TextWeight(val cssName: String)
sealed abstract class TextTransform(val cssName: String)

case class Text(value: String,
                size: Size = Medium,
                weight: TextWeight = Normal,
                transform: TextTransform = NotTransformed,
                paragraph: Boolean = false
               ) extends StandardSize[Text]{

  def toDom = {
    val element = {
      if (paragraph) <.p
      else <.span
    }
    element(
      ^.classSet1("bd-text", size.cssName -> true, weight.cssName -> true, transform.cssName -> true),
      value
    )
  }

  def changeSize(size: Size) = copy(size = size)

  def extraThin = copy(weight = ExtraThin)
  def thin = copy(weight = Thin)
  def normal = copy(weight = Normal)
  def bold = copy(weight = Bold)
  def extraBold = copy(weight = ExtraBold)

  def capitalized = copy(transform = Capitalized)
  def lowerCase = copy(transform = LowerCase)
  def upperCase = copy(transform = UpperCase)
  def noTransform = copy(transform = NotTransformed)
  def toParagraph = copy(paragraph = true)
  def text = copy(paragraph = false)

}

object Text {

  case object ExtraThin extends TextWeight("extra-thin")
  case object Thin extends TextWeight("thin")
  case object Normal extends TextWeight("")
  case object Bold extends TextWeight("bold")
  case object ExtraBold extends TextWeight("extra-bold")

  case object Capitalized extends TextTransform("capitalized")
  case object LowerCase extends TextTransform("lower-case")
  case object UpperCase extends TextTransform("upper-case")
  case object NotTransformed extends TextTransform("not-transformed")

  implicit def textToTagMod(t: Text): TagMod = t.toDom

}

