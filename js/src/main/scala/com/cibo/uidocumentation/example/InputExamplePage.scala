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

import com.cibo.bedrock.ReactGridStrict._
import com.cibo.bedrock.elements.{Button, Icon, MenuButton, Text}
import com.cibo.bedrock.input.Form._
import com.cibo.bedrock.input._
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import com.cibo.bedrock.{Padding, _}
import com.cibo.bedrock.list.{ListItemElement, Listing}

object InputExamplePage {

  def render() = {
    FileUtils.disableWindowDragDrop()

    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Input"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
            column(12)(
              form("Toggle".bold, ToggleInput(toggled = true, onChange = { bool: Boolean =>
                Callback(println(s"Changed to ${bool}"))
              }))
            ),
            column(12)(
              CodeExample(
                "scala",
                s"""
                   |Toggle( toggled = true, onChange = { trueFalse: Boolean =>
                   |    Callback(println(s"Changed to {trueFalse}"))
                   | })
                 """.stripMargin
              )
            ),
            column(12)(
              form("Checkbox".bold, CheckBoxInput(checked = true, onChange = { bool: Boolean =>
                Callback(println(s"Changed to ${bool}"))
              }))
            ),
            column(12)(
              CodeExample(
                "scala",
                s"""
                   |CheckboxInput(checked = true, onChange = { trueFalse: Boolean =>
                   |    Callback(println(s"Changed to {trueFalse}"))
                   | })
                 """.stripMargin
              )
            ),
            column(12)(
              form("Checkbox".bold, CheckBoxInput.withLabel(checked = true, label = "I have a label", onChange = { bool: Boolean =>
                Callback(println(s"Changed to ${bool}"))
              }))
            ),
            column(12)(
              CodeExample(
                "scala",
                s"""
                   |CheckboxInput(checked = true, label = "I have a label", onChange = { trueFalse: Boolean =>
                   |    Callback(println(s"Changed to {trueFalse}"))
                   | })
                 """.stripMargin
              )
            ),
            column(12)(
              form("Select".bold,
                   SelectInput(Seq("this", "is", "a", "test"), initialValue = "test", onChange = {
                     selected: String =>
                       Callback(println(s"Changed to ${selected}"))
                   }))
            ),
            column(12)(
              column(12)(
                CodeExample(
                  "scala",
                  s"""
                     | SelectInput(Seq("this", "is", "a", "test"), initialValue = "test", onChange = {
                     |   selected: String =>
                     |     Callback(println(s"Changed to selected"))
                     | }))
                 """.stripMargin
                )
              )
            ),
            column(12)(
              form("Text".bold, TextInput("InitialText", onChange = { selected: String =>
                Callback(println(s"Input is"))
              }))
            ),
            column(12)(
              column(12)(
                CodeExample(
                  "scala",
                  s"""
                     |TextInput("InitialText",
                     |  onChange = { selected: String =>
                     |    Callback(println(s"Input is"))
                     |  }
                     |)
                 """.stripMargin
                )
              )
            ),
            column(12)(
              form("Text".bold, TextInput.withPlaceHolder("This is placeholder text", onChange = { selected: String =>
                Callback(println(s"Input is"))
              }))
            ),
            column(12)(
              column(12)(
                CodeExample(
                  "scala",
                  s"""
                     |TextInput.withPlaceholder("This is placeholder text",
                     |  onChange = { selected: String =>
                     |    Callback(println(s"Input is"))
                     |  }
                     |)
                 """.stripMargin
                )
              )
            ),
            column(12)(
              form("Slider".bold, SliderInput.Int(0, 100, 5, 10, onChange = { value =>
                Callback(println(value))
              }))
            ),
            column(12)(
              column(12)(
                CodeExample(
                  "scala",
                  s"""
                     |SliderInput.Int(0, 100, 5, 1
                     |  onChange = { value =>
                     |    Callback(println(value))
                     |  }
                     |)
                 """.stripMargin
                )
              )
            ),
            column(12)(
              <.h4("Upload with Drag Drop"),
              row(
                Padding(20),
                column(12)(
                  UploadComponent(files => Callback(files.foreach(y => println(y.name))))
                )),
              <.h4("Upload without Drag Drop"),
              row(
                Padding(20),
                column(12)(
                  UploadComponent(files => Callback(files.foreach(y => println(y.name))), dragDropEnabled = false)
                )
              ),
              row(
                column(12)(
                  CodeExample("scala",
                    """
                      | //with drag drop
                      | UploadComponent(x => Callback(x.foreach(y => println(y.name))))
                      |
                      | //without drag
                      | UploadComponent(x => Callback(x.foreach(y => println(y.name))), dragDropEnabled = false)
                    """.stripMargin
                  )
                )
              )
            ),
            column(12)(
              <.h4("Buttons"),
              row(column(12)(
                  <.h4("Small"),
                  Button.text("click me".capitalized).small,
                  Button.text("secondary".capitalized).small.secondary,
                  Button.text("mild".capitalized).small.mild,
                  Button(Icon.menu.small).small.rounded
                ),
                column(12)(
                  CodeExample(
                    "scala",
                    s"""
                       |Button.text("click me".capitalized).small,
                       |Button.text("secondary".capitalized).small.secondary,
                       |Button.text("mild".capitalized).small.mild,
                       |Button(Icon.menu.small).small.rounded
                       |""".stripMargin
                  )
                )),
              row(column(12)(
                  <.h4("Normal"),
                  Button.text("click me"),
                  Button.text("secondary").secondary,
                  Button.text("mild").mild,
                  Button.text("repressed").repressed,
                  Button(Icon.add.medium).rounded
                ),
                column(12)(
                  CodeExample(
                    "scala",
                    s"""
                       |Button.text("click me"),
                       |Button.text("secondary").secondary,
                       |Button.text("mild").mild,
                       |Button.text("repressed").repressed,
                       |Button(Icon.add.medium).rounded
                       |""".stripMargin
                  )
                )),
              row(column(12)(
                  <.h4("Large"),
                  Button.text("click me").large,
                  Button.text("secondary").large.secondary,
                  Button.text("mild").large.mild,
                  Button(Icon.computer.medium).large.rounded
                ),
                column(12)(
                  CodeExample(
                    "scala",
                    s"""
                       |Button.text("click me").large,
                       |Button.text("secondary").large.secondary,
                       |Button.text("mild").large.mild,
                       |Button(Icon.computer.medium).large.rounded
                       |""".stripMargin
                  )
                )),
              row(column(12)(
                <.h4("On Click"),
                Button.text("CLICK ME!").large.onClick(
                  Callback{
                    dom.window.open("https://www.youtube.com/watch?v=oHg5SJYRHA0&autoplay=true")
                  }
                )
              ),
                column(12)(
                  CodeExample(
                    "scala",
                    s"""Button("CLICK ME!").large.onClick(
                       |   Callback{
                       |     ...
                       |   }
                       | )
                 """.stripMargin
                  )
                )),
              row(column(12)(
                <.h4("Menu Button"),
                MenuButton(Button("Options".lowerCase, Icon.arrowDropDown.small),
                  Listing(divided = true)(
                    ListItemElement("Option 1".bold).toVdom(Padding(7, 14)),
                    ListItemElement("Option 2".bold).toVdom(Padding(7, 14)),
                    ListItemElement("Option 3".bold).toVdom(Padding(7, 14))
                  )
                ).toDom.noselect,
                MenuButton(Button(Icon.menu.small).rounded.repressed,
                  Listing(divided = true)(
                    ListItemElement("Option 1".bold).toVdom(Padding(7, 14)),
                    ListItemElement("Option 2".bold).toVdom(Padding(7, 14)),
                    ListItemElement("Option 3".bold).toVdom(Padding(7, 14))
                  )
                ).toDom.noselect
              ),
              column(12)(
                CodeExample(
                  "scala",
                  s""" MenuButton(Button("Options".lowerCase, Icon.arrowDropDown.small),
                     |   Listing(divided = true)(
                     |     ListItemElement("Option 1".bold).toVdom(Padding(7, 14)),
                     |     ListItemElement("Option 2".bold).toVdom(Padding(7, 14)),
                     |     ListItemElement("Option 3".bold).toVdom(Padding(7, 14))
                     |   )
                     | ).toDom.noselect,
                     | MenuButton(Button(Icon.menu.small).rounded,
                     |   Listing(divided = true)(
                     |     ListItemElement("Option 1".bold).toVdom(Padding(7, 14)),
                     |     ListItemElement("Option 2".bold).toVdom(Padding(7, 14)),
                     |     ListItemElement("Option 3".bold).toVdom(Padding(7, 14))
                     |   )
                     | ).toDom.noselect
               """.stripMargin
                )
              )),
              row(column(12)(
                <.h4("Complex Dropdowns"),
                MenuButton(Button(Icon.volumeUp.small).rounded,
                  <.table(
                    Padding(5),
                    <.tr(
                     <.td("Bass".bold, Padding(5)),
                     <.td(SliderInput.Int(0, 100, 1, 50, x => Callback(println(x))), Padding(5))
                    ),
                    <.tr(
                      <.td("Trebble".bold, Padding(5)),
                      <.td(SliderInput.Int(0, 100, 1, 50, x => Callback(println(x))), Padding(5))
                    )
                  )
                )
              ),
              column(12)(
                CodeExample(
                  "scala",
                  s"""
                     |MenuButton(Button(Icon.volumeUp.small).rounded,
                     |  <.table(
                     |    Padding(5),
                     |    <.tr(
                     |     <.td("Bass".bold, Padding(5)),
                     |     <.td(SliderInput.Int(0, 100, 1, 50, x => Callback(println(x))), Padding(5))
                     |    ),
                     |    <.tr(
                     |      <.td("Trebble".bold, Padding(5)),
                     |      <.td(SliderInput.Int(0, 100, 1, 50, x => Callback(println(x))), Padding(5))
                     |    )
                     |  )
                     |)""".stripMargin
                )
              ))
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("InputExample")(render())
}
