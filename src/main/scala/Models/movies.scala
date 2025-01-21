package Models

case class movies(
                   adult: Boolean,
                   belongs_to_collection: String,
                   budget: Int,
                   genres: String, // JSON como cadena
                   homepage: String,
                   id: Int,
                   imdb_id: String,
                   original_language: String,
                   original_title: String,
                   overview: String,
                   popularity: Double,
                   poster_path: String,
                   production_companies: String, // JSON como cadena
                   production_countries: String, // JSON como cadena
                   release_date: String,
                   revenue: Long,
                   runtime: Int,
                   spoken_languages: String, // JSON como cadena
                   status: String,
                   tagline: String,
                   title: String,
                   video: Boolean,
                   vote_average: Double,
                   vote_count: Int,
                   keywords: String, // JSON como cadena
                   cast: String, // JSON como cadena
                   crew: String, // JSON como cadena
                   ratings: String // JSON como cadena
                 )
