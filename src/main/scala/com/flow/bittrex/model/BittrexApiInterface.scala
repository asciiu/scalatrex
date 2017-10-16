package com.flow.bittrex.model

import com.flow.bittrex.api.Auth
import com.flow.bittrex.api.Bittrex._

import scala.concurrent.Future

trait BittrexApiInterface {
  def accountGetBalance(auth: Auth, currency: String): Future[BalanceResponse]
  def accountGetBalances(auth: Auth): Future[BalancesResponse]
  def accountGetDepositAddress(auth: Auth, currency: String): Future[DepositAddressResponse]
  def accountGetOrderHistory(auth: Auth, market: Option[String]): Future[OrderHistoryResponse]
  def marketBullLimit(auth: Auth, market: String, qty: Float, rate: Float): Future[OrderHistoryResponse]
  def marketGetOpenOrders(auth: Auth, market: Option[String]): Future[GetOpenOrdersResponse]

  // TODO /account/withdraw
  // TODO /account/getorder
  // TODO /account/getdeposithistory
  // TODO /account/getwithdrawalhistory
  // TODO /market/buylimit
  // TODO /market/selllimit
  // TODO /market/cancel
}
