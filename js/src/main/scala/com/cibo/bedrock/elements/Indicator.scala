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

import com.cibo.bedrock.elements.Indicator.Neutral
import com.cibo.bedrock.elements.Size.Medium
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

abstract class IndicatorStatus(val className: String)

case class Indicator(status: IndicatorStatus = Neutral,
                     size: Size = Medium,
                     attached: Boolean = false,
                     onClick: Callback = Callback.empty) extends StandardSize[Indicator] {

  def toDom() = {
    <.div(
      ^.onClick --> onClick,
      ^.cls := s"status-indicator ${status.className} ${size.cssName}",
      ^.classSet(
        "attached" -> attached,
        "clickable" -> (onClick != Callback.empty),
      )
    )
  }

  def changeSize(size: Size) = copy(size = size)
}

object Indicator {

  case object Success extends IndicatorStatus("success")
  case object Neutral extends IndicatorStatus("neutral")
  case object Warning extends IndicatorStatus("warning")
  case object Danger extends IndicatorStatus("danger")

  implicit def iconToTagMod(i: Indicator): TagMod = i.toDom()

}
