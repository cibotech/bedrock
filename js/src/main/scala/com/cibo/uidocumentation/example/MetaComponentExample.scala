package com.cibo.uidocumentation.example

import com.cibo.ui.Padding
import com.cibo.ui.ReactGridStrict.{column, row}
import com.cibo.ui.elements.{Button}
import com.cibo.ui.input.Form._
import com.cibo.ui.input.{CheckBoxInput, TextInput, ToggleInput}
import com.cibo.ui.pane.{Pane, PaneHeader}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import com.cibo.ui._
object MetaComponentExample {

  case class Props()
  case class State(name: String, likesCats: Boolean, isABoss: Boolean)

  class Backend(val $ : BackendScope[Props, State]) {

    def render(p: Props, s: State) = {
      Pane(
        Some(PaneHeader(Some("Form Example".bold)))
      )(
        row(
          Padding(10),
          column(12)(
            spreadForm("I'm a boss",
                       ToggleInput(
                         toggled = s.isABoss,
                         onChange = { aBoss =>
                           $.modState(_.copy(isABoss = aBoss))
                         }
                       ))
          ),
          column(12)(
            spreadForm("Name", TextInput(initialValue = s.name, onChange = { name =>
              $.modState(_.copy(name = name))
            }))
          ),
          column(12)(
            spreadForm("I like cats",
                       CheckBoxInput(
                         checked = s.likesCats,
                         onChange = { likesCats =>
                           $.modState(_.copy(likesCats = likesCats))
                         }
                       ))
          ),
          column(12)(
            formElement(
              Button("click me".medium).small.margin(Some(Margin(5, 0, 0, 0)))
                .onClick(Callback { println("clicked") })
            )
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("EnterpriseReport")
    .initialStateFromProps(p => State("", true, true))
    .renderBackend[Backend]
    .build

  def apply() =
    component(Props())

}
