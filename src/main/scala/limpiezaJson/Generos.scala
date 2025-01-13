package limpiezaJson
import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json.Json

import Models.Movies
case class Generos(ID: Int, Name: String)
object generosSucios extends App {

  val path2DataFile2 = "data/pi_movies_small.csv"
  val movies: List[Movies] = new File(path2DataFile2).readCsv[List, Movies](rfc.withHeader(true).withCellSeparator(';'))
    .collect {
      case Right(movie) => movie
    }.toList

  //Función para limpiar el Json
  def limpiarGenerosJson(genreJson: String): String = {
    genreJson
      .trim
      .replaceAll("'", "\"")
      .replaceAll("None", "null")
      .replaceAll("""\\""", "")
      .replaceAll("True", "true")
      .replaceAll("False", "false")
  }

  implicit val genreFormat = Json.format[Generos]

  val jsonLimpio = limpiarGenerosJson(movies.map(_.genres).mkString("\n"))
  println(s"Generos:\n${jsonLimpio}")

}


/*
  val json = Json.parse(jsonLimpio)

  val genres = (json \ "genre").as[List[Generos]]

  println("Generos procesados: ")
  genres.foreach { genre =>
    println(s"ID: ${genre.ID}, Genero: ${genre.Name}")
  }
*/
