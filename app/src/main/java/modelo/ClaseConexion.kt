package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion(): Connection? {
        try{
<<<<<<< HEAD
            val url = "jdbc:oracle:thin:@192.168.82.156:1521:xe"
            val usuario = "AARON_PTC"
            val contrasena = "Aaron230107"
=======
          
            val url = "jdbc:oracle:thin:@192.168.56.1:1521:xe"
            val usuario = "SYSTEM"
            val contrasena = "ITR2024"
>>>>>>> 3bafb5cb958f95ac5b8de292e02b0bf776095ef9

            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection
        }catch (error:Exception) {
            println("Este es el error: $error")
            return null

        }
    }

}