package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {


    fun cadenaConexion(): Connection? {
        try{
          
            val url = "jdbc:oracle:thin:@192.168.0.18:1521:xe"
            val usuario = "JONATHAN_PTC"
            val contrasena = "JONATHAN_PTC"

            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection
        }catch (error:Exception) {
            println("Este es el error: $error")
            return null

        }
    }

}