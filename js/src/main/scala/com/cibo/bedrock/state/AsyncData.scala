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

package com.cibo.bedrock.state

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
