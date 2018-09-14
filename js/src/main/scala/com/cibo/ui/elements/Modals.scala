package com.cibo.ui.elements

import com.cibo.ui.pane.{Pane, PaneHeader}
import japgolly.scalajs.react.{Callback, CallbackTo}
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^.{<, EmptyVdom, ^}
import japgolly.scalajs.react.vdom.html_<^._

object Modals {

  def wrapWithRawModal(show: Boolean = true, onBackgroundClick: Option[CallbackTo[Unit]] = None)(tagMod: TagMod*) = {
    <.div( ^.classSet1("modal-wrapper","show" -> show, "hide" -> !show),
      <.div( ^.cls := "modal-view",
        <.div( ^.cls := "modal",
          tagMod.toTagMod
        )
      ),
      <.div( ^.cls := "modal-background",
        onBackgroundClick.map( x => ^.onClick --> x ).getOrElse(EmptyVdom)
      )
    )
  }

  def wrapWithClosingPane(show: Boolean, onCloseClick: () => Unit, tagMod: TagMod*) = {
    val button = Button(Icon.close, click = Callback(onCloseClick())).small.rounded.secondary

    wrapWithRawModal(show, Some(Callback {
      onCloseClick()
    }))(
      Pane(header = Some(PaneHeader(interactions = Seq(button))))(
        tagMod :_*
      )
    )
  }

}

