package com.cibo.uidocumentation.example

import com.cibo.ui.ReactGridStrict.{column, row}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, ScalaComponent}
import org.scalajs.dom

object ThemingExamplePage {

  val themes = Seq("default", "dark", "ciboneu")

  def currentTheme = {
    val themeHeader = dom.document.head.getElementsByClassName("bedrock-theme")(0)
    val theme = themeHeader.attributes.getNamedItem("href").value.split('/').last.split('.').head
    theme
  }

  def changeCurrentTheme(theme: String) = {
    val themeHeader = dom.document.head.getElementsByClassName("bedrock-theme")(0)
    themeHeader.attributes.getNamedItem("href").value = s"/resources/css/$theme.css"
  }

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Theming"))),
      <.div(
        ^.cls := "main-view",
        row(
          column(12)(
            <.p(
              "Theming support is currently a work in progress." +
                "Themes are currently defined by modifying " +
                "the parameters in resouces/style-config.scss and rebuilding bedrock. " +
                "This is a WIP and will be much more streamlined in the future.")
          )
        ),
        row(
          column(12)(
            column(12)(
              <.h4("Themes"),
              <.ul(
                themes.map { theme =>
                  <.li(
                    <.a(theme, ^.onClick --> Callback(changeCurrentTheme(theme)))
                  )
                }.toTagMod
              )
            )
          )
        ),
        row(
          column(12)(
            <.h4("Current Theme Example"),
            CodeExample(
              "scss",
              """
                  |$primary-color: #008080;
                  |$contrast-with-primary: #f0f0f0;
                  |
                  |$secondary-color: #485155;
                  |$complement-secondary: lighten($secondary-color, 10%);
                  |$contrast-with-secondary: #e0e0e0;
                  |
                  |$accent-color: #008080;
                  |
                  |$font-color: #393939;
                  |$adjacent-font-color: #7a7a7a;
                  |$inverse-font-color: #f0f0f0;
                  |
                  |$body-background: #f0f0f0;
                  |$card-background: #FEFEFE;
                  |
                  |// Navigation
                  |$dashboard-vertical-menu-height: 40px;
                  |$dashboard-menu-width: 200px;
                  |
                  |$horizontal-background-color: $card-background;
                  |$vertical-background-color: $card-background;
                  |$current-item-color: $primary-color;
                  |
                  |$mobile-width-min: 1024px;
                  |$dashboard-menu-border: 1px solid darken($vertical-background-color, 10%);
                  |$menu-font-color: $font-color;
                """.stripMargin
            )
          )
        )
      )
    )

  }

  val component = ScalaComponent.static("ThemingExample")(render())
}
