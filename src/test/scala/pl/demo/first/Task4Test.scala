package pl.demo.first

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class Task4Test {

  @Test
  def `test file reading`() {
    val testTxtContent = TextFiles("src/main/resources/first/test.txt")

    assertEquals("one two three", testTxtContent)
  }

  @Test
  def `test non-existing file reading`() {
    val testTxtContent = TextFiles("_non_existing.txt")

    assertNull(testTxtContent)
  }

  @Test
  def `test file writing and deleting`() {
//    TextFiles("tmp.txt") = "Hello world" // TODO checkout scala setter method name

//    val tmpTxtContent = TextFiles("tmp.txt")
//    assertEquals("Hello world", tmpTxtContent)
//
//    TextFiles["tmp.txt"] = null
//
//    val tmpTxtContent2 = TextFiles("tmp.txt")
//    assertNull(tmpTxtContent2)
//
//    TextFiles["tmp.txt"] = null
//
//    val tmpTxtContent3 = TextFiles("tmp.txt")
//    assertNull(tmpTxtContent3)
  }

}
