package com.flow.bittrex.api

import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import play.api.libs.ws.StandaloneWSClient
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
    * Returns all currency balances.
    * @param auth
    * @return list of BalanceResult wrapped in future
    */
  def accountGetBalances(auth: Auth): Future[List[BalanceResult]] = {
    val endpoint = "account/getbalances"

    val path = wsClient.url(s"$base_url/$endpoint")
      .addQueryStringParameters("apikey" -> auth.apiKey)
      .addQueryStringParameters("nonce" -> generateNonce)

    auth.bittrexRequest(path)
      .get()
      .flatMap { response =>
        Unmarshal(response.body).to[BalancesResponse].map(bal => bal.result)
      }
  }

  /**
    * Returns account balance for a single currency
    * @param auth
    * @param currency name of currency. e.g BTC, ETC, ETH...
    * @return BalanceResult wrapped in future
    */
  def accountGetBalance(auth: Auth, currency: String = "BTC"): Future[BalanceResult] = {
    val endpoint = "account/getbalance"

    val path = wsClient.url(s"$base_url/$endpoint")
      .addQueryStringParameters("apikey" -> auth.apiKey)
      .addQueryStringParameters("nonce" -> generateNonce)
      .addQueryStringParameters("currency" -> currency)

    auth.bittrexRequest(path)
      .get()
      .flatMap { response =>
        Unmarshal(response.body).to[BalanceResponse].map(bal => bal.result)
      }
  }

  /**
    * Returns a deposit address for the specified currency.
    * @param auth
    * @param currency name
    * @return future wrapped DepositAddressResult
    */
  def accountGetDepositAddress(auth: Auth, currency: String = "BTC"): Future[DepositAddressResult] = {
    val endpoint = "account/getdepositaddress"

    val path = wsClient.url(s"$base_url/$endpoint")
      .addQueryStringParameters("apikey" -> auth.apiKey)
      .addQueryStringParameters("nonce" -> generateNonce)
      .addQueryStringParameters("currency" -> currency)

    auth.bittrexRequest(path)
      .get()
      .flatMap { response =>
        Unmarshal(response.body).to[DepositAddressResponse].map(bal => bal.result)
      }

  }

  // TODO /account/withdraw
  // TODO /account/getorder
  // TODO /account/getdeposithistory
  // TODO /account/getwithdrawalhistory

  def accountGetOrderHistory(auth: Auth, market: Option[String] = None): Future[List[OrderHistoryResult]] = {
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
        Unmarshal(response.body).to[OrderHistoryResponse].map(bal => bal.result)
      }
  }
}

