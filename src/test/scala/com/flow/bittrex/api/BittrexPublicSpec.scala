package com.flow.bittrex.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

class BittrexPublicSpec extends FlatSpec with Matchers {
  implicit val actorSystem = ActorSystem("main")
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // test keys
  val client = new BittrexClient()

  "The BittrexClient" should "get markets" in{
    val futureBalance = client.publicGetMarkets()
    val response = Await.result(futureBalance, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "get currencies" in{
    val futureBalance = client.publicGetCurrencies()
    val response = Await.result(futureBalance, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "get market tickers" in{
    val futureBalance = client.publicGetTicker("BTC-XLM")
    val response = Await.result(futureBalance, 5 second)

    response.success shouldEqual true
    response.result.get.Bid should be > 0.0
  }

  it should "get market summaries" in{
    val futureBalance = client.publicGetMarketSummaries()
    val response = Await.result(futureBalance, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "get market summary" in{
    val futureBalance = client.publicGetMarketSummary("BTC-XMR")
    val response = Await.result(futureBalance, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "get an order book" in{
    val futureBalance = client.publicGetOrderBook("BTC-LSK", "both")
    val response = Await.result(futureBalance, 5 second)

    response.success shouldEqual true
    response.result.get.sell.length should be > 0
    response.result.get.buy.length should be > 0
  }

  it should "get market history" in{
    val futureBalance = client.publicGetMarketHistory("BTC-ETH")
    val response = Await.result(futureBalance, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }
}