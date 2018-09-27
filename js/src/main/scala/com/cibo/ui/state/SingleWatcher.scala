package com.cibo.ui.state

// only supports one subscriber
class SingleWatcher[A](initialState: A) {
  private var currentState: A = initialState
  private var stateUpdatedListener: Option[A => Unit] = None

  def updateState(state: A): Unit = {
    currentState = state
    stateUpdatedListener.foreach(_(state))
  }

  def getCurrentState : A = currentState

  def stateUpdated(event: A => Unit): Unit = stateUpdatedListener = Some(event)

  def unsubscribe(): Unit = stateUpdatedListener = None
}
