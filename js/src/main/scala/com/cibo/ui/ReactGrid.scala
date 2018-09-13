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

package com.cibo.ui

import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.{html => *}

object ReactGridStrict {

  def row(reactTags: TagMod*): VdomElement = <.div(^.cls := "row strict", reactTags.toTagMod)

  def row(vcenter: Boolean = false, center: Boolean = false, fill: Boolean = false)(
      reactTags: TagMod*): VdomElement =
    <.div(^.className := s"row strict",
          reactTags.toTagMod,
          ^.classSet("center" -> center, "vcenter" -> vcenter, "fill" -> fill))

  def column(width: Int)(reactTags: TagMod*): VdomElement =
    <.div(^.cls := s"column strict-$width", reactTags.toTagMod)
  def columnMd(width: Int)(reactTags: TagMod*): VdomElement =
    <.div(^.cls := s"column strict-md-$width", reactTags.toTagMod)
  def columnLg(width: Int)(reactTags: TagMod*): VdomElement =
    <.div(^.cls := s"column strict-lg-$width", reactTags.toTagMod)

}

object ReactGridFlex {
  def rowFlex(reactTags: TagMod*): VdomElement = <.div(^.cls := "row", reactTags.toTagMod)
  def rowFlexCenter(reactTags: TagMod*): VdomElement = <.div(^.cls := "row center", reactTags.toTagMod)

  def column(width: Int)(reactTags: TagMod*): VdomElement = <.div(^.cls := s"column", reactTags.toTagMod)
}
