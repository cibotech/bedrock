package com.cibo.ui

import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.Date

case class Margin(top: Int, right: Int, bottom: Int, left: Int){
  def repr = s"${top}px ${right}px ${bottom}px ${left}px"

}

object Margin{
  def apply(all: Int): Margin = new Margin(all, all, all, all)

  def apply(tb: Int, lr: Int): Margin = new Margin(tb, lr, tb, lr)

  implicit def toTagMod(padding: Margin): TagMod = ^.margin := padding.repr
}

case class Padding(top: Int, right: Int, bottom: Int, left: Int){
  def repr = s"${top}px ${right}px ${bottom}px ${left}px"
  def tagMod = ^.padding := repr
}

object Padding {
  def apply(all: Int): Padding = new Padding(all, all, all, all)

  def apply(tb: Int, lr: Int): Padding = new Padding(tb, lr, tb, lr)

  implicit def toTagMod(padding: Padding): TagMod = padding.tagMod
}

object Util {

  def withLabel(label: String, tagMod: TagMod*) = {
    <.label(TagMod.Composite(tagMod.toVector), label)
  }

}

object Logging {

  private def timeStamp = {
    val current = new Date()
    s"[${current.getHours()}:${current.getMinutes()}:${current.getSeconds()}:${current.getMilliseconds()}]"
  }
  object logger {

    def error(message: String) = dom.console.error(s"$timeStamp : $message")

    def warn(message: String) = dom.console.warn(s"$timeStamp : $message")

    def info(message: String) = dom.console.info(s"$timeStamp : $message")

    def cons(message: String) = dom.console.log(s"$timeStamp : $message")
  }
}