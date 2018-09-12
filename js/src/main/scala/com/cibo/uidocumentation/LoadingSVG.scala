package com.cibo.uidocumentation

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.elements.Size.{ExtraLarge, Medium}
import com.cibo.ui.elements.{Size, StandardSize}
import japgolly.scalajs.react.vdom.html_<^._

case class LoadingSpinner(size: Size = Medium) extends StandardSize[LoadingSpinner]{
  def changeSize(size: Size) = copy(size = size)

  def toDom = {
    <.div( ^.cls := s"loading-spinner ${size.cssName}",
      <.div(),
      <.div(),
      <.div(),
      <.div()
    )
  }
}

object LoadingSpinner {

  implicit def loadingSpinnerToVdom(spinner: LoadingSpinner): VdomElement = spinner.toDom
}

object LoadingSVG {

  @deprecated
  def primary = {
    row(center = true, vcenter = true)(
      LoadingSpinner().extraLarge
    )
  }
}
