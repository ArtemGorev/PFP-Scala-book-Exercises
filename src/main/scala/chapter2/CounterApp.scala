package chapter2

import cats.effect.concurrent.Ref
import cats.effect.{ ExitCode, IO, IOApp, Sync }
import cats.syntax.applicative._
import cats.syntax.functor._

object CounterApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    for {
      value <- LiveCounter.make[IO]
      val1 <- program(value)
      val2 <- program(value)
      val3 <- program(value)
      _ <- println(val1).pure[IO]
      _ <- println(val2).pure[IO]
      _ <- println(val3).pure[IO]
    } yield ExitCode.Success

  def program(counter: Counter[IO]): IO[Int] =
    counter.incr *> counter.get

  trait Counter[F[_]] {
    def incr: F[Unit]
    def get: F[Int]
  }

  object LiveCounter {

    def make[F[_]: Sync]: F[Counter[F]] =
      Ref.of[F, Int](0).map { (ref: Ref[F, Int]) =>
        new Counter[F] {
          def incr: F[Unit] = ref.update(_ + 1)
          def get: F[Int]   = ref.get
        }
      }
  }

}
