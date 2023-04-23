# Gilded Rose ![Workflow](https://github.com/jivagoalves/gilded-rose/actions/workflows/gradle.yml/badge.svg)

This is an attempt of implementing the [Gilded Rose](https://kata-log.rocks/gilded-rose-kata) kata in [Kotlin](https://kotlinlang.org) and [Spring](https://spring.io/) using principles from [Type-Driven Design](https://fsharpforfunandprofit.com/series/designing-with-types) and
the [hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)). 

## The Gilded Rose kata

Gilded Rose is a small inn whose stock control system needs to be developed. The items are constantly degrading in quality as they approach their sell by date. In summary, the behaviour of the system is:

* All items have a SellIn value which denotes the number of days we have to sell the item
* All items have a Quality value which denotes how valuable the item is
* At the end of each day our system lowers both values for every item

There are also some exceptional behaviour and invariants such as:

* Once the sell by date has passed, Quality degrades twice as fast
* The Quality of an item is never negative
* "Aged Brie" actually increases in Quality the older it gets
* The Quality of an item is never more than 50
* "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
* "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
  * Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
  * Quality drops to 0 after the concert

Just for clarification, an item can never have its Quality increase above 50, however "Sulfuras" is a legendary item and as such its Quality is 80 and it never alters.

## Getting Started

Please ensure you have JDK 17.

Build the project by running:
```
./gradlew build
```

Run the web app (API) with:
```
./gradlew bootRun
```

## Stock API

[Swagger UI](http://localhost:8080/swagger-ui/index.html) | [Postman](Stock%20API.postman.json)

You can now:

* Add a new item to the stock:
```
curl --location 'localhost:8080/api/items' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Orange",
    "registeredOn": "2023-02-27",
    "sellBy": "2023-03-15",
    "quality": 35
}'
```

* List all items in the stock:
```
curl --location 'localhost:8080/api/items'
```

* List all items in the stock as of date:
```
curl --location 'localhost:8080/api/items/as-of/2023-03-01'
```

* Remove items from the stock:
```
curl --location --request DELETE 'localhost:8080/api/items/1'
```

Please bear in mind this in under development and not all features are accessible via the web API. Stay tuned as we add them! :)

## Architecture

The project is divided into two modules: `application` and `web`:

![Architecture.jpg](https://github.com/jivagoalves/gilded-rose/blob/master/docs/Architecture.jpg?raw=true)

The `application` module contains the business logic responsible for making sure the system behaves as it should. This is done by making illegal states unrepresentable at the type system level. If possible, we always implement invariants at the type level instead of using tests. Code should not compile if it does not make sense according to the business rules. For example, `Quality` class can never be instantiated with a negative value. The application is agnostic to any infrastructure that it depends on (e.g. REST API, DB). Using the hexagonal architecture concepts, there should be always a port or adapter to ensure the boundaries. As such, the application does not depend on the `web` module but on interfaces implemented and injected by external modules.

The `web` module is responsible for providing the infrastructure such as the web server and the DB. It depends on the `application` in order to accomplish any use case.

## Disclaimer

This is an attempt and does not represent the only "correct" way of doing things. Please take it as an experiment. Feedback is welcome!

Hope you enjoy it!
