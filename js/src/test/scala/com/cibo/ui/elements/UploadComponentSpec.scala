package com.cibo.ui.elements

import com.cibo.ui.input.{TextInput, UploadComponent}
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.test.ReactTestUtils
import org.scalajs.dom.File
import org.scalatest.{FunSpec, Matchers}
import japgolly.scalajs.react.test.{ReactTestUtils, _}
import japgolly.scalajs.react.vdom.html_<^.{ < => vdom}
import japgolly.scalajs.react._
import com.cibo.ui._

class UploadComponentSpec extends FunSpec with Matchers {

  describe("the upload component display"){


    it("Displays upload button if set"){

      val component = UploadComponent( onPopulate = { x: Seq[File] => Callback.empty})

      ReactTestUtils.withRenderedIntoDocument(component) { comp =>
        assert(comp.outerHtmlScrubbed().contains("button"))
      }
    }

    it("doesn't display the button if not set"){
      val component = UploadComponent( onPopulate = { x: Seq[File] => Callback.empty}, button = None)

      ReactTestUtils.withRenderedIntoDocument(component) { comp =>
        assert(!comp.outerHtmlScrubbed().contains("button"))
      }

    }

    it("contains alt content if set"){

      val content = "BEEP BOOP THIS IS CONTENT"
      val component = UploadComponent( onPopulate = { x: Seq[File] => Callback.empty},
        button = None,
        dragOverContent = vdom.div(vdom.span(content.normal))
      )

      ReactTestUtils.withRenderedIntoDocument(component) { comp =>
        assert(comp.outerHtmlScrubbed().contains(content))
      }
    }

  }
}
