package limpiezaJson
import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json.Json

import Utils.General
import Models.movies
import Models.production_countries
object ProdCountriesDirty{
  //Formato del generos
  implicit val format = Json.format[production_countries]

  // Leer el archivo CSV y convertirlo a una lista de películas
  val movies = General.movies()

  val countriesProcessed = movies.flatMap { movie =>
    try {
      val cleanJson = General.cleanJson(movie.production_countries)
      val json = Json.parse(cleanJson)
      val atributos = json.as[List[production_countries]]

      atributos.map { countries =>
        (
          if(countries.iso_3166_1.trim.isEmpty) "Incomplete" else countries.iso_3166_1.trim.replaceAll("\\s+", ""),
          if(countries.name.trim.isEmpty) "Incomplete" else countries.name.trim.replaceAll("\\s+", "")
        )
      }
    } catch {
      case _: Exception => Nil
    }
  }

  val writer = new File(General.path("countries")).asCsvWriter[(String, String)](rfc.withHeader("iso_3166_1", "name"))
  writer.write(countriesProcessed).close()
}
