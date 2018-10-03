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

package com.cibo.uidocumentation

import com.cibo.bedrock.navigation.{HorizontalNavItem, Navigation, NavigationPage}
import com.cibo.bedrock.notifications.GlobalAlertSystem
import com.cibo.uidocumentation.DocumentationRouter.DocumentationPage
import com.cibo.uidocumentation.example._
import japgolly.scalajs.react.extra.router.{RouterConfigDsl, _}
import japgolly.scalajs.react.vdom.html_<^._

object DocumentationRouter {

  sealed trait DocumentationPage
  case object HomePage extends DocumentationPage
  case object GridDocs extends DocumentationPage
  case object InputDocs extends DocumentationPage
  case object NavigationDocs extends DocumentationPage
  case object PaneDocs extends DocumentationPage
  case object CalendarDocs extends DocumentationPage
  case object TableDocs extends DocumentationPage
  case object IconDocs extends DocumentationPage
  case object LoadingDocs extends DocumentationPage
  case object ThemingDocs extends DocumentationPage
  case object ListDocs extends DocumentationPage
  case object StatisticDocs extends DocumentationPage
  case object IndicatorDocs extends DocumentationPage
  case object TextDocs extends DocumentationPage
  case object ComplexViewsDocs extends DocumentationPage
  case object NotificationDocs extends DocumentationPage
  case object ModalDocs extends DocumentationPage
  case object StateDocs extends DocumentationPage

  val pages: Seq[NavigationPage[DocumentationPage]] = Seq(
    NavigationPage("Get Started", HomePage),
    NavigationPage("Grid System", GridDocs),
    NavigationPage("Navigation", NavigationDocs),
    NavigationPage("Lists", ListDocs),
    NavigationPage("Input Elements", InputDocs),
    NavigationPage("Table", TableDocs),
    NavigationPage("Calendar", CalendarDocs),
    NavigationPage("Panes", PaneDocs),
    NavigationPage("Icons", IconDocs),
    NavigationPage("Statistic", StatisticDocs),
    NavigationPage("Indicator", IndicatorDocs),
    NavigationPage("Loading Indicators", LoadingDocs),
    NavigationPage("Theming", ThemingDocs),
    NavigationPage("Text", TextDocs),
    NavigationPage("Complex Views", ComplexViewsDocs),
    NavigationPage("Notifications", NotificationDocs),
    NavigationPage("Modals", ModalDocs),
    NavigationPage("State", StateDocs)
  )

  def layout(c: RouterCtl[DocumentationPage], r: Resolution[DocumentationPage]) = {

    val navigation = DocumentationNav.horizontal(r.page, pages, Seq(
      HorizontalNavItem("Documentation", pages = pages, None),
      HorizontalNavItem("Documentation", pages = pages, None)
    ), HomePage, c, "Bedrock")

    GlobalNavState._setCtl(c)
    <.div(^.cls := "dashboard-viewport horizontal-menu", GlobalAlertSystem(), navigation, r.render())
  }

  val config = RouterConfigDsl[DocumentationPage].buildConfig { dsl =>
    import dsl._
    (emptyRule
      | staticRoute("#", HomePage) ~> render(DocsHomePage.component())
      | staticRoute("#grid", GridDocs) ~> render(GridExamplePage.component())
      | staticRoute("#calendar", CalendarDocs) ~> render(CalendarExamplePage.component())
      | staticRoute("#input", InputDocs) ~> render(InputExamplePage.component())
      | staticRoute("#table", TableDocs) ~> render(TableExamplePage())
      | staticRoute("#panes", PaneDocs) ~> render(PaneExamplePage.component())
      | staticRoute("#icons", IconDocs) ~> render(IconExamplePage.component())
      | staticRoute("#navigation", NavigationDocs) ~> render(NavigationExamplePage.component())
      | staticRoute("#loading", LoadingDocs) ~> render(LoadingExamplePage.component())
      | staticRoute("#theming", ThemingDocs) ~> render(ThemingExamplePage.component())
      | staticRoute("#lists", ListDocs) ~> render(ListExamplePage.component())
      | staticRoute("#statistic", StatisticDocs) ~> render(StatisticExamplePage.component())
      | staticRoute("#indicator", IndicatorDocs) ~> render(IndicatorExamplePage.component())
      | staticRoute("#text", TextDocs) ~> render(TextExamplePage.component())
      | staticRoute("#complex-views", ComplexViewsDocs) ~> render(ViewsExamplePage.component())
      | staticRoute("#notifications", NotificationDocs) ~> render(NotificationExamplePage.component())
      | staticRoute("#modals", ModalDocs) ~> render(ModalExamplePage())
      | staticRoute("#state", StateDocs) ~> render(StateExamplePage()))
    .notFound(HomePage)
      .renderWith(layout)
  }
}

object GlobalNavState {
  class RouterCtlNotSetException extends Exception("RouterCtl not set. You should never see this.")
  private var ctl: Option[RouterCtl[DocumentationPage]] = None // scalastyle:ignore
  def getCtl = ctl.getOrElse(throw new RouterCtlNotSetException())
  def _setCtl(c: RouterCtl[DocumentationPage]) = ctl = Some(c)
}

object DocumentationNav extends Navigation[DocumentationPage]
