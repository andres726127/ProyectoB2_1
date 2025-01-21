package limpiezaJson
import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json.Json
import Models.movies
import Models.production_companies
import Utils.General
object ProdCompaniesDirty{
  //Formato del generos
  implicit val format = Json.format[production_companies]

  // Leer el archivo CSV y convertirlo a una lista de películas
  val movies = General.movies()

  val companiesProcessed = movies.flatMap { movie =>
    try {
      val cleanJson = General.cleanJson(movie.production_companies)
      val json = Json.parse(cleanJson)
      val atributos = json.as[List[production_companies]]

      atributos.map { companies =>
        ( companies.id,
          if(companies.name.trim.isEmpty) "Incomplete" else companies.name.trim.replaceAll("\\s+", "")
        )
      }
    } catch {
      case _: Exception => Nil
    }
  }

  val writer = new File(General.path("companies")).asCsvWriter[(Int, String)](rfc.withHeader("id", "name"))
  writer.write(companiesProcessed).close()
}
