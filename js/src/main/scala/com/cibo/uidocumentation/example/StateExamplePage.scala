package com.cibo.uidocumentation.example


import com.cibo.bedrock.ReactGridStrict._
import com.cibo.bedrock.elements.{Button, Tabs}
import com.cibo.bedrock.elements.Tabs.Tab
import com.cibo.bedrock.state.{MultiWatcher, QuickStateWrapper, WatchingWrapper}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object StateExamplePage {

  val store = new MultiWatcher[Int](0)

  val WatchingWrapper = new WatchingWrapper[Int]

  class Backend($: BackendScope[Unit, Int]) {
    def render() = {


      <.div(
        ^.cls := "style-guide",
        <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Navigation"))),
        <.div(
          ^.cls := "main-view",
          <.div(
            ^.cls := "example-block",
            row(
              column(12)(
                testComponentTwo(),
                WatchingWrapper(store){ x =>
                  <.div(
                    (0 to x).map{ _ =>
                      <.div(WatchingWrapper(store)(x => testComponentOne(x)))
                    }.toTagMod
                  )
                }
              )
            )
          )
        )
      )
    }
  }



  private val testComponentOne = ScalaComponent
    .builder[Int]("ButtonExamples")
    .render( $ => <.div(s"Value: ${$.props}"))
    .build



  val testComponentTwo = ScalaComponent
    .builder[Unit]("StateButtonExamples")
    .render{ $ =>
      <.div(
        Button("Add wrapped watcher").onClick(Callback(store.modState(x => x + 1))),
        Button("Add 10 watchers").onClick(Callback(store.modState(x => x + 10))),
        Button("Remove a wrapped watcher").onClick(Callback(store.modState(x => x - 1))),
        Button("Clear wrapped watchers").onClick(Callback(store.setState(0)))

      )
    }.build


  private val component = ScalaComponent
    .builder[Unit]("TableExample")
    .initialState(0)
    .renderBackend[Backend]
    .build

  def apply() = component()
}

