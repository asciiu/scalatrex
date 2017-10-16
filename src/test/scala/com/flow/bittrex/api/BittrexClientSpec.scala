package com.flow.bittrex.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.flow.bittrex.BittrexConfig
import org.specs2.Specification
import org.specs2.matcher.MatchResult
import org.specs2.specification.core.SpecStructure

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

class BittrexClientSpec extends Specification {

  implicit val actorSystem = ActorSystem("main")
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val conf = BittrexConfig.config.right.get

  // test keys
  val apikey = conf.bittrex.apikey
  val secret = conf.bittrex.secret
  val authorization = Auth(apikey, secret)
  val client = new BittrexClient()

  override def is = { s2"""
  The 'BittrexClient' should
    handle invalid keys: $e1
    get account balance: $e2
    retrieve all account balances: $e3
    retrieve an account deposit address: $e4
    get order history: $e5
    get open orders: $e6
  """
  }

  def e1: MatchResult[Any] = handleInvalidKeys
  def e2: MatchResult[Any] = getAccountBalance
  def e3: MatchResult[Int] = retrieveAllAccountBalances
  def e4: MatchResult[Any] = retrieveAccountDepositAddress
  def e5: MatchResult[Int] = getOrderHistory
  def e6: MatchResult[Int] = getOpenOrders

  def handleInvalidKeys: MatchResult[Any] = {
    val currency = "BTC"
    val futureBalance = client.accountGetBalance(Auth(" ", " "), currency)
    val response = Await.result(futureBalance, 5.second)

    response.success shouldEqual false
    response.message shouldEqual "APIKEY_INVALID"
  }

  def getAccountBalance: MatchResult[Any] = {
    val currency = "BTC"
    val futureBalance = client.accountGetBalance(authorization, currency)
    val response = Await.result(futureBalance, 5.second)

    //balance.Currency shouldEqual currency
    response.success shouldEqual true
    response.result.get.Currency shouldEqual currency
  }

  def retrieveAllAccountBalances: MatchResult[Int] = {
    val futureBalances = client.accountGetBalances(authorization)
    val response = Await.result(futureBalances, 5.second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  def retrieveAccountDepositAddress: MatchResult[Any] = {
    val currency = "BTC"
    val futureBalances = client.accountGetDepositAddress(authorization, currency)
    val response = Await.result(futureBalances, 5.second)

    response.success shouldEqual true
    response.result.get.Currency shouldEqual currency
  }

  def getOrderHistory: MatchResult[Int] = {
    val currency = "BTC"
    val futureHist = client.accountGetOrderHistory(authorization)
    val response = Await.result(futureHist, 5.second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  def getOpenOrders: MatchResult[Int] = {
    val currency = "BTC"
    val futureOrders = client.marketGetOpenOrders(authorization)
    val response = Await.result(futureOrders, 5.second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }
}
