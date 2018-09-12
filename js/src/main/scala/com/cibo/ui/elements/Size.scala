package com.cibo.ui.elements

import com.cibo.ui.elements.Size.{ExtraLarge, Large, Medium, Small}
import japgolly.scalajs.react.vdom.html_<^.TagMod

sealed abstract class Size(val cssName: String)
object Size {
  case object Small extends Size("small")
  case object Medium extends Size("medium")
  case object Large extends Size("large")
  case object ExtraLarge extends Size("extra-large")
}

trait StandardSize[Self <: StandardSize[Self]]{

  def toDom: TagMod

  def changeSize(size: Size): Self

  def small = changeSize(size = Small)
  def medium = changeSize(size = Medium)
  def large = changeSize(size = Large)
  def extraLarge = changeSize(size = ExtraLarge)
}
