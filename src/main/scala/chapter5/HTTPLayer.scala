package chapter5

import java.util.UUID
import java.util.concurrent.Executors

import cats.effect.{ExitCode, IO, IOApp}
import cats.{Defer, Monad}
import io.circe.{Decoder, Encoder, Json}
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import io.estatico.newtype.macros.newtype
import org.http4s.{HttpApp, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.{Middleware, Router}

import scala.concurrent.ExecutionContext

@newtype case class BrandId(value: UUID)
@newtype case class BrandName(value: String)
case class Brand(uuid: BrandId, name: BrandName)
object Brand {
  implicit val encoder = Encoder.instance[Brand] { obj =>
    Json.obj(
      ("uuid", Json.fromString(obj.uuid.value.toString)),
      ("name", Json.fromString(obj.name.value))
    )
  }

  implicit val decoder = Decoder.instance[Brand] { json =>
    for {
      uuid <- json.downField("uuid").as[UUID]
      name <- json.downField("name").as[String]
    } yield Brand(BrandId(uuid), BrandName(name))
  }
}

trait Brands[F[_]] {
  def findAll: F[List[Brand]]
  def create(name: BrandName): F[Unit]
}

final class BrandRoutes[F[_]: Defer: Monad](
  brands: Brands[F]
) extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
  private[routes] val prefixPath = "/brands"
  val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root => Ok(brands.findAll)
  }

}

object HTTPLayer extends IOApp {
  def run(port: Int) = {
    val httpApp: HttpApp[F] = BrandRoutes.httpRoutes.orNotFound
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(httpApp)
      .serve
  }
}
