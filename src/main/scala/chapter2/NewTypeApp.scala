package chapter2

import io.estatico.newtype.macros._

object NewTypeApp extends App {
  val helloWorld = "HelloWorld"

  def lookup(username: Username, email: Email): Boolean = {
    !(username.value.isEmpty || email.value.isEmpty)
  }

  @newtype case class Username(value: String)

  @newtype case class Email(value: String)

  println(lookup(Username("Sasha"), Email("Privet")))
  println(lookup(Username("Sasha"), Email("")))
  println(lookup(Username(""), Email("Privet")))
  println(lookup(Username(""), Email("")))

}
