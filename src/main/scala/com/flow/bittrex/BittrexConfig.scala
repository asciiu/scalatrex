package com.flow.bittrex

import pureconfig._

object BittrexConfig {
  case class Config(bittrex: BittrexConfigPath)
  case class BittrexConfigPath(apikey: String, secret: String)

  val config: Either[pureconfig.error.ConfigReaderFailures, Config] = loadConfig[Config]
}