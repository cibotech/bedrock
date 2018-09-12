package com.cibo.ui.list

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, ScalaComponent}

object Listing {

  def apply(bulleted: Boolean = false,
            ordered: Boolean = false,
            navigation: Boolean = false,
            divided: Boolean = false,
            relaxed: Boolean = false)(content: TagMod*): VdomElement = {
    val listElement = if (ordered) <.ol else <.ul
    listElement(
      ^.cls := "listing",
      ^.classSet(
        "ordered" -> ordered,
        "bulleted" -> (bulleted && !ordered),
        "navigation" -> (navigation && !(ordered || bulleted)),
        "divided" -> divided,
        "relaxed" -> relaxed
      ),
      content.toTagMod(identity)
    )  
  }
}
