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

import com.cibo.bedrock.ReactGridStrict.{column, row}
import com.cibo.bedrock.elements.Size.{ExtraLarge, Large, Medium, Small}
import com.cibo.bedrock.elements.Text.{Bold, Capitalized, ExtraBold, ExtraThin, LowerCase, Normal, NotTransformed, Thin, UpperCase}
import com.cibo.bedrock.elements.{Button, Text}
import com.cibo.bedrock.input.Form.form
import com.cibo.bedrock.input._
import com.cibo.bedrock.list.{BasicList, ElementList, ListItemElement, Listing}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react.{Callback, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import com.cibo.bedrock._

object TextExamplePage {

  val textExampleList = new BasicList[Text] {

    override val listingWrapper = Listing(relaxed = true)

    def renderListItem(item: Text) = ListItemElement(item)
  }

  val typesOfText = for{
    size <- Seq(Small, Medium, Large, ExtraLarge)
    weight <- Seq(ExtraThin, Thin, Normal, Bold, ExtraBold)
    transform <- Seq(NotTransformed, Capitalized, UpperCase, LowerCase)
  } yield {
    Text(s"${size.getClass.getSimpleName} ${weight.getClass.getSimpleName} ${transform}", size = size, weight = weight, transform = transform)
  }

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Text"))),
      <.div(
        ^.cls := "main-view",
        <.div(^.cls := "example-block",
          <.h4("Text Combinator"),
          textExampleList(
            Seq(Text("large thin capitialized").large.thin.capitalized,
              Text("small extraBold lowercase").small.extraBold.lowerCase,
              "implictly extraLarge extraBold".extraLarge.extraBold)),
          CodeExample(
            "scala",
            """
              |
              |  Text("large thin capitialized").large.thin.capitalized
              |  Text("small extraBold lowercase").small.extraBold.lowerCase
              |
              |  or implicitly
              |  import com.cibo.ui._
              |  "implictly extraLarge extraBold".extraLarge.extraBold
              |
              |""".stripMargin
          ),
          <.h4("Text"),
          textExampleList(typesOfText),
          CodeExample(
            "scala",
            """
              |  val typesOfText = for{
              |    size <- Seq(Small, Medium, Large, ExtraLarge)
              |    weight <- Seq(ExtraThin, Thin, Normal, Bold, ExtraBold)
              |    transform <- Seq(NotTransformed, Capitalized, UpperCase, LowerCase)
              |  } yield {
              |    Text(s"${size.getClass.getSimpleName} ${weight.getClass.getSimpleName} ${transform}", size = size, weight = weight, transform = transform)
              |  }
             |""".stripMargin
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("InputExample")(render())
}
