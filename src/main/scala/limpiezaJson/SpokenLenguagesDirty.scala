package limpiezaJson
import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json.Json

import Utils.General
import Models.movies
import Models.spoken_languages
object SpokenLenguagesDirty{
  //Formato del generos
  implicit val format = Json.format[spoken_languages]

  // Leer el archivo CSV y convertirlo a una lista de películas
  val movies = General.movies()

  val spokenProcessed = movies.flatMap { movie =>
    try {
      val cleanJson = General.cleanJson(movie.spoken_languages)
      val json = Json.parse(cleanJson)
      val atributos = json.as[List[spoken_languages]]

      atributos.map { spoken =>
        (
          if(spoken.iso_639_1.trim.isEmpty) "Incomplete" else spoken.iso_639_1.trim.replaceAll("\\s+", ""),
          if(spoken.name.trim.isEmpty) "Incomplete" else spoken.name.trim.replaceAll("\\s+", "")
        )
      }
    } catch {
      case _: Exception => Nil
    }
  }

  val writer = new File(General.path("Spoken_languages")).asCsvWriter[(String, String)](rfc.withHeader("iso_639_1", "name"))
  writer.write(spokenProcessed).close()
}