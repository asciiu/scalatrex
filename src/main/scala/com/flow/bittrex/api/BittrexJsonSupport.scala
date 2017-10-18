package com.flow.bittrex.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

object Bittrex {

  // Results
  case class BalanceResult(Currency: String, Balance: Float, Available: Float, Pending: Float,
                           CryptoAddress: Option[String])

  case class DepositAddressResult(Currency: String, address: Option[String])

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


  case class SingleOrderResult(OrderUuid: String,
                               Exchange: String,
                               Type: String,
                               Quantity: Double,
                               QuantityRemaining: Double,
                               Limit: Double,
                               Reserved: Double,
                               ReserveRemaining: Double,
                               CommissionReserved: Double,
                               CommissionReserveRemaining: Double,
                               CommissionPaid: Double,
                               Price: Double,
                               Opened: String,
                               IsOpen: Boolean,
                               Sentinel: String,
                               CancelInitiated: Boolean,
                               ImmediateOrCancel: Boolean,
                               IsConditional: Boolean)

  case class UUIDResult(uuid: String)

  case class WithdrawalResult(PaymentUuid: String,
                              Currency: String,
                              Amount: Double,
                              Address: String,
                              Opened: String,
                              Authorized: Boolean,
                              PendingPayment: Boolean,
                              TxCost: Double,
                              TxId: String,
                              Canceled: Boolean,
                              InvalidAddress: Boolean)

  case class DepositResult(Id: Int,
                           Amount: Double,
                           Currency: String,
                           Confirmations: Int,
                           LastUpdated: String,
                           TxId: String,
                           CryptoAddress: String)

  // Standard bittrex response json has success, message, and option result
  case class StandardResponse[T](success: Boolean, message: String, result: Option[T])
  case class StandardNullResponse(success: Boolean, message: String)

  // All bittrex responses return some sort of result
  type BalanceResponse = StandardResponse[BalanceResult]
  type BalancesResponse = StandardResponse[List[BalanceResult]]
  type DepositAddressResponse = StandardResponse[DepositAddressResult]
  type OrderHistoryResponse = StandardResponse[List[OrderHistoryResult]]
  type GetOpenOrdersResponse = StandardResponse[List[OrderResult]]
  type UuidResponse = StandardResponse[UUIDResult]
  type SingleOrderResponse = StandardResponse[SingleOrderResult]
  type HistoryResponse = StandardResponse[List[WithdrawalResult]]
  type DepositResponse = StandardResponse[List[DepositResult]]
}

// collect your json format instances into a support trait:
trait BittrexJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  import Bittrex._

  // result formatters
  implicit val balanceResult          = jsonFormat5(BalanceResult)
  implicit val depositAddressResult   = jsonFormat2(DepositAddressResult)
  implicit val orderHistoryResult     = jsonFormat14(OrderHistoryResult)
  implicit val openOrderResult        = jsonFormat12(OrderResult)
  implicit val orderUuidResult        = jsonFormat1(UUIDResult)
  implicit val singleOrderResult      = jsonFormat18(SingleOrderResult)
  implicit val histResult             = jsonFormat11(WithdrawalResult)
  implicit val depositResult          = jsonFormat7(DepositResult)

  // formatters for bittrex responses requires result formatters above
  implicit val balanceReponse         = jsonFormat3(StandardResponse[BalanceResult])
  implicit val balancesResponse       = jsonFormat3(StandardResponse[List[BalanceResult]])
  implicit val depositAddressResponse = jsonFormat3(StandardResponse[DepositAddressResult])
  implicit val orderHistResponse      = jsonFormat3(StandardResponse[List[OrderHistoryResult]])
  implicit val openOrderResponse      = jsonFormat3(StandardResponse[List[OrderResult]])
  implicit val orderUuidResponse      = jsonFormat3(StandardResponse[UUIDResult])
  implicit val nullResponse           = jsonFormat2(StandardNullResponse)
  implicit val singleOrderResponse    = jsonFormat3(StandardResponse[SingleOrderResult])
  implicit val histResponse           = jsonFormat3(StandardResponse[List[WithdrawalResult]])
  implicit val depositResponse        = jsonFormat3(StandardResponse[List[DepositResult]])
}


