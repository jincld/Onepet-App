package jonathan.orellana.onepetapp

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/*private suspend fun obtenerEtiquetas(): List<String> {
        val etiquetas = mutableListOf<String>()
        return etiquetas
    }

    private suspend fun crearEmpleado(nombre: String, correo: String, contra: String, rol: String): Boolean {
        return try {
            val objConexion = ClaseConexion().cadenaConexion()
            val addEmpleado = objConexion?.prepareStatement(
                "INSERT INTO tbUsuariosOne (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, rol) VALUES (?, ?, ?, ?, ?)"
            )
            val uuidEmpleado = UUID.randomUUID().toString()
            addEmpleado?.setString(1, uuidEmpleado)
            addEmpleado?.setString(2, nombre)
            val string = addEmpleado?.setString(3, contra)*/
