package pl.demo.first

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class Task3Test {
  import Task3._

  @Test
  def `test echo process`() {
    assertEquals("test", Task3.echoProcess("test"))
  }

  @Test
  def `test shred process with valid input`() {
    new Task3.ShredProcess().apply(".")
  }

  @Test
  def `test shred process with invalid input`() {
    assertThrows(classOf[IllegalArgumentException],
      () => new Task3.ShredProcess().apply("TOP SECRET"))
  }

  @Test
  def `test event stream process`() {
    assertEquals("test", new EventStreamProcess("test").apply(Unit))
  }
}