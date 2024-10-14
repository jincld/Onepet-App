package jonathan.orellana.onepetapp

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import jonathan.orellana.onepetapp.databinding.ActivityMainBinding
import jonathan.orellana.onepetapp.iniciarsesion.variablesLogin.valorCorreoUsuario
import jonathan.orellana.onepetapp.iniciarsesion.variablesLogin.valorRolUsuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.sql.SQLException

class MainActivity : AppCompatActivity() {



    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    companion object variablesMainActivity {
        lateinit var nombre_user: String
        lateinit var fotoUserV: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val txtcorreoiniciar = findViewById<EditText>(R.id.txtcorreoiniciar)
        //val txtNombreMenu = findViewById<TextView>(R.id.txtNombreUserMenu)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        changeStatusBarColor("#171717")


        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(

                R.id.nav_homeDV, R.id.agregarempleadodv, R.id.misempleadosdv, R.id.solicitudescitadv, R.id.historialcitasdvvv, R.id.clientesdv, R.id.fragment_veterinarias, R.id.chatdv, R.id.resenasdv, R.id.ajustesdv, R.id.fragment_citas, R.id.fragment_agendarCita, R.id.fragment_estadoSolicitud, R.id.agregarempleadodv, R.id.misempleadosdv, R.id.fragment_asignaciones, R.id.fragment_misMascotas, R.id.agregar_vet, R.id.actualizar_y_eliminar_vet2, R.id.agregarempleadodv, R.id.misResenas, R.id.ver_veterinarias_usuario, R.id.agregarmascotaas, R.id.asignarcitadv, R.id.fragment_historialresenas, R.id.fragment_historialAsignacionesEmp, R.id.fragment_citasAsignadas, R.id.fragment_citasatendidas

            ), drawerLayout
        )

        val navigationView: NavigationView = findViewById(R.id.nav_view)

        val headerView = navigationView.getHeaderView(0)
        val btnCerrarMenu: ImageButton = headerView.findViewById(R.id.btnCerrarDrawer)
        val btnOtrosMenu: ImageButton = headerView.findViewById(R.id.btnOtrosMenu)

        btnCerrarMenu.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        btnOtrosMenu.setOnClickListener {

        }

        val navigationView2: NavigationView = findViewById(R.id.nav_view)
        val headerView2 = navigationView2.getHeaderView(0)
        val txtNombreMenu: TextView = headerView2.findViewById(R.id.txtNombreUserMenu)
        val imgMenu: ImageView = headerView2.findViewById(R.id.imgMenu)


//traemos el nombre con un select
        suspend fun traerNombreUser(valorCorreoUsuario: String): String? {
            return withContext(Dispatchers.IO) {
                var nombreUser: String? = null
                val objConexion = ClaseConexion().cadenaConexion()
                val preparedStatement =
                    objConexion?.prepareStatement("SELECT nombre_usuario FROM tbusuariosOne WHERE correo_usuario = ?")
                preparedStatement?.setString(1, valorCorreoUsuario)
                println("..........ESTE ES EL VALOR CORREO: " + valorCorreoUsuario)

                try {
                    val resultSet = preparedStatement?.executeQuery()
                    if (resultSet?.next() == true) {
                        nombreUser = resultSet.getString("nombre_usuario")
                        println("++++ESTE ES EL NOMBRE DENTRO DE LA FUNCION DE TRAER NOMBRE: " + nombreUser)
                    } else {
                        println("++++POSIBLE ERROR EN ELSE: " + nombreUser)
                    }
                } catch (e: SQLException) {
                    // Manejar la excepción
                    e.printStackTrace()
                } finally {
                    // Asegúrate de cerrar recursos aquí
                    preparedStatement?.close()
                    objConexion?.close()
                }

                nombreUser
            }
        }



//Asignar el nombre al texto del menú

        CoroutineScope(Dispatchers.Main).launch {
            val valorRolUsuario = valorCorreoUsuario
            val nombreUsuario = traerNombreUser(valorCorreoUsuario)
            txtNombreMenu.text = nombreUsuario
            nombre_user = nombreUsuario.toString()
            println("ESTE ES EL NOMBRE TRAIDO:" + nombreUsuario)
        }


        //traemos la  foto con un select
        suspend fun traerFotoUser(valorCorreoUsuario: String): String? {
            return withContext(Dispatchers.IO) {
                var fotoUser: String? = null
                val objConexion = ClaseConexion().cadenaConexion()
                val preparedStatement = objConexion?.prepareStatement("SELECT foto_usuario FROM tbusuariosOne WHERE correo_usuario = ?")
                preparedStatement?.setString(1, valorCorreoUsuario)
                println("..........ESTE ES EL VALOR DE LA FOTO: " + valorCorreoUsuario)

                try {
                    val resultSet = preparedStatement?.executeQuery()
                    if (resultSet?.next() == true) {
                        fotoUser = resultSet.getString("foto_usuario")
                        println("++++ESTE ES LA FOTO DENTRO DE LA FUNCION DE TRAER FOTO: " + fotoUser)
                    } else {
                        println("++++POSIBLE ERROR EN ELSE FOTO USER: " + fotoUser)
                    }
                } catch (e: SQLException) {
                    // Manejar la excepción
                    e.printStackTrace()
                } finally {
                    // Asegúrate de cerrar recursos aquí
                    preparedStatement?.close()
                    objConexion?.close()
                }
                println("++++---------- FOTO USER: " + fotoUser)
                fotoUser
            }
        }

