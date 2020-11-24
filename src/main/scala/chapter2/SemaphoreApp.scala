package chapter2

import cats.effect.Console.io.putStrLn
import cats.effect.{ExitCode, IO, IOApp}

import scala.concurrent.duration.DurationInt
import cats.effect._
import cats.effect.concurrent.Semaphore
import cats.effect.implicits._
import cats.implicits._
import scala.concurrent.duration._

object SemaphoreApp extends IOApp {

  def someExpensiveTask(n: Int): IO[Unit] =
    IO.sleep(1.second) >>
      putStrLn(s"expensive task ${n}")

  def p1(sem: Semaphore[IO]): IO[Unit] =
    sem.withPermit(someExpensiveTask(1))

  def p2(sem: Semaphore[IO]): IO[Unit] =
    sem.withPermit(someExpensiveTask(2))

  override def run(args: List[String]): IO[ExitCode] = {
    Semaphore[IO](2).flatMap { sem =>
      p1(sem).start.void *>
        p2(sem).start.void
    } *> IO.never.as(ExitCode.Success)
  }
}
