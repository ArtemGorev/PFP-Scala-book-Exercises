package chapter2

import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype

object RefinedApp extends App {

  type PasswordType = Refined[String, NonEmpty]
  val brand: Brand      = Brand("foo")
  val emptyBrand: Brand = Brand("1")

  def lookup[F[_]](username: NonEmptyString): F[Option[User]] = ???

  @newtype case class Username(value: String)

  @newtype case class Email(value: String)

  case class User(username: Username, email: Email)

  @newtype case class Brand(value: NonEmptyString)
  println(s"brand $brand")

  @newtype case class Category(value: NonEmptyString)
  println(s"brand $emptyBrand")
  @newtype case class Password(value: PasswordType)

  println(s"password ${Password("Hello World")}")

}
