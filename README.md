# scalatrex version 0.1

A Bittrex API wrapper in Scala.

## Getting Started

```
implicit val actorSystem = ActorSystem("main")
implicit val executor: ExecutionContext = actorSystem.dispatcher
implicit val materializer: ActorMaterializer = ActorMaterializer()

// your api keys
val apikey = "x...x"
val secret = "x...x"
val authorization = Auth(apikey, secret)
val client = new BittrexClient()

val futureBalance = client.accountGetBalance(Auth(" ", " "), currency)
val response = Await.result(futureBalance, 5 second)
```

## Running the tests

Replace your own API keys and mod the tests to your environment.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration my Dog!


