package Utils

import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json._

import Models.movies
import Models.genres
object Estadisticas extends App {
  def Allgenres(): Unit = {
    val movies = General.movies()
    implicit val genreFormat = Json.format[genres]
    val allGenres = movies.flatMap(_.genres)
    val genresUnique = allGenres.distinct
    val genres = General.cleanJson(genresUnique.toString())
    println(s"Generos totales: ${genres.length}")
  }
}
