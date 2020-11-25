package chapter3

import cats.effect.concurrent.Ref
import cats.effect.{IO, Resource, Sync}
import cats.syntax.applicative._
import cats.{Applicative, Functor, Monad}
import chapter2.CounterApp.Counter
import io.estatico.newtype.macros.newtype

object TaglessFinalEncodingApp extends App {

  // TaglessFinalEncoding - interface
  // Algebra - interface
  // Interpreter - implementation of interface
  // Program - interface with typeclass constraint

  // ItemsAlgebra - algebra
  // LiveItemsAlgebra - interpreter

  class LiveCounter[F[_]: Monad] private (
    key: RedisKey,
    cmd: RedisCommand[F, String, Int]
  ) extends Counter[F] {
    def incr: F[Unit] = cmd.incr(key.value)
    def get: F[Int]   = ???
//    def get: F[Int]   = cmd.get(key.value).map(_.getOrElse(0))
  }

  abstract class TestCounter[F[_]](
    ref: Ref[F, Int]
  ) extends Counter[F] {
    def incr: F[Unit] = ref.update(_ + 1)
    def get: F[Int]   = ref.get
  }

  class RedisCommand[F[_]: Functor, A, B](implicit F: Applicative[F]) {
    def incr(key: A): F[Unit] = {
      println(key)
      ().pure[F]
    }

    def get(key: A): F[Option[B]] = {
      println(key)
      Option.empty[B].pure[F]
    }
  }

  @newtype case class RedisKey(value: String)

//  object LiveCounter {
//    private val cmdApi: Resource[IO, RedisCommand[IO, String, Int]] = {
//    }

//    def make[F[_]: Sync: Applicative]: Resource[F, Counter[F]] = cmdApi.map { cmd =>
//      new LiveCounter(RedisKey("myKey"), cmd)
//    }
//  }
}

/*

trait B {
  def run
}

trait C {
  def run
}

class A () {
  def run = ???
}
 */
