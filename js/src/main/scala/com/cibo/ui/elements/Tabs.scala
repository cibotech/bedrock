package com.cibo.ui.elements

import com.cibo.ui.ReactGridStrict._
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, ScalaComponent}

object Tabs {

  case class Tab(name: String, dom: TagMod*)
  case class State(currentTab: Tab)
  case class Props(initialTab: Tab, tabs: Seq[Tab])

  class Backend($ : BackendScope[Props, State]) {

    def render(props: Props, state: State) = {
      row(
        column(12) {
          <.div(
            ^.cls := "tabs",
            props.tabs.toTagMod { tab =>
              <.div(^.cls := s"tab ${if (tab == state.currentTab) "current" else ""}",
                    tab.name,
                    ^.onClick --> $.modState(x => x.copy(currentTab = tab)))
            }
          )
        },
        column(12) {
          row(state.currentTab.dom.toTagMod)
        }
      )
    }
  }

  val component = ScalaComponent
    .builder[Props]("Tabs")
    .initialStateFromProps(p => State(p.initialTab))
    .renderBackend[Backend]
    .build

  def apply(initialTab: Tab, tabs: Seq[Tab]) =
    component(Props(initialTab, tabs))

}
