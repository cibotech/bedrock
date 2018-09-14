package com.cibo.uidocumentation.example

import com.cibo.ui.{Margin, Padding}
import com.cibo.ui.ReactGridStrict.{column, row}
import com.cibo.ui.elements.{Button, Modals}
import com.cibo.ui.input.Form.{formElement, spreadForm}
import com.cibo.ui.input.{CheckBoxInput, TextInput, ToggleInput}
import com.cibo.ui.pane.{Pane, PaneHeader}
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import com.cibo.ui._
import com.cibo.ui.state.QuickStateWrapper
import japgolly.scalajs.react.vdom.html_<^._
import ReactGridStrict._
import com.cibo.uidocumentation.{CodeExample, SourceLink}
import com.cibo.uidocumentation.example.ThemingExamplePage.{changeCurrentTheme, themes}

object ModalExamplePage {

  case class Props()
  case class State()

  val exampleModalWrapper = new QuickStateWrapper[Boolean]
  class Backend(val $ : BackendScope[Props, State]) {

    def render(p: Props, s: State) = {
      <.div(
        ^.cls := "style-guide",
        <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Theming"))),
        <.div(
          ^.cls := "main-view",
          row(
            column(12)(
            <.h4("Modal Example"),
              exampleModalWrapper.wrap(false){ case (curr, mod) =>
                column(12)(
                  Modals.wrapWithClosingPane(curr, () => {
                    mod(_ => false).runNow()
                  },
                    row(Padding(10), "Im a modal!".bold)
                  ),
                  Button("show modal")(^.onClick --> mod(_ => true))
                )
              }
            ),
            SourceLink.example("ModalExamplePage.scala")
          )
        )
      )


    }
  }

  private val component = ScalaComponent
    .builder[Props]("EnterpriseReport")
    .initialState(State())
    .renderBackend[Backend]
    .build

  def apply() = component(Props())
}

