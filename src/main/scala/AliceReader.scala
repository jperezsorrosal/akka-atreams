import java.nio.file.{Path, Paths}
import java.nio.file.StandardOpenOption.{CREATE, WRITE, TRUNCATE_EXISTING}
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, RunnableGraph, Sink, Source}
import akka.util.ByteString


import scala.concurrent.Future

object AliceReader extends App {

  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val inputFile = Paths.get("Alice.txt")
  val outputFile = Paths.get("AliceCopy.txt")

  val source: Source[ByteString, Future[IOResult]] = FileIO.fromPath(inputFile)

  val sink: Sink[ByteString, Future[IOResult]] = FileIO.toPath(outputFile, Set(CREATE, WRITE, TRUNCATE_EXISTING))

  val runnableGraph: RunnableGraph[Future[IOResult]] = source.to(sink)

  runnableGraph.run().foreach { result =>
    println(s"${result.status}, ${result.count} bytes read.")
    system.terminate()
  }
}
