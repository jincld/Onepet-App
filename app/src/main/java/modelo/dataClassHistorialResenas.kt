package modelo

data class dataClassHistorialResenas(
    //datos para reseñas
    var UUID_resena: String,
    var calificacion: Int,
    var comentarios: String,
    var resenador: String,
    var vet: String
)
