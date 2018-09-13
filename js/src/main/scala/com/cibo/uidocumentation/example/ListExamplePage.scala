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

package com.cibo.uidocumentation.example

import java.util.UUID

import com.cibo.ui.elements.Icon
import com.cibo.ui.list._
import com.cibo.uidocumentation.{CodeExample, DocumentationRouter, GlobalNavState}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.navigation.NavigationPage
import com.cibo.ui.pane.{Pane, PaneHeader}
import com.cibo.uidocumentation.DocumentationRouter.DocumentationPage
import com.cibo.ui._
import japgolly.scalajs.react.vdom.html_<^

object ListExamplePage {

  val staticListSubDoc = SubDoc("Static Lists")
  val dynamicListSubDoc = SubDoc("Dynamic Lists")
  val gridListSubDoc = SubDoc("Grid Lists")

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Lists"))),
      <.div(
        ^.cls := "main-view",
        subDocList(Seq(staticListSubDoc, dynamicListSubDoc, gridListSubDoc)),
        <.div(
          ^.cls := "example-block",
          <.h3(staticListSubDoc.name, ^.id := staticListSubDoc.id),
          <.div(
            <.h4("Basic List"),
            Listing()(
              ListItemElement("List Item 1"),
              ListItemElement("List Item 2"),
              ListItemElement("List Item 3")
            ),
            CodeExample(
              "scala",
              s"""
                 |    // Note that the outer list component is called "Listing" to avoid naming conflicts in Scala
                 |    Listing(
                 |        ListItem("List Item 1"),
                 |        ListItem("List Item 2"),
                 |        ListItem("List Item 3")
                 |    )
                 |
                 |""".stripMargin
            )
          ),
          <.div(
            <.h4("Bulleted List"),
            Listing(bulleted = true)(
              ListItemElement("List Item 1"),
              ListItemElement("List Item 2"),
              ListItemElement("List Item 3")
            ),
            CodeExample(
              "scala",
              s"""
               |    Listing(bulleted = true)(
               |        ListItem("List Item 1"),
               |        ListItem("List Item 2"),
               |        ListItem("List Item 3")
               |    )
               |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Ordered List"),
            Listing(ordered = true)(
              ListItemElement("List Item 1"),
              ListItemElement("List Item 2"),
              ListItemElement("List Item 3")
            ),
            CodeExample(
              "scala",
              s"""
                 |    Listing(ordered = true)(
                 |        ListItem("List Item 1"),
                 |        ListItem("List Item 2"),
                 |        ListItem("List Item 3")
                 |    )
                 |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("List Items with Icons"),
            Listing()(
              ListItemElement("List Item 1", icon = Some(Icon("face"))),
              ListItemElement("List Item 2", icon = Some(Icon("textsms"))),
              ListItemElement("List Item 3", icon = Some(Icon("help")))
            ),
            CodeExample(
              "scala",
              s"""
                 |    Listing(
                 |        ListItem(icon = Icon("face"))("List Item 1"),
                 |        ListItem(icon = Icon("textsms"))("List Item 2"),
                 |        ListItem(icon = Icon("help"))("List Item 3")
                 |    )
                 |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("List Items with Labels"),
            <.div(
              ^.style := scalajs.js.Dynamic.literal(
                "maxWidth" -> "10em",
                "padding" -> "1em",
                "border" -> "1px dotted lightgrey",
              ),
              Listing()(
                ListItemElement("List Item 2", label = Some(14)),
                ListItemElement("List Item 1", label = Some(Icon.stars.small)),
                ListItemElement("List Item 3", label = Some("B"))
              )
            ),
            CodeExample(
              "scala",
              s"""
                 |    // Note that labels are floated right to the edge of the Listing's parent component
                 |    // The dotted box is just provided as an example container.
                 |    Listing(
                 |        ListItem(label = 14)("List Item 1"),
                 |        ListItem(label = Icon.stars)("List Item 2"),
                 |        ListItem(label = "B")("List Item 3")
                 |    )
                 |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Definition List"),
            DefinitionList(
              Seq(
                Definition("Term 1", "Definition 1"),
                Definition("Term 2", "Definition 2"),
                Definition("Term 3", "Definition 3")
              )),
            CodeExample(
              "scala",
              s"""
                 |
                 |  DefinitionList(
                 |     Definition("Term 1", "Definition 1"),
                 |     Definition("Term 2", "Definition 2"),
                 |     Definition("Term 3", "Definition 3"))
                 |
               |""".stripMargin
            )
          ),
          <.h3(dynamicListSubDoc.name, ^.id := dynamicListSubDoc.id),
          <.div(
            <.h4("List of Links"),
            navigationList(navigations),
            CodeExample(
              "scala",
              s"""
                 |  // in companion
                 |  val navigations = DocumentationRouter.pages.take(3)
                 |  val navigationList = new BasicList[NavigationPage[DocumentationPage]] {
                 |    override val listingWrapper = Listing(navigation = true)
                 |
                 |    def renderListItem(item: NavigationPage[DocumentationPage]) = {
                 |      ListItemElement(href = GlobalNavState.getCtl.urlFor(item.link).value)(item.name)
                 |    }
                 |  }
                 |
                 |  //in render
                 |  navigationList(navigations),
                 |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Navigation List (with onClick Callbacks)"),
            alertList(alertItems),
            CodeExample(
              "scala",
              s"""
                 |
                 |  //in companion
                 |  case class AlertItem(name: String){
                 |    val onClick = Callback(dom.window.alert(name +" clicked"))
                 |  }
                 |
                 |  val alertList = new BasicList[AlertItem] {
                 |
                 |    override val listingWrapper = Listing(navigation = true)
                 |
                 |    def renderListItem(item: AlertItem) = {
                 |      ListItemElement(onClick = item.onClick)(item.name)
                 |    }
                 |  }
                 |
                 |  //in render
                 |  alertList(alertItems),
                 |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Complex List with Several Props"),
            complexList(complexItems),
            CodeExample(
              "scala",
              s"""
                 |  // in companion object
                 |  case class ComplexListItem(icon: Icon, header: String, content: TagMod*)
                 |  val complexList = new BasicList[ComplexListItem] {
                 |    override val listingWrapper = Listing(divided = true)
                 |
                 |    def renderListItem(item: ComplexListItem) = ListItemElement(
                 |      icon = Icon("cloud").large, header = item.header)(
                 |      "This is the ListItem's content.")
                 |  }
                 |
                 |  //in render
                 |  complexList(complexItems)
                 |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Relaxed List"),
            songList(listOfSongs),
            CodeExample(
              "scala",
              s"""
                 |  // in companion object
                 |  case class Song(url: String, title: String)
                 |
                 |  val songList = new BasicList[Song] {
                 |    override val listingWrapper = Listing(relaxed = true)
                 |    def renderListItem(item: Song) = {
                 |      ListItemElement(href = item.url)(item.title)
                 |    }
                 |  }
                 |
                 |  //in render
                 |  songList(listOfSongs)
                 |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Generic element listing"),
            carList(Seq(Car("Black", 2010), Car("Red", 1950))),
            CodeExample(
              "scala",
              s"""
                 |
                 |  // in companion object
                 |  case class Car(color: String, year: Int)
                 |  val carList = new ElementList[Car]{
                 |    def renderListItem(item: Car) = row(
                 |      column(6)(item.color),
                 |      column(6)(item.year)
                 |    )
                 |  }
                 |
                 |  // in render
                 |  carList(Seq(Car("Black", 2010), Car("Red", 1950))),
                 |
               |""".stripMargin
            )
          ),
          <.h3(gridListSubDoc.name, ^.id := gridListSubDoc.id),
          <.div(
            <.h4("Grid list"),
            iconGridlist(icons),
            CodeExample(
              "scala",
              s"""
                 |
                 |  val iconGridlist = new ElementList[Icon] {
                 |
                 |    def renderListItem(item: Icon) = column(3) {
                 |      <.div(^.padding := "20px", ^.textAlign := "center", item)
                 |    }
                 |
                 |    override def renderListItems(items: Seq[Icon]) = row(
                 |      items.toTagMod(renderListItem)
                 |    )
                 |  }
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Icon Card List"),
            iconCardGridList(icons),
            CodeExample(
              "scala",
              s"""
                 |
                 |  val iconCardGridList = new ElementList[Icon] {
                 |
                 |    def renderListItem(item: Icon) = column(3) {
                 |      Pane()(<.div(^.padding := "20px", ^.textAlign := "center", item.large))
                 |    }
                 |
                 |    override def renderListItems(items: Seq[Icon]) = row(
                 |      items.toTagMod(renderListItem)
                 |    )
                 |  }
                 |""".stripMargin
            )
          ),
          <.div(
            <.h4("Filtered Icon Card List"),
            complexFilteringList(icons, { ib => column(3)(
              Pane(Some(
                PaneHeader(title = Some(ib.element.iconName))
              ))(
                <.div(^.padding := "20px", ^.textAlign := "center", if(ib.isFocused) ib.element.extraLarge else ib.element.medium, ^.onClick --> ib.select(ib.element))
              ))},
              x => Callback(println(x))
            ),
            CodeExample(
              "scala",
              s"""
                 |
                 |  val complexFilteringList = new StringFilteringList[Icon]{
                 |
                 |    def filterFunction(item: Icon, filterString: String) = {
                 |      item.iconName.contains(filterString)
                 |    }
                 |
                 |    override def renderListItems(items: Seq[Icon]) = row(
                 |      items.toTagMod(renderListItem)
                 |    )
                 |
                 |    def renderListItem(item: Icon) = column(3) {
                 |      Pane(Some(
                 |        PaneHeader(title = Some(item.iconName))
                 |      ))(
                 |        <.div(^.padding := "20px", ^.textAlign := "center", item.large)
                 |      )
                 |    }
                 |  }
                 |""".stripMargin
            )
          )
        )
      )
    )
  }

  case class SubDoc(name: String, id: String = UUID.randomUUID().toString)
  val subDocList = new ElementList[SubDoc] {

    def renderListItem(item: SubDoc) = {
      val onclick = Callback{
        dom.document.getElementById(item.id).scrollIntoView()
      }
      <.li( <.a( ^.onClick --> onclick, item.name))
    }
  }


  val navigations = DocumentationRouter.pages.take(3)
  val navigationList = new BasicList[NavigationPage[DocumentationPage]] {
    override val listingWrapper = Listing(navigation = true)

    def renderListItem(item: NavigationPage[DocumentationPage]) = {
      ListItemElement(item.name, href = Some(GlobalNavState.getCtl.urlFor(item.link).value))
    }
  }


  case class AlertItem(name: String){
    val onClick = Callback(dom.window.alert(s"$name clicked"))
  }

  val alertItems = Seq(
    AlertItem("List Item 1"),
    AlertItem("List Item 2"),
    AlertItem("List Item 3")
  )
  val alertList = new BasicList[AlertItem] {

    override val listingWrapper = Listing(navigation = true)

    def renderListItem(item: AlertItem) = {
      ListItemElement(item.name, onClick = item.onClick)
    }
  }

  val complexItems = Seq(
    ComplexListItem(
      icon = Icon("headset").large,
      header = "List Item 1",
      "This is the ListItem's content."),
    ComplexListItem(
      icon = Icon("store").large,
      header = "List Item 2",
      "This is the ListItem's content."),
    ComplexListItem(
      icon = Icon("cloud").large,
      header = "List Item 3",
      "This is the ListItem's content.")
  )

  case class ComplexListItem(icon: Icon, header: String, content: TagMod*)
  val complexList = new BasicList[ComplexListItem] {
    override val listingWrapper = Listing(divided = true)


    def renderListItem(item: ComplexListItem) = ListItemElement(
      item.content.toTagMod(identity),
      icon = Some(item.icon), header = Some(item.header))
  }


  case class Song(url: String, title: String)
  val listOfSongs = Seq(
    Song(url = "https://www.youtube.com/watch?v=rCp2h5jslKY", "Frankie Goes To Hollywood - Relax"),
    Song(url = "https://www.youtube.com/watch?v=mMEcb0vaC4w", "Styx - Fooling Yourself (The Angry Young Man)"),
    Song(url = "https://www.youtube.com/watch?v=RVmG_d3HKBA", "MIKA - Relax, Take It Easy")
  )

  val songList = new BasicList[Song] {
    override val listingWrapper = Listing(relaxed = true)
    def renderListItem(item: Song) = {
      ListItemElement(item.title, href = Some(item.url))
    }
  }

  case class Car(color: String, year: Int)
  val carList = new ElementList[Car]{
    def renderListItem(item: Car) = row(
      column(6)(item.color),
      column(6)(item.year)
    )
  }

  val icons = Seq(
    Icon.callReceived,
    Icon.cancel,
    Icon.dock,
    Icon.flag,
    Icon.call,
    Icon.face,
    Icon.pregnantWoman,
    Icon.insertEmoticon,
    Icon.computer
  )

  val iconGridlist = new ElementList[Icon] {

    def renderListItem(item: Icon) = column(3) {
      <.div(^.padding := "20px", ^.textAlign := "center", item)
    }

    override def renderListItems(items: Seq[Icon]) = row(
      items.toTagMod(renderListItem)
    )
  }

  val iconCardGridList = new ElementList[Icon] {

    def renderListItem(item: Icon) = column(3) {
      Pane(Some(
        PaneHeader(title = Some(item.iconName))
      ))(
        <.div(^.padding := "20px", ^.textAlign := "center", item.large)
      )
    }

    override def renderListItems(items: Seq[Icon]) = row(
      items.toTagMod(renderListItem)
    )
  }

  val complexFilteringList = new StringFilteringList[Icon]{

    override def listWrapper(selecting: Boolean, inner: html_<^.TagMod): html_<^.TagMod = row(inner)

    def filterFunction(item: Icon, filterString: String) = {
      item.iconName.contains(filterString)
    }
  }


  val component = ScalaComponent.static("ListExample")(render())
}

