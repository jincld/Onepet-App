package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion(): Connection? {
        try{

            val url = "jdbc:oracle:thin:@10.10.4.217:1521:xe"
            val usuario = "PAOEXPO_DEVELOPER"
            val contrasena = "ITR2024"

            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection
        }catch (error :Exception) {
            println("Este es el error: $error ")
            return null
        }
    }

}