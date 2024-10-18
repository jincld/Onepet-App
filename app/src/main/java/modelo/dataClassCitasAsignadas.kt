package modelo

data class dataClassCitasAsignadas(
    //datos para citas
    var UUID_Cita: String,
    var fecha_cita: String,
    var motivo_cita: String,
    var descripcion_motivo: String,
    var estado: String,
    var mascota: String,
    var vet: String,
    var usuario: String,
    var detalle_cita: String
)
