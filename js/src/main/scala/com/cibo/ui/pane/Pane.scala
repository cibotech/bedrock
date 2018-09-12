package com.cibo.ui.pane

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.elements.{Button, Icon, Text}
import com.cibo.uidocumentation.LoadingSVG
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import Text._
import com.cibo.ui._
object Pane {
  case class Props(header: Option[PaneHeader], lowProfile: Boolean, padding: Option[Padding], content: TagMod*)
  class Backend(val $ : BackendScope[Props, Unit]) {

    def render(p: Props) = {
      val header = p.header.map(x => <.div(PaneHeaderRenderer(x)))

      <.div(^.cls := "card",
            p.padding.map(_.tagMod).getOrElse(EmptyVdom),
            ^.classSet("low-profile" -> p.lowProfile),
            header.getOrElse(EmptyVdom),
            p.content.toTagMod(identity))
    }
  }

  private val component = ScalaComponent
    .builder[Props]("Pane")
    .renderBackend[Backend]
    .build

  def apply(header: Option[PaneHeader] = None, lowProfile: Boolean = false, padding: Option[Padding] = None)(content: TagMod*) = {
    component(Props(header, lowProfile, padding, content: _*))
  }

}

case class PaneHeader(title: Option[Text] = None,
                      interactions: Seq[Button] = Seq(),
                      textStyle: Text => Text = _.bold.medium)

object PaneHeaderRenderer {

  class Backend(val $ : BackendScope[PaneHeader, Unit]) {

    val loading = row(vcenter = true, center = true, fill = true)(
      column(12)(
        <.div(^.cls := "pane-loading-indicator", LoadingSVG.primary)
      )
    )

    def render(p: PaneHeader) = {
      val title = p.title.getOrElse(Text(""))
      <.div(^.cls := "header",
            <.span(^.cls := "title", p.textStyle(title), Padding(10)),
            <.div(^.cls := "controls", p.interactions.map(_.toDom).toTagMod(identity)))
    }
  }

  private val component = ScalaComponent
    .builder[PaneHeader]("PaneHeader")
    .renderBackend[Backend]
    .build

  def apply(paneHeader: PaneHeader) = component(paneHeader)

}

object LoadablePane {

  case class Props(thing: Option[String])

  class Backend(val $ : BackendScope[Props, Unit]) {

    val loading = row(vcenter = true, center = true, fill = true)(
      column(12)(
        <.div(^.cls := "pane-loading-indicator", LoadingSVG.primary)
      )
    )

    def render(p: Props) = {

      val state = if (!p.thing.isDefined) "loading" else ""
      <.div(
        ^.cls := s"card text-center ${state}",
        ^.height := "300px",
        if (p.thing.isDefined) <.h4(^.cls := "title", "Finished Loading")
        else loading
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("LoadablePane")
    .renderBackend[Backend]
    .build

  def apply(size: Int)(thing: Option[String]) =
    column(size)(component(Props(thing)))

}
