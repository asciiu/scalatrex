package com.flow.bittrex.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

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

  case class OrderResult(OrderUuid: String,
                         Exchange: String,
                         OrderType: String,
                         Limit: Float,
                         Quantity: Float,
                         QuantityRemaining: Float,
                         CommissionPaid: Float,
                         Price: Float,
                         CancelInitiated: Boolean,
                         Opened: String,
                         ImmediateOrCancel: Boolean,
                         IsConditional: Boolean)

  // Standard bittrex response json has success, message, and option result
  case class StandardResponse[T](success: Boolean, message: String, result: Option[T])

  // All bittrex responses return some sort of result
  type BalanceResponse = StandardResponse[BalanceResult]
  type BalancesResponse = StandardResponse[List[BalanceResult]]
  type DepositAddressResponse = StandardResponse[DepositAddressResult]
  type OrderHistoryResponse = StandardResponse[List[OrderHistoryResult]]
  type GetOpenOrdersResponse = StandardResponse[List[OrderResult]]
}

// collect your json format instances into a support trait:
trait BittrexJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  import Bittrex._

  // result formatters
  implicit val balanceResult          = jsonFormat5(BalanceResult)
  implicit val depositAddressResult   = jsonFormat2(DepositAddressResult)
  implicit val orderHistoryResult     = jsonFormat14(OrderHistoryResult)
  implicit val openOrderResult        = jsonFormat12(OrderResult)

  // formatters for bittrex responses requires result formatters above
  implicit val balanceReponse         = jsonFormat3(StandardResponse[BalanceResult])
  implicit val balancesResponse       = jsonFormat3(StandardResponse[List[BalanceResult]])
  implicit val depositAddressResponse = jsonFormat3(StandardResponse[DepositAddressResult])
  implicit val orderHistResponse      = jsonFormat3(StandardResponse[List[OrderHistoryResult]])
  implicit val openOrderResponse      = jsonFormat3(StandardResponse[List[OrderResult]])
}


