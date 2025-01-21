package limpiezaJson
import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json.Json

import  Utils.General
import Models.movies
import Models.crew
object CrewDirty{

  implicit val format = Json.format[crew]
  // Leer el archivo CSV y convertirlo a una lista de películas
  val movies = General.movies()

  val crewProcessed = movies.flatMap { movie =>
    try {
      val cleanJson = General.cleanJson(movie.crew)
      val json = Json.parse(cleanJson)
      val atributos = json.as[List[crew]]

      atributos.map { crew =>
        ( crew.id,
          crew.credit_id,
          if( crew.department.trim.isEmpty) "Incomplete" else crew.department.trim.replaceAll("\\s+", ""),
          crew.gender,
          if( crew.job.trim.isEmpty) "Incomplete" else crew.job.trim.replaceAll("\\s+", ""),
          if( crew.name.trim.isEmpty) "Incomplete" else crew.name.trim.replaceAll("\\s+", ""),
          if( crew.profile_path.trim.isEmpty) "Incomplete" else crew.profile_path.trim.replaceAll("\\s+", ""),
        )
      }
    } catch {
      case _: Exception => Nil
    }
  }

  val writer = new File(General.path("crew")).asCsvWriter[(Int, String, String, Int, String, String, String)](rfc.withHeader("id",
    "credit_id", "department", "gender", "job", "name", "profile_path"))
  writer.write(crewProcessed).close()
}
