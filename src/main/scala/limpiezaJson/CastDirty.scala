package limpiezaJson
import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json.Json


import Models.movies
import Models.cast
import Utils.General
object CastDirty {
  val movies = General.movies()
  implicit val castFormat = Json.format[cast]

  val processedCast = movies.flatMap { movie =>
    try {
      val cleanJson = General.cleanJson(movie.cast)
      val json = Json.parse(cleanJson)
      val atributos = json.as[List[cast]]

      atributos.map { cast =>
        (
          cast.cast_id,
          if (cast.character.trim.isEmpty) "Incomplete" else cast.character.trim.replaceAll("\\s+", " "),
          if (cast.credit_id.trim.isEmpty) "Incomplete" else cast.credit_id.trim.replaceAll("\\s+", " "),
          cast.gender,
          cast.id,
          if (cast.name.trim.isEmpty) "Incomplete" else cast.name.trim.replaceAll("\\s+", " "),
          cast.order,
          Option(cast.profile_path)
            .map(_.trim.replaceAll("\\s+", " "))
            .filterNot(_.isEmpty)
            .getOrElse("Incomplete")
        )
      }
    } catch {
      case _: Exception => Nil
    }
  }

  val writer = new File(General.path("cast")).asCsvWriter[(Int, String, String, Int, Int, String, Int, String)](rfc.withHeader("cast_id", "character", "credit_id", "gender", "id", "name", "order", "profile_path"))
  writer.write(processedCast).close()
}
