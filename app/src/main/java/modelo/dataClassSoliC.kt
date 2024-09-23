package modelo

data class dataClassSoliC (

    //datos para solicitud de cita
    var UUID_Cita: String,
    var fecha_cita: String,
    var motivo_cita: String,
    var descripcion_motivo: String,
    var mascota: String,
    var vet: String,
    var usuario: String
)
