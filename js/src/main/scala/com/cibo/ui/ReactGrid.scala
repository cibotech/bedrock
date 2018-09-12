package com.cibo.ui

import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.{html => *}

object ReactGridStrict {

  def row(reactTags: TagMod*): VdomElement = <.div(^.cls := "row strict", reactTags.toTagMod)

  def row(vcenter: Boolean = false, center: Boolean = false, fill: Boolean = false)(
      reactTags: TagMod*): VdomElement =
    <.div(^.className := s"row strict",
          reactTags.toTagMod,
          ^.classSet("center" -> center, "vcenter" -> vcenter, "fill" -> fill))

  def column(width: Int)(reactTags: TagMod*): VdomElement =
    <.div(^.cls := s"column strict-$width", reactTags.toTagMod)
  def columnMd(width: Int)(reactTags: TagMod*): VdomElement =
    <.div(^.cls := s"column strict-md-$width", reactTags.toTagMod)
  def columnLg(width: Int)(reactTags: TagMod*): VdomElement =
    <.div(^.cls := s"column strict-lg-$width", reactTags.toTagMod)

}

object ReactGridFlex {
  def rowFlex(reactTags: TagMod*): VdomElement = <.div(^.cls := "row", reactTags.toTagMod)
  def rowFlexCenter(reactTags: TagMod*): VdomElement = <.div(^.cls := "row center", reactTags.toTagMod)

  def column(width: Int)(reactTags: TagMod*): VdomElement = <.div(^.cls := s"column", reactTags.toTagMod)
}
