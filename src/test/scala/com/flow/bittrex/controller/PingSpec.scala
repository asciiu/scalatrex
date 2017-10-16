package com.flow.bittrex.controller

import org.http4s.dsl._
import org.http4s.{Method, Request, Response, Status}
import org.specs2.Specification
import org.specs2.matcher.MatchResult
import org.specs2.specification.core.SpecStructure

class PingSpec extends Specification {
  def is: SpecStructure = {
    s2"""
  This is a specification to check the 'PingSpec' Endpoint

  The 'PingSpec' endpoint should
    ping is 200  $e1
    ping is pong $e2
  """
  }

  def e1: MatchResult[Status] = pingIs200()
  def e2: MatchResult[String] = pingIsPong()


  val retPing: Response = {
    val getRoot = Request(Method.GET, uri("/ping"))
    val task = Ping.pong.run(getRoot)
    task.unsafeRun.toOption.get
  }

  def pingIs200(): MatchResult[Status] = {
    retPing.status === Status.Ok
  }

  def pingIsPong(): MatchResult[String] = {
    retPing.as[String].unsafeRun() === "pong"
  }
}