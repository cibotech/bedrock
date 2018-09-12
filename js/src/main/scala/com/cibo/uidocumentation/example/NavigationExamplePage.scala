package com.cibo.uidocumentation.example

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.elements.Tabs
import com.cibo.ui.elements.Tabs.Tab
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object NavigationExamplePage {

  def render() = {

    val tabs = Seq(
      Tab("one", column(12) {
        <.div(^.cls := "tabs-example", <.h4("Tab One"))
      }),
      Tab("two", column(12) {
        <.div(^.cls := "tabs-example", <.h4("Tab Two"))
      })
    )

    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Navigation"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
            column(12)(
              <.h4("Global Navigation Definition"),
              CodeExample(
                "scala",
                s"""
                   |object DocumentationNav extends Navigation[DocumentationPage] {
                   |  def router : RouterCtl[DocumentationPage] = DocumentationRouter.ctl
                   |}
                      """.stripMargin
              )
            ),
            column(12)(
              <.h4("Global Navigation Usage"),
              CodeExample(
                "scala",
                s"""
                   |  DocumentationNav.init()
                   |
                   |  val navigation: Seq[NavigationPage[DocumentationPage]] = Seq(
                   |    NavigationPage("Home", HomePage),
                   |    NavigationPage("Grid System", GridDocs),
                   |    NavigationPage("Calendar", CalendarDocs),
                   |    NavigationPage("Input Elements", InputDocs),
                   |    NavigationPage("Table", TableDocs),
                   |  )
                   |
                   |
                   |  def layout(c: RouterCtl[DocumentationPage], r: Resolution[DocumentationPage]) = {
                   |    ctl = c
                   |    <.div(^.cls := "dashboard-viewport", DocumentationNav(r.page, navigation, "UI"), r.render())
                   |  }
                      """.stripMargin
              )
            ),
            column(12)(
              <.h4("Tabs Example"),
              Tabs(initialTab = tabs.head, tabs)
            ),
            column(12)(
              <.h4("Tabs Example Code"),
              CodeExample(
                "scala",
                s"""
                   |
                   |    // definition
                   |    val tabs = Seq(
                   |      Tab("one", column(12){
                   |        <.div( ^.cls := "tabs-example", <.h4("Tab One"))
                   |      }),
                   |      Tab("two", column(12){
                   |        <.div( ^.cls := "tabs-example", <.h4("Tab Two"))
                   |      })
                   |    )
                   |
                   |    // component creation
                   |    Tabs( initialTab = tabs.head, tabs)
                   |
                   |
                      """.stripMargin
              )
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("NavigationExample")(render())
}
