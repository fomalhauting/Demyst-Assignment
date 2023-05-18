import sttp.client3._
import sttp.client3.asynchttpclient.zio._
import sttp.client3.circe._
import sttp.model.Uri._

import zio._
import zio.console._

object Main extends App {
  val request = basicRequest.get(uri"https://api.ipify.org/?format=json")

  val program: ZIO[SttpClient with Console, Throwable, Unit] = for {
    response <- SttpClient.send(request)
    _ <- putStrLn(s"Response code: ${response.code}")
    _ <- putStrLn(s"Response body: ${response.body}")
    _ <- extractIpAddress(response)
  } yield ()

  def extractIpAddress(response: Response[String]): ZIO[Console, Throwable, Unit] = {
    val ipAddress = response.body.flatMap(jsonString => io.circe.parser.parse(jsonString).toOption.flatMap(_.hcursor.get[String]("ip").toOption))
    ipAddress match {
      case Some(ip) => putStrLn(s"IP address: $ip")
      case None => putStrLn("Failed to extract IP address")
    }
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    program.provideCustomLayer(AsyncHttpClientZioBackend.layer()).exitCode
}
