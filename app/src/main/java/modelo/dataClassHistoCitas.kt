package modelo

data class dataClassHistoCitas(
    var UUID_Cita: String,
    var fecha_cita: String,
    var motivo_cita: String,
    var descripcion_motivo: String,
    var mascota: String,
    var vet: String,
    var estado: String,
    var usuario: String
)
