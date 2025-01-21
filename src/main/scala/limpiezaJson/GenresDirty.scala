import java.io.File
import kantan.csv._
import kantan.csv.ops._
import play.api.libs.json._

import Models.genres
import Utils.General
object GenresDirty extends App {
  //Formato del generos
  implicit val format = Json.format[genres]

  // Leer el archivo CSV y convertirlo a una lista de películas
  val movies = General.movies()

  val genresProcessed = movies.flatMap { movie =>
    try {
      val cleanJson = General.cleanJson(movie.genres)
      val json = Json.parse(cleanJson)
      val atributos = json.as[List[genres]]

      atributos.map { genre =>
        (
          genre.id,
          if (genre.name.trim.isEmpty) "Incomplete" else genre.name.trim.replaceAll("\\s+", " "))// Reemplaza si name está vacío
      }
    } catch {
      case _: Exception => Nil
      }
    }

  val writer = new File(General.path("genres")).asCsvWriter[(Int, String)](rfc.withHeader("id", "name"))
  writer.write(genresProcessed).close()
}