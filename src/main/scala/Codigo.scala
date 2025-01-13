import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import Models.Movies
object Codigo {
  val path2DataFile2 = "data/pi_movies_complete.csv"
  val dataSource1 = new File(path2DataFile2).readCsv[List, Movies](rfc.withHeader(true).withCellSeparator(';'))

  val rows = dataSource1.collect {
    case Right(movie) => movie
  }
  val rows_fail = dataSource1.collect {
    case Left(error) => error
  }

}
