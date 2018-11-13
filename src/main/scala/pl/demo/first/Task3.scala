package pl.demo.first

object Task3 {

  /**
    * Create the following implementations of `Process` interface:
    * * `EchoProcess<T>: Process<T, T>` - the `run` method should simply return the `input` argument,
    * * `ShredProcess<A>: Process<A, Unit>` - the `run` method should check if the string representation of input
    *   (i.e. the value returned by calling `toString`) contains "TOP SECRET" and if it's not it should throw [IllegalArgumentException],
    * * `EventStreamProcess(output: B): Process<A, Unit>` - the `run` method should always return output given in constructor.
    */
//  def process[A, B](input: A): B
  // @link scala.Function1

  def echoProcess[A](a: A): A = a

  class ShredProcess[A] extends Function1[A, Unit] {
    override def apply(a: A): Unit = {
      if (a.toString.contains("TOP SECRET"))
        throw new IllegalArgumentException("...")
    }
  }

  class EventStreamProcess[B](private val output: B) extends Function1[Unit, B] {
    override def apply(v1: Unit): B =
      output
//      return output
  }

//  def ShredProcess[A](a: A): Unit =
//    if (a.toString.contains("TOP SECRET"))
//      throw new IllegalArgumentException("...")

//  class EchoProcess[A] extends Function1[A, A] {
//    override def apply(v1: A): A =
//      v1
//  }

//  interface Process<in A, out B> {
//    fun run(input: A): B
//  }
//
//  class EchoProcess<A> : Process<A, A> {
//    override fun run(input: A): A {
//      return input
//    }
//
//  }
//
//  class ShredProcess<A>: Process<A, Unit> {
//    override fun run(input: A) {
//      if (!input.toString().contains("TOP SECRET"))
//        throw IllegalArgumentException("no secrets pls")
//    }
//
//  }
//
//  class EventStreamProcess<B>(private val output: B): Process<Unit, B> {
//    override fun run(input: Unit): B {
//      return output
//    }
//  }

  def SharedProcess() = {}

}
