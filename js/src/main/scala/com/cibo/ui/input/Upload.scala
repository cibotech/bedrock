/*
 * Copyright (c) 2018, CiBO Technologies, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.cibo.ui.input

import java.util.UUID

import com.cibo.ui.Padding
import com.cibo.ui.ReactGridStrict.{column, row}
import com.cibo.ui.elements.{Button, Icon}
import com.cibo.ui.pane.Pane
import japgolly.scalajs.react.vdom.Attr.Ref
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}
import org.scalajs.dom
import org.scalajs.dom.{Event, ProgressEvent, html}
import org.scalajs.dom.raw.{Event, File, FileReader}
import com.cibo.ui._
import org.w3c.dom.events.MouseEvent

import scala.concurrent.{Future, Promise}
import scala.scalajs.js
import scala.util.Try

object UploadComponent{

  case class Props(onPopulate: Seq[File] => CallbackTo[Unit],
                   dragDropEnabled: Boolean,
                   dragOverContent: TagMod,
                   multiple: Boolean,
                   accept: Seq[String],
                   button: Option[Button]
                  )

  case class State(dragging: Boolean)

  def defaultDragOverContent = {
    row(center = true, vcenter = true, fill = true)(
      column(12)(
        ^.textAlign := "center",
        Icon.cloudUpload.extraLarge
      )
    )
  }

  class Backend($: BackendScope[Props, State]) extends DragDropFileUpload {
    def onUpload(files: Seq[File]) = $.props.runNow().onPopulate(files)

    override def onDragLeave: Callback = $.modState(_.copy(dragging = false))

    override def onDragEnter: Callback = $.modState(_.copy(dragging = true))

    def attachToUploadClick = Callback(dom.document.getElementById(hiddenInputID).asInstanceOf[js.Dynamic].click())

    def handleChange(e: ReactEventFromInput) = Callback{
      e.persist()
      val files = e.target.files

      val filesSeq = for(idx <- 0 until files.length) yield files.item(idx)

      onUpload(filesSeq).runNow()
    }

    val hiddenInputID = s"upload-input-${UUID.randomUUID().toString}"

    def renderFillDragDrop(s: State, p: Props) = {
      <.div( ^.cls := "bd-upload-component drag-drop",
        ^.position := "relative",
        eventTags,
        inner(p.dragOverContent, s.dragging),
        p.button.map{ b =>
          val inner: Seq[TagMod] = Seq(
            <.input(
              ^.`type` := "file",
              ^.onChange ==> handleChange,
              ^.display := "none",
              ^.id := hiddenInputID, //TODO replace with ref
              if(p.multiple) ^.multiple := true else EmptyVdom,
              if(p.accept.nonEmpty) ^.accept := p.accept.mkString(",") else EmptyVdom
            ),
            b{
              ^.onClick --> attachToUploadClick
            },
            " or drag and drop file to upload".bold
          )
          inner.toTagMod
        }.getOrElse(EmptyVdom)
      )
    }

    def render(s: State, p: Props): VdomElement = {
      if(p.dragDropEnabled){
        renderFillDragDrop(s, p)
      } else if(p.button.isDefined) {
        <.div(
          ^.cls := "bd-upload-component",
          <.input(
            ^.`type` := "file",
            ^.onChange ==> handleChange,
            ^.display := "none",
            ^.id := hiddenInputID, //TODO replace with ref
            if (p.multiple) ^.multiple := true else EmptyVdom,
            if (p.accept.nonEmpty) ^.accept := p.accept.mkString(",") else EmptyVdom
          ),
          p.button.get.apply{
            ^.onClick --> attachToUploadClick
          }
        )
      } else <.div(
        EmptyVdom
      )
    }
  }

  val component = ScalaComponent.builder[Props]("LayerPicker")
    .initialStateFromProps( p => State(false))
    .renderBackend[Backend]
    .build

  def apply(onPopulate: Seq[File] => CallbackTo[Unit],
            dragDropEnabled: Boolean = true,
            dragOverContent: TagMod = defaultDragOverContent,
            multiple: Boolean = true,
            accept: Seq[String] = Seq(),
            button: Option[Button] = Some(Button("Click here")),
            ghostDrop: Boolean = false) = component(Props(
    onPopulate,
    dragDropEnabled,
    dragOverContent,
    multiple,
    accept,
    button
  ))

}

object FileUtils {

  // use this on app init to disable drag drop file on window
  def disableWindowDragDrop() = {
    dom.window.addEventListener("drop",{ e: Event =>
      e.preventDefault()
    })
    dom.window.addEventListener("dragover",{ e: Event =>
      e.preventDefault()
    })
  }

  def readContentAsString(file: File): Future[String] = {
    val reader = new FileReader()

    val promise = Promise[String]

    def onLoadEnd(e: ProgressEvent) = {
      val contents = e.target.asInstanceOf[js.Dynamic].result.asInstanceOf[String]
      promise.success(contents)
    }

    def onLoadError(e: Event) = {
      println(s"ERROR ${e}")
      promise.failure(throw new Exception(s"Unable to parse file contents as string ${file.name}"))
    }

    reader.onloadend = onLoadEnd _
    reader.onerror = onLoadError _

    reader.readAsText(file)

    promise.future
  }
}

trait DragDropFileUpload {

  protected def dragEnter(e: ReactDragEvent) = {
    e.preventDefault()
    e.stopPropagation()

    onDragEnter
  }

  protected def dragLeave(e: ReactDragEvent) = {
    e.preventDefault()
    e.stopPropagation()

    onDragLeave
  }

  def preventDefaults(e: ReactDragEvent) = {
    e.preventDefault()
    e.stopPropagation()

    Callback.empty
  }

  val eventTags = Seq(
    ^.onDragEnter ==> dragEnter,
    ^.onDragExit ==> preventDefaults,
    ^.onDrop ==> dragDrop,
    ^.onDragEnd ==> preventDefaults,
    ^.onDragStart ==> dragEnter,
    ^.onDragOver ==> preventDefaults,
  ).toTagMod(identity)

  def inner(innerContent: TagMod, dragging: Boolean) = {
    <.div(^.classSet1("drag-drop-upload-inner", "display" -> dragging, "hide" -> !dragging),
      ^.onDragLeave ==> dragLeave,
      ^.onDrop ==> dragDrop,
      innerContent
    )
  }

  def onDragLeave: Callback = Callback.empty

  def onDragEnter: Callback = Callback.empty

  def onUpload(file: Seq[File]): CallbackTo[Unit]

  def dragDrop(e: ReactDragEvent) = {
    e.preventDefault()
    e.stopPropagation()

    onDragLeave >> Callback{
      val files = e.dataTransfer.files

      val filesSeq = for(idx <- 0 until files.length) yield files.item(idx)
      onUpload(filesSeq).runNow()
    }
  }

}

