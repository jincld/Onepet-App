package jonathan.orellana.onepetapp

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
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
                R.id.nav_homeDV, R.id.agregarempleadodv, R.id.misempleadosdv, R.id.solicitudescitadv, R.id.historialcitasdv, R.id.clientesdv, R.id.fragment_veterinarias, R.id.chatdv, R.id.resenasdv, R.id.ajustesdv, R.id.fragment_citas, R.id.fragment_agendarCita, R.id.fragment_estadoSolicitud, R.id.agregarempleadodv, R.id.misempleadosdv, R.id.fragment_asignaciones, R.id.fragment_misMascotas, R.id.agregar_vet, R.id.actualizar_y_eliminar_vet2, R.id.agregarempleadodv
            ), drawerLayout
        )

        val navigationView: NavigationView = findViewById(R.id.nav_view)

        val headerView = navigationView.getHeaderView(0)
        val btnCerrarMenu: ImageButton = headerView.findViewById(R.id.btnCerrarDrawer)

        btnCerrarMenu.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val navigationView2: NavigationView = findViewById(R.id.nav_view)
        val headerView2 = navigationView2.getHeaderView(0)
        val txtNombreMenu: TextView = headerView2.findViewById(R.id.txtNombreUserMenu)
        val txtInfo1: TextView = findViewById(R.id.txtInfo1)
        val txtCont1: TextView = findViewById(R.id.txtCont1)
        val txtInfo2: TextView = findViewById(R.id.txtInfo2)
        val txtCont2: TextView = findViewById(R.id.txtCont2)
        val txtInfo3: TextView = findViewById(R.id.txtInfo3)
        val txtCont3: TextView = findViewById(R.id.txtCont3)

        suspend fun traerNombreUser(valorCorreoUsuario: String): String? {
            return withContext(Dispatchers.IO) {
                var nombreUser: String? = null
                val objConexion = ClaseConexion().cadenaConexion()
                val preparedStatement = objConexion?.prepareStatement("SELECT nombre_usuario FROM tbusuariosOne WHERE correo_usuario = ?")
                preparedStatement?.setString(1, valorCorreoUsuario)
                println("..........ESTE ES EL VALOR CORREO: " + valorCorreoUsuario)

                try {
                    val resultSet = preparedStatement?.executeQuery()
                    if (resultSet?.next() == true) {
                        nombreUser = resultSet.getString("nombre_usuario")
                        println("++++ESTE ES EL NOMBRE DENTRO DE LA FUNCION DE TRAER NOMBRE: " + nombreUser)
                    }
                    else    {
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



        CoroutineScope(Dispatchers.Main).launch {
            val valorRolUsuario = valorCorreoUsuario
            val nombreUsuario = traerNombreUser(valorCorreoUsuario)
            txtNombreMenu.text = nombreUsuario
            println("ESTE ES EL NOMBRE TRAIDO:" + nombreUsuario)
        }


      // txtNombreMenu.text = traerNombreUser()

        fun traerID(): String? {
            var uuidRol: String? = null
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT UUID_Rol FROM tbRolesUsuarios WHERE nombre_rol = 'Dueno Mascota'")!!

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_Rol")
            }
            return uuidRol
        }

       CoroutineScope(Dispatchers.Main).launch {
           val info1 = "Administrando"
           txtInfo1.text = info1
           val contenido1 = "Bienvenido a la administración en OnePet!"
           txtCont1.text = contenido1

           val info2 = "Veterinaria"
           txtInfo2.text = info2
           val contenido2 = "Recuerde  revisar las citas de su veterinaria"
           txtCont2.text = contenido2

           val info3 = "Empleados"
           txtInfo3.text = info3
           val contenido3 = "Recuerde revisar la actividad de sus empleados"
           txtCont3.text = contenido3

        }


        CoroutineScope(Dispatchers.IO).launch {
            val txtcorreoiniciarval = valorRolUsuario
            val RolUsuarioMainActivity = traerID()
            withContext(Dispatchers.Main){
                if (txtcorreoiniciarval == RolUsuarioMainActivity) {
                    navView.menu.findItem(R.id.nav_homeDV).isVisible = true
                    navView.menu.findItem(R.id.fragment_citas).isVisible = true
                    navView.menu.findItem(R.id.fragment_agendarCita).isVisible = true
                    navView.menu.findItem(R.id.fragment_estadoSolicitud).isVisible = true
                    navView.menu.findItem(R.id.chatdv).isVisible = true
                    navView.menu.findItem(R.id.resenasdv).isVisible = true
                    navView.menu.findItem(R.id.fragment_misMascotas).isVisible = true
                    navView.menu.findItem(R.id.ajustesdv).isVisible = true
                    navView.menu.findItem(R.id.fragment_asignaciones).isVisible = false
                    navView.menu.findItem(R.id.agregar_vet).isVisible = false
                    navView.menu.findItem(R.id.agregarempleadodv).isVisible = false
                    navView.menu.findItem(R.id.misempleadosdv).isVisible = false
                    navView.menu.findItem(R.id.solicitudescitadv).isVisible = false

                    if (txtcorreoiniciarval == RolUsuarioMainActivity){
                        val info1 = "Bienvenido"
                        txtInfo1.text = info1
                        val cont1 = "Te damos la bienvenida a OnePet!"
                        txtCont1.text = cont1

                        val info2 = "Citas"
                        txtInfo2.text = info2
                        val cont2 = "Recuerde revisar las citas de su mascota"
                        txtCont1.text = cont2

                        val info3 = "Mascota"
                        txtInfo3.text = info3
                        val cont3 = "Recuerde dedicarle tiempo de calidad a su mascota"
                        txtCont3.text = cont3
                    }

                } else {
                    navView.menu.findItem(R.id.fragment_asignaciones).isVisible = true
                    navView.menu.findItem(R.id.fragment_agendarCita).isVisible = false
                    navView.menu.findItem(R.id.fragment_estadoSolicitud).isVisible = false
                    navView.menu.findItem(R.id.fragment_misMascotas).isVisible = false
                    navView.menu.findItem(R.id.agregar_vet).isVisible = true
                    navView.menu.findItem(R.id.agregarempleadodv).isVisible = true
                    navView.menu.findItem(R.id.resenasdv).isVisible = true
                    navView.menu.findItem(R.id.ajustesdv).isVisible = true
                }

                println("*******este es el resultado que traigo con el select ROL USUARIO MAIN $RolUsuarioMainActivity")
                println("*************este es el resultado que traigo con el select CORREO INICIAR $txtcorreoiniciarval")

            }



        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (intent.getBooleanExtra("ir_a_agregar_Cita", false)){
            navController.navigate(R.id.asignarcitadv)
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
