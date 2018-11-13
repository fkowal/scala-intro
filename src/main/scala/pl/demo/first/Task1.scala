package pl.demo.first

/**
  * Define a final class `MyError` with a constructor that has the following parameters:
  * * `code` of nullable type `String` - with no default value
  * * `message` of type `String` - with no default value
  * * `httpStatus` of type `Int` - with the default value of 500
  *
  * Define a `toString` function which return the `httpStatus` and `message` separated by a colon, e.g.
  * for `httpStatus` = 404 and `message` = "abc" it should return "404:abc"
  *
  * The value of `code` does not have to be preserved.
  */

class MyError(code: String, message: String, httpStatus: Int = 500) {
  override def toString() = s"$httpStatus:$message"
}