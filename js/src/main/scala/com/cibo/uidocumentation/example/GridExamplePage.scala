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

import com.cibo.bedrock.ReactGridStrict.{column, columnLg, columnMd, row}
import com.cibo.bedrock.list.{ElementList, ListItemElement, Listing}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import com.cibo.bedrock._
object GridExamplePage {

  def DashedDiv(tagMod: TagMod*) = <.div(
    ^.border := "2px dashed grey",
    ^.padding := "0.25em",
    ^.textAlign := "center",
    ^.height := "100%",
    ^.boxSizing := "border-box",
    tagMod.toTagMod
  )

  val pageContent = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Grid System"))),
      <.div(
        ^.cls := "main-view",
        <.div("Use Bedrock's Grid System to create complex layouts."),
        Listing(bulleted = true)(
          ListItemElement(
            "A Grid System layout is made up of ",
            "rows".bold,
            " that contain ",
            "columns".bold,
            "."
          ),
          ListItemElement(
            "A ",
            "row".bold,
            " can contain up to 12 column-widths."
          )
        ),
        <.div(
          <.h4("Basic Row"),
          row(
            column(4)(DashedDiv("column(4)")),
            column(4)(DashedDiv("column(4)")),
            column(4)(DashedDiv("column(4)"))
          ),
          CodeExample(
            "scala",
            s"""
               |    // A single row with three equally sized columns.
               |    row(
               |        // Note that rows and columns are minimally styled for maximum flexibility.
               |        // The "DashedDiv" is an example component used to show column boundaries.
               |        column(4)(DashedDiv("column(4)")),
               |        column(4)(DashedDiv("column(4)")),
               |        column(4)(DashedDiv("column(4)"))
               |    )
               |
               |""".stripMargin
          )
        ),
        <.div(
          <.h4("Nested Grid"),
          row(
            column(12)(DashedDiv("column(12)"))
          ),
          row(
            column(4)(DashedDiv("column(4)")),
            column(8)(
              row(
                column(6)(DashedDiv("column(6)")),
                column(6)(DashedDiv("column(6)"))
              ),
              row(
                column(5)(DashedDiv("column(5)")),
                column(7)(DashedDiv("column(7)"))
              )
            )
          ),
          CodeExample(
            "scala",
            s"""
               |    // Be wary of using complex layouts on small screens.
               |    row(
               |      column(12)(DashedDiv("column(12)"))
               |    ),
               |    row(
               |      column(4)(DashedDiv("column(4)")),
               |      column(8)(
               |        row(
               |          column(6)(DashedDiv("column(6)")),
               |          column(6)(DashedDiv("column(6)"))
               |        ),
               |        row(
               |          column(5)(DashedDiv("column(5)")),
               |          column(7)(DashedDiv("column(7)"))
               |        )
               |      )
               |    )
               |
             |""".stripMargin
          )
        ),
        <.div(
          <.h4("Overflown Row"),
          row(
            column(4)(DashedDiv("column(4)")),
            column(5)(DashedDiv("column(5)")),
            column(6)(DashedDiv("column(6)"))
          ),
          CodeExample(
            "scala",
            s"""
               |    // Columns that exceed the 12 column-widths of a row will wrap to a new line.
               |    row(
               |        column(4)(DashedDiv("column(4)")),
               |        column(5)(DashedDiv("column(5)")),
               |        column(6)(DashedDiv("column(6)"))
               |    )
               |
                 |""".stripMargin
          )
        ),
        <.div(
          <.h4("Centered Row"),
          row(center = true)(
            column(4)(DashedDiv("column(4)")),
            column(5)(DashedDiv("column(5)")),
            column(6)(DashedDiv("column(6)"))
          ),
          CodeExample(
            "scala",
            s"""
               |    // A row can horizontally center it's columns using the "center" prop.
               |    row(center = true)(
               |        column(4)(DashedDiv("column(4)")),
               |        column(5)(DashedDiv("column(5)")),
               |        column(6)(DashedDiv("column(6)"))
               |    )
               |
                 |""".stripMargin
          )
        ),
        <.div(
          <.h4("Responsive Columns"),
          row(
            column(4)(DashedDiv("always 4 wide")),
            column(4)(DashedDiv("always 4 wide")),
            column(4)(DashedDiv("always 4 wide"))
          ),
          row(
            columnMd(4)(DashedDiv("4 wide at medium screen widths")),
            columnMd(4)(DashedDiv("4 wide at medium screen widths")),
            columnMd(4)(DashedDiv("4 wide at medium screen widths"))
          ),
          row(
            columnLg(4)(DashedDiv("4 wide at large screen widths")),
            columnLg(4)(DashedDiv("4 wide at large screen widths")),
            columnLg(4)(DashedDiv("4 wide at large screen widths"))
          ),
          CodeExample(
            "scala",
            s"""
               |    // There are two responsive column components:
               |    // columnMd fills its row unless the screen is at a "medium" width. (756px)
               |    // columnLg fills its row unless the screen is at a "large" width. (1024px)
               |    row(
               |      column(4)(DashedDiv("always 4 wide")),
               |      column(4)(DashedDiv("always 4 wide")),
               |      column(4)(DashedDiv("always 4 wide"))
               |    ),
               |    row(
               |      columnMd(4)(DashedDiv("4 wide at medium screen widths")),
               |      columnMd(4)(DashedDiv("4 wide at medium screen widths")),
               |      columnMd(4)(DashedDiv("4 wide at medium screen widths"))
               |    ),
               |    row(
               |      columnLg(4)(DashedDiv("4 wide at large screen widths")),
               |      columnLg(4)(DashedDiv("4 wide at large screen widths")),
               |      columnLg(4)(DashedDiv("4 wide at large screen widths"))
               |    ),
               |
                 |""".stripMargin
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("GridExample")(pageContent)
}
