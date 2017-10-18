package com.flow.bittrex.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

class BittrexClientSpec extends FlatSpec with Matchers {

  implicit val actorSystem = ActorSystem("main")
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // test keys
  val apikey = "your_key"
  val secret = "your_secret"
  val authorization = Auth(apikey, secret)
  val client = new BittrexClient()

  "The BittrexClient" should "handle invalid keys" in{
    val currency = "BTC"
    val futureBalance = client.accountGetBalance(Auth(" ", " "), currency)
    val response = Await.result(futureBalance, 5 second)

    response.success shouldEqual false
    response.message shouldEqual "APIKEY_INVALID"
  }

  it should "get account balance" in {
    val currency = "BTC"
    val futureBalance = client.accountGetBalance(authorization, currency)
    val response = Await.result(futureBalance, 5 second)

    //balance.Currency shouldEqual currency
    response.success shouldEqual true
    response.result.get.Currency shouldEqual currency
  }

  it should "retrieve all account balances" in {
    val futureBalances = client.accountGetBalances(authorization)
    val response = Await.result(futureBalances, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "retrieve an account deposit address" in {
    val currency = "BTC"
    val futureBalances = client.accountGetDepositAddress(authorization, currency)
    val response = Await.result(futureBalances, 5 second)

    response.success shouldEqual true
    response.result.get.Currency shouldEqual currency
  }

  it should "get order history" in {
    val currency = "BTC"
    val futureHist = client.accountGetOrderHistory(authorization)
    val response = Await.result(futureHist, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "get open orders" in {
    val currency = "BTC"
    val futureOrders = client.marketGetOpenOrders(authorization)
    val response = Await.result(futureOrders, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "set a limit order" in {
    val market = "BTC-XLM"
    val futureUuid = client.marketSellLimit(authorization, market, 1.00000000, 1.00000000)
    val response1 = Await.result(futureUuid, 5 second)

    response1.success shouldEqual true
    response1.result.isDefined shouldEqual true

    // it should also get details for an open order
    val uuid = response1.result.get.uuid
    val futureUuid2 = client.accountGetOrder(authorization, uuid)
    val response2 = Await.result(futureUuid2, 5 second)

    response2.success shouldEqual true
    response2.result.get.IsOpen shouldEqual true

    // finally it should cancel open orders
    val futureUuid3 = client.marketCancel(authorization, uuid)
    val response3 = Await.result(futureUuid3, 5 second)

    response3.success shouldEqual true
  }

  it should "execute a withdrawal" in {
    val currency = "XLM"
    val futureUuid = client.accountWithdraw(authorization, currency, 1.0, "fakeddress")
    val response = Await.result(futureUuid, 5 second)

    response.success shouldEqual false
  }

  it should "get withdrawal history" in {
    val currency = "BTC"
    val futureUuid = client.accountGetWithdrawalHistory(authorization, currency)
    val response = Await.result(futureUuid, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "get deposit history" in {
    val currency = "BTC"
    val futureUuid = client.accountGetDepositHistory(authorization, currency)
    val response = Await.result(futureUuid, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }
}
