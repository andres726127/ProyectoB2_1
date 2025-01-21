package limpiezaJson

import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json.Json
import Utils.General
import Models.movies
import Models.belongs_to_collection

object CollectionsDirty {

  implicit val format = Json.format[belongs_to_collection]

  // Leer el archivo CSV y convertirlo a una lista de películas
  val movies = General.movies()

  val collectionsProcessed = movies.flatMap { movie =>
    try {
      // Corregir JSON mal formateado
      val cleanJson = General.cleanJson(movie.belongs_to_collection)
      val json = Json.parse(cleanJson)
      val collection = json.as[belongs_to_collection]  // Deserializar a un solo objeto

      // Retornar una tupla con los atributos del objeto como una lista de un solo elemento
      List((collection.id,
        if (collection.name.trim.isEmpty) "Incomplete" else collection.name.trim.replaceAll("\\s+", " "), // Reemplazar si name está vacío
        Option(collection.poster_path).map(_.trim).filterNot(_.isEmpty).getOrElse("Incomplete"), // Reemplazar si poster_path está vacío
        Option(collection.backdrop_path).map(_.trim).filterNot(_.isEmpty).getOrElse("Incomplete") // Reemplazar si backdrop_path está vacío
      ))
    } catch {
      case _: Exception => Nil
    }
  }

  val writer = new File(General.path("collections"))
    .asCsvWriter[(Int, String, String, String)](rfc.withHeader("id", "name", "poster_path", "backdrop_path"))

  writer.write(collectionsProcessed).close()
}
