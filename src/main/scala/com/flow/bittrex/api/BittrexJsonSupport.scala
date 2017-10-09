package com.flow.bittrex.api

import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

//case class BittrexNonce(Nounce: Int, Deltas: List[MarketUpdate])
//case class BittrexSummary(H: String, M: String, A: List[BittrexNonce])


object Bittrex {

  // Results
  case class DepositAddressResult(Currency: String, address: Option[String])

  case class BalanceResult(Currency: String, Balance: Float, Available: Float, Pending: Float,
                           CryptoAddress: Option[String])

  case class OrderHistoryResult(OrderUuid: String,
                                Exchange: String,
                                TimeStamp: String,
                                OrderType: String,
                                Limit: Float,
                                Quantity: Float,
                                QuantityRemaining: Float,
                                Commission: Float,
                                Price: Float,
                                PricePerUnit: Float,
                                IsConditional: Boolean,
                                Condition: String,
                                ImmediateOrCancel: Boolean,
                                Closed: String)
  // Responses
  case class BalanceResponse(success: Boolean, message: String, result: BalanceResult)

  case class BalancesResponse(success: Boolean, message: String, result: List[BalanceResult])

  case class DepositAddressResponse(success: Boolean, message: String, result: DepositAddressResult)

  case class OrderHistoryResponse(success: Boolean, message: String, result: List[OrderHistoryResult])

}

// collect your json format instances into a support trait:
trait BittrexJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  import Bittrex._

  // Results
  implicit val balanceResult          = jsonFormat5(BalanceResult)
  implicit val depositAddressResult   = jsonFormat2(DepositAddressResult)
  implicit val orderHistoryResult     = jsonFormat14(OrderHistoryResult)

  // Responses
  implicit val balanceResponse        = jsonFormat3(BalanceResponse)
  implicit val balancesResponse       = jsonFormat3(BalancesResponse)
  implicit val depositAddressResponse = jsonFormat3(DepositAddressResponse)
  implicit val orderHistResponse      = jsonFormat3(OrderHistoryResponse)
}

