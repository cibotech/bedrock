package com.cibo.uidocumentation

import japgolly.scalajs.react.extra.router.{BaseUrl, Router}
import org.scalajs.dom
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

object DocumentationApp {
  @JSExportTopLevel("uidocumentation.DocumentationApp")
  protected def getInstance(): this.type = this

  @JSExport
  def main(args: Array[String]): Unit = {
    val baseUrl = BaseUrl.fromWindowOrigin_/
    val router = Router(baseUrl, DocumentationRouter.config)
    router().renderIntoDOM(dom.document.getElementById("bedrockApp"))
  }
}
