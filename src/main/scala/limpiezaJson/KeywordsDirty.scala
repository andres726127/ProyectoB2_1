package limpiezaJson
import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json.Json

import Utils.General
import Models.movies
import Models.keywords

object KeywordsDirty{
  //Formato del generos
  implicit val format = Json.format[keywords]

  // Leer el archivo CSV y convertirlo a una lista de películas
  val movies = General.movies()

  val keywordsProcessed = movies.flatMap { movie =>
    try {
      val cleanJson = General.cleanJson(movie.keywords)
      val json = Json.parse(cleanJson)
      val atributos = json.as[List[keywords]]

      atributos.map { keyword =>
        ( keyword.id,
          if (keyword.name.trim.isEmpty) "Incomplete" else keyword.name.trim.replaceAll("\\s+", "")
        )
      }
    } catch {
      case _: Exception => Nil
    }
  }

  val writer = new File(General.path("keywords")).asCsvWriter[(Int, String)](rfc.withHeader("id", "name"))
  writer.write(keywordsProcessed).close()
}