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

import com.cibo.bedrock.Margin
import com.cibo.bedrock.elements.Size.Medium
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.vdom.html_<^
import japgolly.scalajs.react.vdom.html_<^._

case class ButtonGroup(
                      buttons: Seq[Button],
                      size: Size ,
                      significance: ButtonSignificance,
                      roundedEdges: Boolean,
                      borders: Boolean) extends StandardSize[ButtonGroup] {

  def toDom: html_<^.TagMod = <.div( ^.classSet1(
    s"bd-button-group ${size.cssName} ${significance.cssName}",
    "borders" -> borders,
    "rounded" -> roundedEdges
  ),
    buttons.toTagMod(_.changeSize(size))
  )

  def changeSize(size: Size): ButtonGroup = this.copy(size = size)
}


object ButtonGroup {

  def apply(
             buttons: Seq[Button],
             size: Size = Medium,
             significance: ButtonSignificance = Button.Secondary,
             roundedEdges: Boolean = true,
             borders: Boolean = false
           ): ButtonGroup = {

    new ButtonGroup(buttons, size, significance, roundedEdges, borders)
  }

  implicit def buttonGroupToTagMod(b: ButtonGroup): TagMod = b.toDom

}