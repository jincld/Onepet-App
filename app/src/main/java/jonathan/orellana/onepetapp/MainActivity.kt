package jonathan.orellana.onepetapp

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import jonathan.orellana.onepetapp.databinding.ActivityMainBinding
import android.view.Window
import android.view.WindowManager
import androidx.core.view.GravityCompat
import jonathan.orellana.onepetapp.iniciarsesion.variablesLogin.valorRolUsuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val txtcorreoiniciar = findViewById<EditText>(R.id.txtcorreoiniciar)

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
                R.id.nav_homeDV, R.id.agregarempleadodv, R.id.misempleadosdv, R.id.solicitudescitadv, R.id.historialcitasdv, R.id.clientesdv, R.id.miveterinariadv, R.id.chatdv, R.id.resenasdv, R.id.ajustesdv, R.id.fragment_citas, R.id.fragment_agendarCita, R.id.fragment_estadoSolicitud, R.id.agregarempleadodv, R.id.misempleadosdv, R.id.fragment_asignaciones, R.id.fragment_misMascotas
            ), drawerLayout
        )

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

        CoroutineScope(Dispatchers.IO).launch {
            val txtcorreoiniciarval = valorRolUsuario
            val RolUsuarioMainActivity = traerID()
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
            } else {
                navView.menu.findItem(R.id.fragment_asignaciones).isVisible = true
                navView.menu.findItem(R.id.resenasdv).isVisible = false
                navView.menu.findItem(R.id.fragment_estadoSolicitud).isVisible = false
                navView.menu.findItem(R.id.ajustesdv).isVisible = false
                navView.menu.findItem(R.id.fragment_misMascotas).isVisible = false
            }

            println("*******este es el resultado que traigo con el select ROL USUARIO MAIN $RolUsuarioMainActivity")
            println("*************este es el resultado que traigo con el select CORREO INICIAR $txtcorreoiniciarval")
        }


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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
