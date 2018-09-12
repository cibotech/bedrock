package com.cibo.uidocumentation

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.navigation.NavigationPage
import com.cibo.uidocumentation.DocumentationRouter._
import com.cibo.uidocumentation.example.MetaComponentExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.document
import org.scalajs.dom.ext.PimpedNodeList
import org.scalajs.dom.html.LI

object DocsHomePage {

  def docsPage(page: NavigationPage[DocumentationPage]): VdomTagOf[LI] =
    <.li(
      <.a(page.name, ^.href := GlobalNavState.getCtl.urlFor(page.link).value)
    )

  def applySyntaxHighlight = Callback {
    import scala.scalajs.js.Dynamic.{global => g}
    val nodeList = document.querySelectorAll("pre code").toArray
    nodeList.foreach(g.window.hljs highlightBlock _)
  }

  def static() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Bedrock"))),
      <.div(
        ^.cls := "main-view",
        column(12)(
          <.h4("Components"),
          <.ul(
            TagMod.Composite(
              DocumentationRouter.pages.map(docsPage).toVector
            )
          )
        ),
        column(12)(
          <.h4("Scala Dependencies"),
          <.h5(
            <.a(
              "Find latest version here",
              ^.href := "https://cibotech.jfrog.io/cibotech/webapp/#/artifacts/browse/tree/General/libs-release-local/com/cibo/bedrock_sjs0.6_2.12"
            )),
          CodeExample("scala",
                      s"""
                 | "com.cibo" %%% "bedrock" % bedrockVersion
                """.stripMargin)
        ),
        column(12)(
          <.h4("Style Dependencies"),
          CodeExample(
            "html",
            s"""
                 | Add include this to your html header
                 | <link rel="stylesheet" href="https://bedrock.cibo.tech/resources/css/main.css" />
                """.stripMargin
          )
        ),
        column(12)(
          <.h4("Example Component"),
          row(
            column(6)(
              MetaComponentExample()
            ),
            column(12)(
              CodeExample(
                "scala",
                """
                  |Pane(
                  |  Some(PaneHeader(Some("Form Example".bold)))
                  |)(
                  |  row(
                  |    Padding(10),
                  |    column(12)(
                  |      spreadForm("I'm a boss",
                  |                 ToggleInput(
                  |                   toggled = s.isABoss,
                  |                   onChange = { aBoss =>
                  |                     $.modState(_.copy(isABoss = aBoss))
                  |                   }
                  |                 ))
                  |    ),
                  |    column(12)(
                  |      spreadForm("Name", TextInput(initialValue = s.name, onChange = { name =>
                  |        $.modState(_.copy(name = name))
                  |      }))
                  |    ),
                  |    column(12)(
                  |      spreadForm("I like cats",
                  |                 CheckBoxInput(
                  |                   checked = s.likesCats,
                  |                   onChange = { likesCats =>
                  |                     $.modState(_.copy(likesCats = likesCats))
                  |                   }
                  |                 ))
                  |    ),
                  |    column(12)(
                  |      formElement(
                  |        Button("click me".medium, buttonSizedText = false).small.margin(Some(Margin(5, 0, 0, 0)))
                  |          .onClick(Callback { println("clicked") })
                  |      )
                  |    )
                  |  )
                  |)
                 """.stripMargin
              )
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("DocsHomePage")(static())
}
