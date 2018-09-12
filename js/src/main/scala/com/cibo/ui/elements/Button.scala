package com.cibo.ui.elements

import com.cibo.ui.{Margin, Padding}
import com.cibo.ui.elements.Size.{Large, Medium, Small}
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.vdom.html_<^._
import com.cibo.ui._
import com.cibo.ui.Margin._
sealed abstract class ButtonSignificance(val className: String)

case class Button(content: Either[Text, TagMod],
                  click: Callback,
                  size: Size,
                  significance: ButtonSignificance,
                  round: Boolean,
                  margin: Option[Margin]) extends StandardSize[Button]{

  import Button._
  def toDom: TagMod = {

    val inner: TagMod = content.fold(
      x => x.copy(size = size),
      x => x
    )

    <.button(
      ^.classSet(
        size.cssName -> true,
        "round" -> round,
        significance.className -> true
      ),
      margin.map(toTagMod).getOrElse(EmptyVdom),
      inner,
      ^.onClick --> click
    )
  }

  def changeSize(size: Size) = copy(size = size)

  def margin(margin: Option[Margin]) = copy(margin = margin)

  def primary = copy(significance = Primary)
  def secondary = copy(significance = Secondary)
  def mild = copy(significance = Mild)
  def repressed = copy(significance = Repressed)
  def error = copy(significance = Error)
  def success = copy(significance = Success)

  def rounded = copy(round = true)
  def rectangular = copy(round = false)

  def onClick(cb: Callback) = copy(click = cb)
}


object Button {

  def apply(content: TagMod,
            click: Callback = Callback.empty,
            size: Size = Medium,
            significance: ButtonSignificance = Button.Primary,
            round: Boolean = false,
            buttonSizedText: Boolean = true,
            margin: Option[Margin] = None): Button = {

    new Button(Right(content), click, size, significance, round, margin)
  }

  def apply(content: TagMod*): Button = {
    new Button(Right(content.toTagMod), Callback.empty, Medium, Button.Primary, false, None)
  }

  def text(text: Text,
            click: Callback = Callback.empty,
            size: Size = Medium,
            significance: ButtonSignificance = Button.Primary,
            round: Boolean = false,
            margin: Option[Margin] = None): Button = {

    new Button(Left(text), click, size, significance, round, margin)
  }

  case object Primary extends ButtonSignificance("")
  case object Secondary extends ButtonSignificance("secondary")
  case object Mild extends ButtonSignificance("mild")
  case object Repressed extends ButtonSignificance("repressed")
  case object Error extends ButtonSignificance("error")
  case object Success extends ButtonSignificance("success")

  implicit def buttonToTagMod(b: Button): TagMod = b.toDom
}
