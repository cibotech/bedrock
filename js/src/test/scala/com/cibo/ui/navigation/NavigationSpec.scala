package com.cibo.ui.navigation

import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.test.ReactTestUtils
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.html_<^.{^ => upBoi, < => leftBoi}

import org.scalatest.{FunSpec, Matchers}
import japgolly.scalajs.react.test._

class NavigationSpec extends FunSpec with Matchers {

  describe("Main navigation component") {
    val testInstance = new TestRouter {
      override def dummyNavigation(router: RouterCtl[TestPage]) = {
        DummyNavigation(TestPageOne, navs, TestPageOne, router, "TestName")
      }
    }

    val renderedApp = Router(BaseUrl.fromWindowOrigin_/, testInstance.config)()

    val testInstance2 = new TestRouter {
      val aboveNav = Some(leftBoi.span("above-nav"))
      val belowNav = Some(leftBoi.span("below-nav"))

      override def dummyNavigation(router: RouterCtl[TestPage]) = {
        DummyNavigation(TestPageOne,
                        navs,
                        TestPageOne,
                        router,
                        "TestName",
                        aboveNav = aboveNav,
                        belowNav = belowNav)
      }
    }

    val renderedApp2 = Router(BaseUrl.fromWindowOrigin_/, testInstance2.config)()

    it("Shows pages as options in the navbar") {
      ReactTestUtils.withRenderedIntoDocument(renderedApp) { comp =>
        testInstance.navs.map { nav =>
          assert(comp.outerHtmlScrubbed().contains(s"<span>${nav.name}</span>"))
        }
      }
    }

    it("Shows header") {

      ReactTestUtils.withRenderedIntoDocument(renderedApp) { comp =>
        assert(comp.outerHtmlScrubbed().contains(s"<span>TestName</span>"))
      }
    }

    it("Labels current nav") {

      ReactTestUtils.withRenderedIntoDocument(renderedApp) { comp =>
        assert(
          comp
            .outerHtmlScrubbed()
            .contains("<li class=\"menu-list-item current\"><span>Home</span></li>"))
      }
    }

    it("Conditinally displays aboveNav") {
      ReactTestUtils.withRenderedIntoDocument(renderedApp2) { comp =>
        assert(comp.outerHtmlScrubbed().contains("above-nav"))
      }
    }

    it("Conditinally displays belowNav") {
      ReactTestUtils.withRenderedIntoDocument(renderedApp2) { comp =>
        assert(comp.outerHtmlScrubbed().contains("below-nav"))
      }
    }
  }
}

abstract class TestRouter {
  sealed trait TestPage
  case object TestPageOne extends TestPage
  case object TestPageTwo extends TestPage

  def dummyNavigation(router: RouterCtl[TestPage])
    : Unmounted[DummyNavigation.Props[TestPage], DummyNavigation.State, DummyNavigation.Backend]

  val navs: Seq[NavigationPage[TestPage]] = Seq(
    NavigationPage("Home", TestPageOne),
    NavigationPage("Two", TestPageTwo)
  )

  def layout(c: RouterCtl[TestPage], r: Resolution[TestPage]) = {
    <.div(^.cls := "dashboard-viewport", dummyNavigation(c), r.render())
  }

  val config = RouterConfigDsl[TestPage].buildConfig { dsl =>
    import dsl._
    (emptyRule
      | staticRoute(root, TestPageOne) ~> render(<.div("Home"))
      | staticRoute("#grid", TestPageTwo) ~> render(<.div("Two")))
      .notFound(TestPageOne)
      .renderWith(layout)
  }

  object DummyNavigation extends Navigation[TestPage]
}
