package com.cibo.bedrock.elements

import com.cibo.bedrock.Margin
import com.cibo.bedrock.elements.Size.Medium
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.vdom.html_<^
import japgolly.scalajs.react.vdom.html_<^._

case class ButtonGroup(
                      buttons: Seq[Button],
                      size: Size ,
                      significance: ButtonSignificance,
                      roundedEdges: Boolean,
                      borders: Boolean) extends StandardSize[ButtonGroup] {

  def toDom: html_<^.TagMod = <.div( ^.classSet1(
    s"bd-button-group ${size.cssName} ${significance.cssName}",
    "borders" -> borders,
    "rounded" -> roundedEdges
  ),
    buttons.toTagMod(_.changeSize(size))
  )

  def changeSize(size: Size): ButtonGroup = this.copy(size = size)
}


object ButtonGroup {

  def apply(
             buttons: Seq[Button],
             size: Size = Medium,
             significance: ButtonSignificance = Button.Secondary,
             roundedEdges: Boolean = true,
             borders: Boolean = false
           ): ButtonGroup = {

    new ButtonGroup(buttons, size, significance, roundedEdges, borders)
  }

  implicit def buttonGroupToTagMod(b: ButtonGroup): TagMod = b.toDom

}