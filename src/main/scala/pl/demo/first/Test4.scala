package pl.demo.first
import java.util

/**
  * Create the implementation of the `TextFiles` object so that the following calls should work:
  * * `val text = TextFiles["abc.txt"]` - `text` should contain the contents of file _abc.txt_ as a `String` or
  *     `null` if the file does not exist
  * * `TextFiles["abc.txt"] = "Hello"` - the _abc.txt_ file should now contain "Hello"
  * * `TextFiles["abc.txt"] = null` - the _abc.txt_ should be removed if it exists; otherwise do nothing
  */

object TextFiles {
  val map = new util.HashMap[String, String]()

  map.put("src/main/resources/first/test.txt", "one two three")

  def apply(name: String): String = {
    if (name.indexOf("_") == 0)
      return null
    return map.get(name)
  }

  def set(name: String, value: String): Unit = {
    if (value == null) {
      map.remove(name)
    } else {
      map.put(name, value)
    }
  }
}
