package com.cibo.ui.elements

import com.cibo.ui.elements.Size.{ExtraLarge, Large, Medium, Small}
import com.cibo.ui.elements.Text._
import japgolly.scalajs.react.vdom.html_<^.TagMod
import japgolly.scalajs.react.vdom.html_<^._

sealed abstract class TextWeight(val cssName: String)
sealed abstract class TextTransform(val cssName: String)

case class Text(value: String,
                size: Size = Medium,
                weight: TextWeight = Normal,
                transform: TextTransform = NotTransformed,
                paragraph: Boolean = false
               ) extends StandardSize[Text]{

  def toDom = {
    val element = {
      if (paragraph) <.p
      else <.span
    }
    element(
      ^.classSet1("bd-text", size.cssName -> true, weight.cssName -> true, transform.cssName -> true),
      value
    )
  }

  def changeSize(size: Size) = copy(size = size)

  def extraThin = copy(weight = ExtraThin)
  def thin = copy(weight = Thin)
  def normal = copy(weight = Normal)
  def bold = copy(weight = Bold)
  def extraBold = copy(weight = ExtraBold)

  def capitalized = copy(transform = Capitalized)
  def lowerCase = copy(transform = LowerCase)
  def upperCase = copy(transform = UpperCase)
  def noTransform = copy(transform = NotTransformed)
  def toParagraph = copy(paragraph = true)
  def text = copy(paragraph = false)

}

object Text {

  case object ExtraThin extends TextWeight("extra-thin")
  case object Thin extends TextWeight("thin")
  case object Normal extends TextWeight("")
  case object Bold extends TextWeight("bold")
  case object ExtraBold extends TextWeight("extra-bold")

  case object Capitalized extends TextTransform("capitalized")
  case object LowerCase extends TextTransform("lower-case")
  case object UpperCase extends TextTransform("upper-case")
  case object NotTransformed extends TextTransform("not-transformed")

  implicit def textToTagMod(t: Text): TagMod = t.toDom

}

