package com.cibo.ui.list

import com.cibo.ui.elements.{Icon, Text}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import org.scalajs.dom

case class ListItemElement(content: TagMod,
                           header: Option[Text] = None,
                           icon: Option[TagMod] = None,
                           href: Option[String] = None,
                           label: Option[TagMod] = None,
                           onClick: Callback = Callback.empty,
                           isActive: Boolean = false){

  def header(text: Some[Text]): ListItemElement = copy(header = text)
  def icon(icon: Option[TagMod]): ListItemElement = copy(icon = icon)
  def href(href: Option[String]): ListItemElement = copy(href = href)
  def label(label: Option[TagMod]): ListItemElement = copy(label = label)
  def onClick(onClick: Option[Callback]): ListItemElement = copy(onClick = onClick.getOrElse(Callback.empty))
  def isActive(isActive: Boolean): ListItemElement = copy(isActive = isActive)
  def content(content: TagMod*): ListItemElement = copy(content = content.toTagMod(identity))

  def toVdom = <.li(
    ^.cls := "listItem",
    ^.classSet(
      "active" -> isActive
    ),
    ^.onClick --> onClick,
    if (icon.isDefined) {
      <.div(^.cls := "iconWrapper", icon.get)
    } else {
      EmptyVdom
    },
    <.div(
      ^.cls := "contentWrapper",
      if (header.isDefined) {
        <.div(^.cls := "header", header.get)
      } else {
        EmptyVdom
      },
      if (href.isDefined) {
        <.a(^.cls := "content", ^.href := href.get, content)
      } else {
        <.span(^.cls := "content", content)
      }
    ),
    if (label.isDefined) <.span(^.cls := "label", label.get) else EmptyVdom
  )

}

object ListItemElement {

  def apply(content: TagMod*): ListItemElement = new ListItemElement(content.toTagMod)

  implicit def listItemToVdom(listItemElement: ListItemElement): VdomElement = listItemElement.toVdom

}
