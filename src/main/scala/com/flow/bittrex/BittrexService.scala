package com.flow.bittrex

import com.flow.bittrex.controller.Ping
import fs2.{Scheduler, Strategy, Task}
import org.http4s.server.blaze._
import org.http4s.util.StreamApp
import org.http4s.{MaybeResponse, Request, Service}

object BittrexService extends StreamApp {
  implicit val scheduler: Scheduler = Scheduler.fromFixedDaemonPool(2)
  implicit val strategy: Strategy = Strategy.fromFixedDaemonPool(8, threadName = "worker")

  val service: Service[Request, MaybeResponse] = Ping.pong

  def stream(args: List[String]): fs2.Stream[Task, Nothing] = BlazeBuilder.bindHttp(8080)
    .withWebSockets(true)
    .mountService(service, "/")
    .serve
}
