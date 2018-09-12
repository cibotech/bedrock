package com.cibo

import com.cibo.ui.Padding
import com.cibo.ui.elements.Text
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._

package object ui {

  implicit def enhancedTagMod(s: TagMod) = new {
    def padding(padding: Padding): TagMod = s(padding)
    def margin(margin: Margin): TagMod = s(margin)
    def centerText: TagMod = s(^.textAlign := "center")
    def noselect: TagMod = s(^.cls := "noselect-pointer")
    def fillWidth: TagMod = s(^.width := "100%")
  }

  implicit def enhancedVdom(s: VdomElement) = new {
    def padding(padding: Padding): TagMod = s(padding)
    def margin(margin: Margin): TagMod = s(margin)
    def centerText: TagMod = s(^.textAlign := "center")
    def noselect: TagMod = s(^.cls := "noselect-pointer")
    def fillWidth: TagMod = s(^.width := "100%")
  }

  implicit def stringToDefaultText(s: String): Text = Text(s)

}