        //Asignar la foto al menú

        CoroutineScope(Dispatchers.Main).launch {
            val fotoUsuario = traerFotoUser(valorCorreoUsuario)
            if (fotoUsuario != null) {
                fotoUserV = fotoUsuario
                println("ESTE ES EL VALOR DE FOTO TRAIDO: $fotoUsuario")
                val imgejemplo = "https://i.pinimg.com/564x/04/0b/48/040b48d97b59a0e93648d330aef497ab.jpg"
                Glide.with(this@MainActivity)
                    .load(fotoUsuario)
                    .placeholder(R.drawable.usericonosocuro) // Imagen de carga
                    .error(R.drawable.usericonosocuro) // Imagen de error
                    .into(imgMenu)
            } else {
                println("Error: No se pudo cargar la foto del usuario.")
            }
        }



//traemos los id de los diferentes roles
        fun traerID_Dueno_Mascota(): String? {
            var uuidRol: String? = null
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT UUID_Rol FROM tbRolesUsuarios WHERE nombre_rol = 'Dueno Mascota'")!!

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_Rol")
            }
            return uuidRol
        }


        fun traerID_Empleado(): String? {
            var uuidRol: String? = null
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT UUID_Rol FROM tbRolesUsuarios WHERE nombre_rol = 'Empleado'")!!

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_Rol")
            }
            return uuidRol
        }

        fun traerID_Dueno_Vet(): String? {
            var uuidRol: String? = null
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT UUID_Rol FROM tbRolesUsuarios WHERE nombre_rol = 'Admin Vet'")!!

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_Rol")
            }
            return uuidRol
        }

        fun traerID_Secretariot(): String? {
            var uuidRol: String? = null
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT UUID_Rol FROM tbRolesUsuarios WHERE nombre_rol = 'Secretario'")!!

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_Rol")
            }
            return uuidRol
        }


        //menu segun los roles
        CoroutineScope(Dispatchers.IO).launch {
            val txtcorreoiniciarval = valorRolUsuario
            val rolDuenoMascotaMainActivity = traerID_Dueno_Mascota()
            val rolEmpleadoMainActivity = traerID_Empleado()
            val rolDuenoVetMainActivity = traerID_Dueno_Vet()
            val rolSecretarioMainActivity = traerID_Secretariot()

            withContext(Dispatchers.Main){
                if (txtcorreoiniciarval == rolDuenoMascotaMainActivity) {
                    //Dueño mascota
                    navView.menu.findItem(R.id.fragment_citas).isVisible = false
                    navView.menu.findItem(R.id.fragment_agendarCita).isVisible = true
                    navView.menu.findItem(R.id.fragment_estadoSolicitud).isVisible = true
                    navView.menu.findItem(R.id.ver_veterinarias_usuario).isVisible = true
                    navView.menu.findItem(R.id.resenasdv).isVisible = true
                    navView.menu.findItem(R.id.agregarmascotaas).isVisible = true
                    navView.menu.findItem(R.id.historialcitasdvvv).isVisible = false
                    navView.menu.findItem(R.id.fragment_asignaciones).isVisible = false
                    navView.menu.findItem(R.id.misResenas).isVisible = false
                    navView.menu.findItem(R.id.misempleadosdv).isVisible = false
                    navView.menu.findItem(R.id.solicitudescitadv).isVisible = false
                    navView.menu.findItem(R.id.agregarempleadodv).isVisible = false
                    navView.menu.findItem(R.id.fragment_veterinarias).isVisible = false
                    navView.menu.findItem(R.id.agregar_vet).isVisible = false
                    navView.menu.findItem(R.id.chatdv).isVisible = false
                    navView.menu.findItem(R.id.fragment_misMascotas).isVisible = true
                    navView.menu.findItem(R.id.fragment_historialresenas).isVisible = true
                    navView.menu.findItem(R.id.fragment_historialAsignacionesEmp).isVisible = false
                    navView.menu.findItem(R.id.fragment_citasAsignadas).isVisible = false
                    navView.menu.findItem(R.id.fragment_citasatendidas).isVisible = false
                }
                if (txtcorreoiniciarval == rolEmpleadoMainActivity) {
                    //Empleado
                    navView.menu.findItem(R.id.fragment_citas).isVisible = false
                    navView.menu.findItem(R.id.fragment_agendarCita).isVisible = false
                    navView.menu.findItem(R.id.fragment_estadoSolicitud).isVisible = false
                    navView.menu.findItem(R.id.ver_veterinarias_usuario).isVisible = false
                    navView.menu.findItem(R.id.resenasdv).isVisible = false
                    navView.menu.findItem(R.id.agregarmascotaas).isVisible = false
                    navView.menu.findItem(R.id.historialcitasdvvv).isVisible = false
                    navView.menu.findItem(R.id.fragment_asignaciones).isVisible = true
                    navView.menu.findItem(R.id.misResenas).isVisible = true
                    navView.menu.findItem(R.id.misempleadosdv).isVisible = false
                    navView.menu.findItem(R.id.solicitudescitadv).isVisible = false
                    navView.menu.findItem(R.id.agregarempleadodv).isVisible = false
                    navView.menu.findItem(R.id.fragment_veterinarias).isVisible = false
                    navView.menu.findItem(R.id.agregar_vet).isVisible = false
                    navView.menu.findItem(R.id.chatdv).isVisible = false
                    navView.menu.findItem(R.id.fragment_misMascotas).isVisible = false
                    navView.menu.findItem(R.id.fragment_historialresenas).isVisible = false
                    navView.menu.findItem(R.id.fragment_historialAsignacionesEmp).isVisible = true
                    navView.menu.findItem(R.id.fragment_citasAsignadas).isVisible = false
                    navView.menu.findItem(R.id.fragment_citasatendidas).isVisible = false
                }
                if (txtcorreoiniciarval == rolDuenoVetMainActivity) {
                    //Dueño veterinaria
                    navView.menu.findItem(R.id.fragment_citas).isVisible = false
                    navView.menu.findItem(R.id.fragment_agendarCita).isVisible = false
                    navView.menu.findItem(R.id.fragment_estadoSolicitud).isVisible = false
                    navView.menu.findItem(R.id.ver_veterinarias_usuario).isVisible = false
                    navView.menu.findItem(R.id.resenasdv).isVisible = false
                    navView.menu.findItem(R.id.agregarmascotaas).isVisible = false
                    navView.menu.findItem(R.id.historialcitasdvvv).isVisible = false
                    navView.menu.findItem(R.id.fragment_asignaciones).isVisible = false
                    navView.menu.findItem(R.id.misResenas).isVisible = true
                    navView.menu.findItem(R.id.misempleadosdv).isVisible = true
                    navView.menu.findItem(R.id.solicitudescitadv).isVisible = true
                    navView.menu.findItem(R.id.agregarempleadodv).isVisible = true
                    navView.menu.findItem(R.id.fragment_veterinarias).isVisible = true
                    navView.menu.findItem(R.id.agregar_vet).isVisible = true
                    navView.menu.findItem(R.id.chatdv).isVisible = false
                    navView.menu.findItem(R.id.fragment_misMascotas).isVisible = false
                    navView.menu.findItem(R.id.fragment_historialresenas).isVisible = false
                    navView.menu.findItem(R.id.fragment_historialAsignacionesEmp).isVisible = false
                    navView.menu.findItem(R.id.fragment_citasAsignadas).isVisible = true
                    navView.menu.findItem(R.id.fragment_citasatendidas).isVisible = true
                }
                if (txtcorreoiniciarval == rolSecretarioMainActivity) {
                    //Secretario
                    navView.menu.findItem(R.id.fragment_citas).isVisible = false
                    navView.menu.findItem(R.id.fragment_agendarCita).isVisible = false
                    navView.menu.findItem(R.id.fragment_estadoSolicitud).isVisible = false
                    navView.menu.findItem(R.id.ver_veterinarias_usuario).isVisible = false
                    navView.menu.findItem(R.id.resenasdv).isVisible = false
                    navView.menu.findItem(R.id.agregarmascotaas).isVisible = false
                    navView.menu.findItem(R.id.historialcitasdvvv).isVisible = false
                    navView.menu.findItem(R.id.fragment_asignaciones).isVisible = false
                    navView.menu.findItem(R.id.misResenas).isVisible = true
                    navView.menu.findItem(R.id.misempleadosdv).isVisible = true
                    navView.menu.findItem(R.id.solicitudescitadv).isVisible = true
                    navView.menu.findItem(R.id.agregarempleadodv).isVisible = false
                    navView.menu.findItem(R.id.fragment_veterinarias).isVisible = false
                    navView.menu.findItem(R.id.agregar_vet).isVisible = false
                    navView.menu.findItem(R.id.chatdv).isVisible = false
                    navView.menu.findItem(R.id.fragment_misMascotas).isVisible = false
                    navView.menu.findItem(R.id.fragment_historialresenas).isVisible = false
                    navView.menu.findItem(R.id.fragment_historialAsignacionesEmp).isVisible = false
                    navView.menu.findItem(R.id.fragment_citasAsignadas).isVisible = true
                    navView.menu.findItem(R.id.fragment_citasatendidas).isVisible = true
                }

                println("*******este es el resultado que traigo con el select ROL USUARIO MASCOTA $rolDuenoMascotaMainActivity")
                println("*************este es el resultado que traigo con el select CORREO INICIAR $txtcorreoiniciarval")

            }



        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (intent.getBooleanExtra("ir_a_agregar_Cita", false)){
            navController.navigate(R.id.fragment_agendarCita)
        }
            }


   private fun changeStatusBarColor(color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = android.graphics.Color.parseColor(color)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

