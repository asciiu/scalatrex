package com.flow.bittrex.controller

import org.http4s._
import org.http4s.dsl._

object Ping {
  val pong = HttpService {
    case GET -> Root / "ping" =>
      Ok("pong")
  }

}
