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

package com.cibo.ui.list

import com.cibo.ui.elements.{Icon, Text}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import org.scalajs.dom

case class ListItemElement(content: TagMod,
                           header: Option[Text] = None,
                           icon: Option[TagMod] = None,
                           href: Option[String] = None,
                           label: Option[TagMod] = None,
                           onClick: Callback = Callback.empty,
                           isActive: Boolean = false){

  def header(text: Some[Text]): ListItemElement = copy(header = text)
  def icon(icon: Option[TagMod]): ListItemElement = copy(icon = icon)
  def href(href: Option[String]): ListItemElement = copy(href = href)
  def label(label: Option[TagMod]): ListItemElement = copy(label = label)
  def onClick(onClick: Option[Callback]): ListItemElement = copy(onClick = onClick.getOrElse(Callback.empty))
  def isActive(isActive: Boolean): ListItemElement = copy(isActive = isActive)
  def content(content: TagMod*): ListItemElement = copy(content = content.toTagMod(identity))

  def toVdom = <.li(
    ^.cls := "listItem",
    ^.classSet(
      "active" -> isActive
    ),
    ^.onClick --> onClick,
    if (icon.isDefined) {
      <.div(^.cls := "iconWrapper", icon.get)
    } else {
      EmptyVdom
    },
    <.div(
      ^.cls := "contentWrapper",
      if (header.isDefined) {
        <.div(^.cls := "header", header.get)
      } else {
        EmptyVdom
      },
      if (href.isDefined) {
        <.a(^.cls := "content", ^.href := href.get, content)
      } else {
        <.span(^.cls := "content", content)
      }
    ),
    if (label.isDefined) <.span(^.cls := "label", label.get) else EmptyVdom
  )

}

object ListItemElement {

  def apply(content: TagMod*): ListItemElement = new ListItemElement(content.toTagMod)

  implicit def listItemToVdom(listItemElement: ListItemElement): VdomElement = listItemElement.toVdom

}
