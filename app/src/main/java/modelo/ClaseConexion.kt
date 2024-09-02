package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {


    fun cadenaConexion(): Connection? {
        try{
            val url = "jdbc:oracle:thin:@192.168.88.241:1521:xe"
            val usuario = "AARON_PTC"
            val contrasena = "Aaron230107"


            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection
        }catch (error:Exception) {
            println("Este es el error: $error")
            return null

        }
    }

}