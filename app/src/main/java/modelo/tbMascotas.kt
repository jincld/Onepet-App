package modelo

data class tbMascotas(
    val UUID_mascota: String,
    var nombre_mascota: String,
    var raza: String?,
//    var sexo: String?,
    var procesos_previos: String?,
    var alergias: String?,
    var enfermedades_cronicas: String?,
    var fecha_nacimiento: String?,
    var peso: String?,
//    var especie: String?
)


