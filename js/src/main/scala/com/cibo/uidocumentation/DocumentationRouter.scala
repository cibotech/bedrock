package com.cibo.uidocumentation

import com.cibo.ui.navigation.{Navigation, NavigationPage}
import com.cibo.ui.notifications.GlobalAlertSystem
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
    NavigationPage("Notifications", NotificationDocs)
  )

  def layout(c: RouterCtl[DocumentationPage], r: Resolution[DocumentationPage]) = {

    val navigation = DocumentationNav(r.page, pages, HomePage, c, "Bedrock")

    GlobalNavState._setCtl(c)
    <.div(^.cls := "dashboard-viewport", GlobalAlertSystem(), navigation, r.render())
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
      | staticRoute("#notifications", NotificationDocs) ~> render(NotificationExamplePage.component()))
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
