package com.cibo.uidocumentation

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}
import org.scalajs.dom.document
import org.scalajs.dom.ext.PimpedNodeList

object CodeExample {

  case class Props(codeType: String, code: String)

  class Backend(val $ : BackendScope[Props, Unit]) {
    def applySyntaxHighlight = Callback {
      import scala.scalajs.js.Dynamic.{global => g}
      val nodeList = document.querySelectorAll("pre code").toArray
      nodeList.foreach(g.window.hljs highlightBlock _)
    }

    def render(p: Props) = {
      <.pre(^.cls := "code-example", <.code(^.cls := p.codeType, p.code))
    }
  }

  private val component = ScalaComponent
    .builder[Props]("CodeExample")
    .renderBackend[Backend]
    .componentDidMount(_.backend.applySyntaxHighlight)
    .build

  def apply(codeType: String, code: String) = component(Props(codeType, code))
}
