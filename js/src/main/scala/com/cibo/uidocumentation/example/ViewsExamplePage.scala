package com.cibo.uidocumentation.example

import com.cibo.ui.ReactGridStrict.{column, row}
import com.cibo.ui.elements.Size.{ExtraLarge, Large, Medium, Small}
import com.cibo.ui.elements.Text.{Bold, Capitalized, ExtraBold, ExtraThin, LowerCase, Normal, NotTransformed, Thin, UpperCase}
import com.cibo.ui.elements.{Button, Icon, Text}
import com.cibo.ui.input.Form.form
import com.cibo.ui.input._
import com.cibo.ui.list._
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react.{Callback, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import com.cibo.ui._
import com.cibo.ui.Padding
import com.cibo.ui.compound.FilteringContentView
import com.cibo.ui.pane.Pane
import japgolly.scalajs.react.component.builder.Lifecycle
import japgolly.scalajs.react.vdom.html_<^

object ViewsExamplePage {

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

  val filterableListView: FilteringContentView[Icon] = new FilteringContentView[Icon]{


    override def listItemRenderer(ib: ItemBinding[Icon]): html_<^.TagMod = {
      <.li(
        Padding(10),
        ^.cls := "listItem",
        ^.classSet("focused" -> ib.isFocused),
        ^.onClick --> ib.select(ib.element),
        <.div(^.cls := "iconWrapper", ib.element),
        <.span(^.cls := "label", ib.element.iconName)
      ).noselect
    }

    val stringFilteringList: StringFilteringList[Icon] = new StringFilteringList[Icon] {
      override def filterFunction(item: Icon, filterString: String): Boolean = item.iconName.contains(filterString)

      override def listWrapper(selecting: Boolean, inner: html_<^.TagMod): html_<^.TagMod = <.div(
        ^.classSet1("absolute-list", "selecting" -> selecting),
        Pane(padding = Some(Padding(0)))(Listing(divided = true)(inner))
      )
    }

    def renderIcon(icon: Icon) = {
      column(3)(
        row(
          column(12)(s"${icon.size.cssName}".bold),
          column(12)(icon)
        )
      ).centerText
    }

    def activeContentRenderer(element: Option[Icon]) = element match {
      case Some(icon) =>
        Pane()(Padding(10),
          row(
            renderIcon(icon.small),
            renderIcon(icon.medium),
            renderIcon(icon.large),
            renderIcon(icon.extraLarge)
          )
        )
      case None => Pane()(Padding(10),
        row(column(12)("Please select an Icon".extraLarge.bold).centerText)
      )
    }
  }

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Views"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
             column(12)(
              <.div(
                <.h4("Filterable Content View Example"),
                row( Padding(10), ^.border := "1px solid grey", column(12)(
                  filterableListView(icons)
                )),
                CodeExample(
                  "scala",
                  """
                    |  val filterableListView: FilteringContentView[Icon] = new FilteringContentView[Icon]{
                    |
                    |    val stringFilteringList: StringFilteringList[Icon] = new StringFilteringList[Icon] {
                    |      override def filterFunction(item: Icon, filterString: String): Boolean = item.iconName.contains(filterString)
                    |
                    |      override def listWrapper(inner: html_<^.TagMod): html_<^.TagMod = Listing(divided = true)(inner, Padding(10))
                    |    }
                    |
                    |    def listItemRenderer(callback: Icon => Callback, element: Icon): html_<^.TagMod = {
                    |      <.li(
                    |        ^.cls := "listItem",
                    |        ^.onClick --> callback(element),
                    |        <.div(^.cls := "iconWrapper", element),
                    |        <.span(^.cls := "label", element.iconName)
                    |      ).noselect
                    |    }
                    |
                    |    def renderIcon(icon: Icon) = {
                    |      column(3)(
                    |        row(
                    |          column(12)(s"${icon.size.cssName}".bold),
                    |          column(12)(icon)
                    |        )
                    |      ).centerText
                    |    }
                    |
                    |    def activeContentRenderer(element: Option[Icon]) = element match {
                    |      case Some(icon) =>
                    |        Pane()(Padding(10),
                    |          row(
                    |            renderIcon(icon.small),
                    |            renderIcon(icon.medium),
                    |            renderIcon(icon.large),
                    |            renderIcon(icon.extraLarge)
                    |          )
                    |        )
                    |      case None => Pane()(Padding(10),
                    |        row(column(12)("Please select an Icon".extraLarge.bold).centerText)
                    |      )
                    |    }
                    |  }
                    |""".stripMargin
                )
              )
             )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("ViewsExample")(render())
}

