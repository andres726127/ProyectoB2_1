package Utils

import java.io.File
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import play.api.libs.json._


import Models.movies
object General extends App{
  def cleanJson(Json: String): String = {
    Json
      .trim
      .replaceAll("'", "\"") // Cambia comillas simples por dobles
      .replaceAll("None", "null") // Reemplaza "None" por "null"
      .replaceAll("""\\""", "") // Elimina caracteres de escape innecesarios
      .replaceAll("True", "true") // Corrige valores booleanos
      .replaceAll("False", "false")
      .replaceAll("[^\\x00-\\x7F]", "corrupted")
  }
  def movies(): List[movies] = {
    val pathDataFile = "data/pi_movies_small.csv"
    new File(pathDataFile).readCsv[List, movies](rfc.withHeader(true).withCellSeparator(';'))
      .collect {
        case Right(movie) => movie
      }.toList
  }
  def path(name: String): String = {
    s"data/${name}.csv"
  }
}
