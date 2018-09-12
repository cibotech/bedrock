package com.cibo.uidocumentation.example

import com.cibo.ui.ReactGridStrict.{column, row}
import com.cibo.ui.elements.Size.{ExtraLarge, Large, Medium, Small}
import com.cibo.ui.elements.Text.{Bold, Capitalized, ExtraBold, ExtraThin, LowerCase, Normal, NotTransformed, Thin, UpperCase}
import com.cibo.ui.elements.{Button, Text}
import com.cibo.ui.input.Form.form
import com.cibo.ui.input._
import com.cibo.ui.list.{BasicList, ElementList, ListItemElement, Listing}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react.{Callback, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import com.cibo.ui._

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
               |
               |  textExampleList(typesOfText),
               |
               |
               |
             |""".stripMargin
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("InputExample")(render())
}
