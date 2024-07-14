package modelo

import java.sql.Struct
import java.util.UUID

data class tbMascotas(
    var nombre_mascota: String?,
    val raza: String?,
    val sexo: String?,
    val procesos_previos: String?,
    val alergias: String?,
    val enfermedades_cronicas: String?,
    val fecha_nacimiento: String?,
    val peso: Int?,
    val especie: String?
)


