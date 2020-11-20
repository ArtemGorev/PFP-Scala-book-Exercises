package chapter2

import eu.timepit.refined._
import eu.timepit.refined.auto._
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype

object RefinedApp extends App {

  @newtype case class Username(value: String)
  @newtype case class Email(value: String)

  case class User(username: Username, email: Email)

  def lookup[F[_]](username: NonEmptyString): F[Option[User]] = ???
  @newtype case class Brand(value: NonEmptyString)
  @newtype case class Category(value: NonEmptyString)
  val brand: Brand = Brand("foo")
  println(s"brand $brand")
  val emptyBrand: Brand = Brand("1")
  println(s"brand $emptyBrand")

}
