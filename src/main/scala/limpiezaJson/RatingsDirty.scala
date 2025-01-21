package limpiezaJson
import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json.Json

import Models.movies
import Models.ratings
import Utils.General
object RatingsDirty{
  //Formato del generos
  implicit val format = Json.format[ratings]

  // Leer el archivo CSV y convertirlo a una lista de películas
  val movies = General.movies()

  val ratingsProcessed = movies.flatMap { movie =>
    try {
      val cleanJson = General.cleanJson(movie.ratings)
      val json = Json.parse(cleanJson)
      val atributos = json.as[List[ratings]]

      atributos.map { ratings =>
        (ratings.userId, ratings.rating, ratings.timestamp)
      }
    } catch {
      case _: Exception => Nil
    }
  }

  val writer = new File(General.path("ratings")).asCsvWriter[(Int, Double, Double)](rfc.withHeader("user_id", "ratings", "timestamp"))
  writer.write(ratingsProcessed).close()
}