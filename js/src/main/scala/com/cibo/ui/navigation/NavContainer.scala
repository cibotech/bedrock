package com.cibo.ui.navigation

import com.cibo.ui.Util
import com.cibo.ui.elements.Icon
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._

case class NavigationPage[T](name: String,
                             link: T,
                             subContent: Option[TagMod] = None,
                             renderer: Option[NavigationPage[T] => TagMod] = None)

trait Navigation[T] {

  case class Props[T](current: T,
                      pages: Seq[NavigationPage[T]],
                      home: T,
                      title: String,
                      router: RouterCtl[T],
                      titleDiv: Option[TagMod],
                      aboveNav: Option[TagMod],
                      belowNav: Option[TagMod],
                      enableSlim: Boolean,
                      navIconSrc: Option[String])

  case class State(displayMenuMobile: Option[Boolean] = None)

  class Backend($ : BackendScope[Props[T], State]) {

    def navigate(page: T, router: RouterCtl[T]): Callback = {
      router.set(page)
    }

    private def clsAndIconForState(state: State): (String, Icon) = {
      state.displayMenuMobile match {
        case Some(x) if x  => ("show", Icon.close)
        case Some(x) if !x => ("hide", Icon.menu)
        case None          => ("", Icon.menu)
      }
    }

    def render(props: Props[T], state: State) = {
      val router = props.router

      val (cls, icon) = clsAndIconForState(state)

      val defaultHeader = <.a( ^.cls := "title-header",
        <.div(^.cls := "title",
              props.navIconSrc.map(src => <.img(^.src := src)).getOrElse(EmptyVdom),
              <.span(props.title)),
        ^.href := router.pathFor(props.home).value
      )

      def renderMainItem(page: NavigationPage[T]) = {
        <.div(
          <.a(
            <.li(
              ^.cls := s"menu-list-item ${if (page.link == (props.current)) "current" else ""}",
              <.span(page.name),
              ^.onClick --> {
                $.modState(_.copy(displayMenuMobile = Some(false))).runNow()
                navigate(page.link, router)
              }
            ),
            ^.href := router.urlFor(page.link).value
          ),
          page.subContent.getOrElse(EmptyVdom)
        )
      }

      val dashboardMenu = <.div(
        <.div(^.cls := "menu-header", props.titleDiv.getOrElse(defaultHeader)),
        props.aboveNav.getOrElse(EmptyVdom),
        <.div(^.cls := "menu-items",
              <.ul(^.cls := "menu-item-list", props.pages.toTagMod(page => renderMainItem(page)))),
        props.belowNav.getOrElse(EmptyVdom)
      )

      val leftClass = if(props.enableSlim) "left"
                      else ""

      <.div(
        <.div(
          ^.cls := s"retracted-menu-bar $leftClass",
          <.div(^.cls := "icon menu-button",
                icon,
                ^.onClick --> $.modState(x =>
                  x.copy(displayMenuMobile = Some(!x.displayMenuMobile.getOrElse(false))))),
          props.titleDiv.getOrElse(defaultHeader)
        ),
        <.div(^.cls := s"dashboard-menu $leftClass $cls", dashboardMenu)
      )
    }
  }

  val component = ScalaComponent
    .builder[Props[T]]("Navigation")
    .initialState(State(None))
    .renderBackend[Backend]
    .build

  def apply(current: T,
            pages: Seq[NavigationPage[T]],
            home: T,
            router: RouterCtl[T],
            title: String = "",
            titleDiv: Option[TagMod] = None,
            aboveNav: Option[TagMod] = None,
            belowNav: Option[TagMod] = None,
            enableSlim: Boolean = false,
            navIconSrc: Option[String] = None) = {
    component(Props(current, pages, home, title, router, titleDiv, aboveNav, belowNav, enableSlim, navIconSrc))
  }

}
