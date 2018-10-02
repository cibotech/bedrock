package com.cibo.bedrock.state

import org.scalatest.{AsyncFunSpec, Matchers}

class WatchersSpec extends AsyncFunSpec with Matchers {


  describe("MultiWatcher") {


    it("contains potatoes"){
      val multiWatcher = new MultiWatcher[Option[String]](Some("Potatoes"))

      multiWatcher.getCurrentState shouldEqual Some("Potatoes")
    }

    it("will notify on update"){
      val multiWatcher = new MultiWatcher[Option[String]](Some("Potatoes"))

      var newFood = ""

      multiWatcher.subscribe(x => newFood = x.getOrElse(""))
      multiWatcher.setState(Some("Berries"))

      multiWatcher.getCurrentState shouldEqual Some("Berries")
      newFood shouldEqual "Berries"
    }

    it("can be unsubscribed from"){
      val multiWatcher = new MultiWatcher[Option[String]](Some("Potatoes"))

      var newFood = ""

      val id = multiWatcher.subscribe(x => newFood = x.getOrElse(""))
      multiWatcher.setState(Some("Berries"))
      multiWatcher.unsubscribe(id)
      multiWatcher.setState(Some("Mushrooms"))

      multiWatcher.getCurrentState shouldEqual Some("Mushrooms")
      newFood shouldEqual "Berries"
    }

  }

}
