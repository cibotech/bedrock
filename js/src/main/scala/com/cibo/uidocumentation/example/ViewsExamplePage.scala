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

package com.cibo.uidocumentation.example

import com.cibo.ui.ReactGridStrict.{column, row}
import com.cibo.ui.elements.Size.{ExtraLarge, Large, Medium, Small}
import com.cibo.ui.elements.Text.{Bold, Capitalized, ExtraBold, ExtraThin, LowerCase, Normal, NotTransformed, Thin, UpperCase}
import com.cibo.ui.elements.{Button, Icon, Text}
import com.cibo.ui.input.Form.form
import com.cibo.ui.input._
import com.cibo.ui.list._
import com.cibo.uidocumentation.{CodeExample, SourceLink}
import japgolly.scalajs.react.{Callback, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import com.cibo.ui._
import com.cibo.ui.Padding
import com.cibo.ui.compound.FilteringContentView
import com.cibo.ui.pane.Pane
import japgolly.scalajs.react.component.builder.Lifecycle
import japgolly.scalajs.react.vdom.html_<^

object ViewsExamplePage {

  val icons = Seq(
    Icon.callReceived,
    Icon.cancel,
    Icon.dock,
    Icon.flag,
    Icon.call,
    Icon.face,
    Icon.pregnantWoman,
    Icon.insertEmoticon,
    Icon.computer
  )

  val filterableListView: FilteringContentView[Icon] = new FilteringContentView[Icon]{


    override def listItemRenderer(ib: ItemBinding[Icon]): html_<^.TagMod = {
      <.li(
        Padding(10),
        ^.cls := "listItem",
        ^.classSet("focused" -> ib.isFocused),
        ^.onClick --> ib.select(ib.element),
        <.div(^.cls := "iconWrapper", ib.element),
        <.span(^.cls := "label", ib.element.iconName)
      ).noselect
    }

    val stringFilteringList: StringFilteringList[Icon] = new StringFilteringList[Icon] {
      override def filterFunction(item: Icon, filterString: String): Boolean = item.iconName.contains(filterString)

      override def listWrapper(selecting: Boolean, inner: html_<^.TagMod): html_<^.TagMod = <.div(
        ^.classSet1("absolute-list", "selecting" -> selecting),
        Pane(padding = Some(Padding(0)))(Listing(divided = true)(inner))
      )
    }

    def renderIcon(icon: Icon) = {
      column(3)(
        row(
          column(12)(s"${icon.size.cssName}".bold),
          column(12)(icon)
        )
      ).centerText
    }

    def activeContentRenderer(element: Option[Icon]) = element match {
      case Some(icon) =>
        Pane()(Padding(10),
          row(
            renderIcon(icon.small),
            renderIcon(icon.medium),
            renderIcon(icon.large),
            renderIcon(icon.extraLarge)
          )
        )
      case None => Pane()(Padding(10),
        row(column(12)("Please select an Icon".extraLarge.bold).centerText)
      )
    }
  }

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Views"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
             column(12)(
              <.div(
                <.h4("Filterable Content View Example"),
                row( Padding(10), ^.border := "1px solid grey", column(12)(
                  filterableListView(icons)
                )),
                SourceLink.example("ViewsExamplePage.scala")
              )
             )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("ViewsExample")(render())
}

