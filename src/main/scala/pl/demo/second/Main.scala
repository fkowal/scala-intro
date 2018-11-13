package pl.demo.second

import java.nio.file.{Files, Paths}
import java.time.LocalDate

import pl.demo.second.Main.TaskBoard

object Main {
  /*
    One of the teams used task board to organize their work in project aimed to launch
    a simple video streaming service. The task board was divided into columns: To Do,
    In Progress, To Verify, Done. Subsequent system features were placed on cards on
    the task board and then moved between that columns, as work progressed.

    File task_history.csv stores all events that happened every day on the task board. Each event consists of:
    - date of event in format YYYY-MM-DD,
    - name of the task, which is also a task identifier,
    - type of event; there are three possibilities: task creation, task movement to another column, task removal from board,
    - additional data depending on type of event:
        - for task creation event: difficulty level, (integer from 1 to 5, optional)
        - for task movement event: target column,
        - for task removal event: no additional data.

    All events in the file are sorted chronologically. The project started on 2017-01-01 and finished on 2017-01-18.

    Your role is to restore task board state on 2017-01-15. The entire code should be placed in this file.

    For fun: Try to implement every used function as single expression function i.e. 'def someName(...) = ...'
    Limitation doesn't apply to lambdas. Lambdas can contain multiple lines.

    Follow the steps. Good luck.
   */

  /*
    STEP 1:

    Fill Column enum according to task board description above.
   */
//enum class Column {
//  TODO,
//  IN_PROGRESS,
//  TO_VERIFY,
//  DONE
//}
  sealed trait Column
  case object TODO extends Column
  case object IN_PROGRESS extends Column
  case object TO_VERIFY extends Column
  case object DONE extends Column

  object Column {
    def apply(column: String): Column =
      column match {
        case "TODO"        => TODO
        case "IN_PROGRESS" => IN_PROGRESS
        case "TO_VERIFY"   => TO_VERIFY
        case "DONE"        => DONE
      }
  }

  /*
    STEP 2:

    Each line in the file contains single event. Before you try to implement file parsing,
    create all event types as classes extending TaskEvent. We'll be using LocalDate
    to store date.

    Hints:
    - data classes are rather discouraged in this step (inheritance).
   */
  sealed abstract class TaskEvent {
    val date: LocalDate
    val taskName: String
  }
  case class CreateEvent(override val date: LocalDate,
                         override val taskName: String,
                         dificulty: Int)
      extends TaskEvent
  case class MoveEvent(override val date: LocalDate,
                       override val taskName: String,
                       column: Column)
      extends TaskEvent
  case class RemoveEvent(override val date: LocalDate,
                         override val taskName: String)
      extends TaskEvent

  /*
    STEP 3:

    Implement parseFile(). The function takes a path to CSV file with task history (src/main/resources/task_history.csv)
    and returns list of parsed events.

    Hints:
    - use Files.readAllLines() to read the file,
    - do not use sophisticated solution for parsing csv, just split lines with String.split(),
    - 'when' expression can be handy,
    - in case of an error, throw IllegalArgumentException with meaningful message.
   */
  def parseFile(path: String): List[TaskEvent] = {
    import scala.collection.JavaConverters._
    Files
      .readAllLines(Paths.get(path))
      .asScala
      .toList
      .map(_.split(","))
      .map(
        strs =>
          strs(2) match {
            case "CREATED" =>
              CreateEvent(
                date = LocalDate.parse(strs(0)),
                taskName = strs(1),
                dificulty =
                  if (strs.length == 4) strs(3).toInt else DEFAULT_DIFFICULTY
              )
            case "DELETED" =>
              RemoveEvent(date = LocalDate.parse(strs(0)), taskName = strs(1))
            case _ =>
              MoveEvent(
                date = LocalDate.parse(strs(0)),
                taskName = strs(1),
                column = Column(strs(3))
              )

        }
      )
  }

  /*
    In the CSV file, task creation event may not have task's difficulty level.
    Take the default difficulty level as defined below.
   */
  private val DEFAULT_DIFFICULTY = 3

  /*
    STEP 4:

    Now, implement class that represents state of a task board. The task board should be immutable,
    what implies that all methods modifying its state should return new task board with that state.

    Hints:
    - due to immutability and no inheritance, data class can be quite useful,
    - it is very likely you will need Task class for internal task representation; feel free
        to create one, also as immutable type,
    - use previously defined Column enum to represent columns.
   */
  case class Task(name: String,
                  dificulty: Int = DEFAULT_DIFFICULTY,
                  column: Column = TODO) {
    override def toString: String = s"$name - ($dificulty)"
  }
  case class TaskBoard(tasks: List[Task] = List()) {

    /*
      STEP 5:

      Write apply() function which returns modified state of the task board based on an event.

      Hints:
      - once again, 'when' can be good choice,
      - remember to apply default difficulty level,
      - in case of an error, throw IllegalArgumentException with meaningful message.
     */
    def apply(event: TaskEvent): TaskBoard = event match {
      case create: CreateEvent =>
        this.copy(tasks :+ Task(event.taskName, create.dificulty))

      case _: RemoveEvent =>
        this.copy(tasks.filter(_.name != event.taskName))

      case move: MoveEvent =>
        this.copy(tasks.map {
          case task: Task if task.name == event.taskName =>
            task.copy(column = move.column)
          case task => task
        })
    }

    /*
      STEP 6:

      Implement print() function. Print function should display current state of the task board on console.
      The display format is this:

      TO DO:
      - [task name] ([difficulty level])
      IN PROGRESS:
      - [task name] ([difficulty level])
      TO VERIFY:
      - [task name] ([difficulty level])
      DONE:
      - [task name] ([difficulty level])

      for example:

      TO DO:
      - Add account management (2)
      - Implement video upload (5)
      IN PROGRESS:
      TO VERIFY:
      DONE:

      In each column, tasks should be sorted by name.

      Hints:
      - use string templates,
      - you can iterate over enum values and replace _ to whitespace to have column names.
     */
    def print(): Unit = {
      println("TO DO:")
      tasks.filter(_.column == TODO).foreach(println)
      println("IN PROGRESS:")
      tasks.filter(_.column == IN_PROGRESS).foreach(println)
      println("TO VERIFY:")
      tasks.filter(_.column == TO_VERIFY).foreach(println)
      println("DONE:")
      tasks.filter(_.column == DONE).foreach(println)
    }
  }
}

/*
    STEP 6:

    Finally, implement main function. A target date should be specified as a program argument.
    Remember to filter out all events that happened after that date. They won't be needed.
    The final console output should look like this:

    TO DO:
    IN PROGRESS:
    - Create video search (3)
    TO VERIFY:
    - Add playlist management (3)
    - Implement video comments (4)
    DONE:

    which is a correct solution of this exercise.
*/
object Run extends App {
  import Main._

  val path = getClass.getClassLoader.getResource("second/task_history.csv").getPath
  val events = parseFile(path)

  def aggr(board: TaskBoard, event: TaskEvent): TaskBoard = board.apply(event)
  //    val board = events.foldLeft(TaskBoard())(aggr) //{ (agg, ev) -> agg.apply(ev)})
  val board = events
    .filter(_.date.isBefore(LocalDate.parse("2017-01-16")))
    .foldLeft(TaskBoard())(aggr)

  board.print()
}
