package com.flow.bittrex.api

import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.StandaloneAhcWSClient

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

/**
  * Created by bishop on 9/7/16.
  */
class BittrexClient(implicit context: ExecutionContext, materializer: ActorMaterializer)
  extends BittrexJsonSupport {

  import Bittrex._

  val wsClient = StandaloneAhcWSClient()

  // TODO read this from config?
  val base_url = "https://bittrex.com/api/v1.1"

  import java.security.SecureRandom
  import java.util.Base64

  private def generateNonce: String = {
    val random = SecureRandom.getInstance("SHA1PRNG")
    random.setSeed(System.currentTimeMillis)

    val nonceBytes = new Array[Byte](16)
    random.nextBytes(nonceBytes)
    new String(Base64.getEncoder.encode(nonceBytes), "UTF-8")
  }

  /** ***************************************************************
    * ACCOUNT API
    * ***************************************************************/


  /**
    * Returns account balance for a single currency
    * @param auth
    * @param currency name of currency. e.g BTC, ETC, ETH...
    * @return BalanceResult wrapped in future
    */
  def accountGetBalance(auth: Auth, currency: String = "BTC"): Future[BalanceResponse] = {
    val endpoint = "account/getbalance"

    val path = wsClient.url(s"$base_url/$endpoint")
      .addQueryStringParameters("apikey" -> auth.apiKey)
      .addQueryStringParameters("nonce" -> generateNonce)
      .addQueryStringParameters("currency" -> currency)

    auth.bittrexRequest(path)
      .get()
      .flatMap { response =>
        Unmarshal(response.body).to[BalanceResponse]
      }
  }

  /**
    * Returns all currency balances.
    * @param auth
    * @return list of BalanceResult wrapped in future
    */
  def accountGetBalances(auth: Auth): Future[BalancesResponse] = {
    val endpoint = "account/getbalances"

    val path = wsClient.url(s"$base_url/$endpoint")
      .addQueryStringParameters("apikey" -> auth.apiKey)
      .addQueryStringParameters("nonce" -> generateNonce)

    auth.bittrexRequest(path)
      .get()
      .flatMap { response =>
        Unmarshal(response.body).to[BalancesResponse]
      }
  }

  /**
    * Returns a deposit address for the specified currency.
    * @param auth
    * @param currency name
    * @return future wrapped DepositAddressResult
    */
  def accountGetDepositAddress(auth: Auth, currency: String = "BTC"): Future[DepositAddressResponse] = {
    val endpoint = "account/getdepositaddress"

    val path = wsClient.url(s"$base_url/$endpoint")
      .addQueryStringParameters("apikey" -> auth.apiKey)
      .addQueryStringParameters("nonce" -> generateNonce)
      .addQueryStringParameters("currency" -> currency)

    auth.bittrexRequest(path)
      .get()
      .flatMap { response =>
        Unmarshal(response.body).to[DepositAddressResponse]
      }

  }

  // TODO /account/withdraw
  // TODO /account/getorder
  // TODO /account/getdeposithistory
  // TODO /account/getwithdrawalhistory

  def accountGetOrderHistory(auth: Auth, market: Option[String] = None): Future[OrderHistoryResponse] = {
    val endpoint = "account/getorderhistory"

    val path = wsClient.url(s"$base_url/$endpoint")
      .addQueryStringParameters("apikey" -> auth.apiKey)
      .addQueryStringParameters("nonce"  -> generateNonce)

    if (market.nonEmpty) {
      path.addQueryStringParameters("market" -> market.get)
    }

    auth.bittrexRequest(path)
      .get()
      .flatMap { response =>
        Unmarshal(response.body).to[OrderHistoryResponse]
      }
  }
}

