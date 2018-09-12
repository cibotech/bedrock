package com.cibo.ui.list

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

case class Definition(term: String, definition: String)
class DefinitionList extends ElementList[Definition]{


  override def renderListItems(items: Seq[Definition]) = <.dl(
    ^.cls := "definition-list",
    items.toTagMod(renderListItem)
  )

  def renderListItem(item: Definition) = {
    <.div(
      ^.cls := "definition-list-item",
      <.dt(item.term),
      <.dd(item.definition)
    )
  }
}

object DefinitionList extends DefinitionList
