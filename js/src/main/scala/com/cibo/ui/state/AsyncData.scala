package com.cibo.ui.state

trait Message {
  val message: String
  val style: String
}

case class InfoMessage(message: String = "") extends Message {
  val style = "info"
}

case class WarningMessage(message: String = "") extends Message {
  val style = "warning"
}

case class ErrorMessage(message: String = "") extends Message {
  val style = "error"
}

case class SuccessMessage(message: String = "") extends Message {
  val style = "success"
}

sealed abstract class AsyncData[+A] {
  val isEmpty: Boolean
  val isDefined: Boolean
  val isLoading: Boolean
  val message: Message

  def value: A
  def flatMap[B](f: A => AsyncData[B]): AsyncData[B] =
    if (isDefined) f(value) else new AsyncError(message)
  def map[B](f: A => B): AsyncData[B]
}

class AsyncFinished[+A](val value: A, val message: Message) extends AsyncData[A] {
  override val isDefined: Boolean = true

  val isEmpty: Boolean = false
  val isLoading: Boolean = false

  override def map[B](f: (A) => B): AsyncData[B] = new AsyncFinished(f(value), message)
}

object AsyncFinished {
  def apply[A](value: A, message: String = "") =
    new AsyncFinished[A](value, SuccessMessage(message))
  def unapply[A](arg: AsyncFinished[A]): Option[(A, Message)] =
    if (arg.isDefined) Some((arg.value, arg.message)) else None
}

class AsyncError(val message: Message) extends AsyncData[Nothing] {
  override val isDefined: Boolean = false

  val isEmpty: Boolean = false
  val isLoading: Boolean = false

  override def value: Nothing = throw new NoSuchElementException("EmptyMessage.get")
  override def map[B](f: (Nothing) => B): AsyncData[Nothing] = this
}

object AsyncError {
  def apply(message: String = "") = new AsyncError(ErrorMessage(message))
  def unapply(arg: AsyncError): Option[Message] = Some(arg.message)
}

class AsyncLoading(val message: Message) extends AsyncData[Nothing] {
  override val isDefined: Boolean = false

  val isEmpty: Boolean = false
  val isLoading: Boolean = true

  override def value: Nothing = throw new NoSuchElementException("EmptyMessage.get")
  override def map[B](f: (Nothing) => B): AsyncData[Nothing] = this
}

object AsyncLoading {
  def apply(message: String = "") = new AsyncLoading(InfoMessage(message))
  def unapply(arg: AsyncLoading): Option[Message] = Some(arg.message)
}

class AsyncEmpty extends AsyncData[Nothing] {

  val message: Message = InfoMessage("This element is empty")
  override val isDefined: Boolean = false

  val isEmpty: Boolean = true
  val isLoading: Boolean = false

  override def value: Nothing = throw new NoSuchElementException("EmptyMessage.get")
  override def map[B](f: (Nothing) => B): AsyncData[Nothing] = this
}

object AsyncEmpty extends AsyncEmpty{
  def apply() = new AsyncEmpty()
  def unapply(arg: AsyncLoading): Option[Message] = Some(arg.message)
}
