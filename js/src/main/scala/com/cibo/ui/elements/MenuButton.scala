package com.cibo.ui.elements


import com.cibo.ui.Padding
import com.cibo.ui.list.Listing
import com.cibo.ui.pane.Pane
import com.cibo.ui.state.QuickStateWrapper
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import org.scalajs.dom.MouseEvent

import scala.scalajs.js

case class MenuButton(button: Button, menu: TagMod*) {

  def toDom() = {
    MenuButton.stateWrapper.wrap(false) { case (state, stateUpdate) =>

      <.div(^.cls := "menu-button",
        button.toDom.apply(
          ^.onBlur --> stateUpdate{_ => false},
          ^.onMouseUp ==> { e =>
            e.persist()
            val statePersist = if(state) true else false
            stateUpdate{_ =>
              e.target.asInstanceOf[js.Dynamic].focus() // because safari and firefox are dumb
              !statePersist}
          }
        ),
        <.div(^.classSet1("absolute-menu-list", "show" -> state, "hide" -> !state),
          Pane(padding = Some(Padding(0)))(menu: _*)
        )
      )
    }.vdomElement
  }

}

object MenuButton {

  private[elements] val stateWrapper = new QuickStateWrapper[Boolean]
  implicit def toTagMod(b: MenuButton): TagMod = b.toDom

}