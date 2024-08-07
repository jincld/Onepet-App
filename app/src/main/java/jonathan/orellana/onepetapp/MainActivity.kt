package jonathan.orellana.onepetapp

import android.os.Bundle
import android.view.Menu
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

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    /*companion object variablesMain {
        lateinit var valorRolUsuarioMA: String
        var uuidRolMA: String? = null
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val txtcorreoiniciar = findViewById<EditText>(R.id.txtcorreoiniciar)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

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
                R.id.nav_homeDV, R.id.agregarempleadodv, R.id.misempleadosdv, R.id.solicitudescitadv, R.id.historialcitasdv, R.id.clientesdv, R.id.miveterinariadv, R.id.chatdv, R.id.resenasdv, R.id.ajustesdv, R.id.fragment_citas, R.id.fragment_agendarCita, R.id.fragment_estadoSolicitud, R.id.agregarempleadodv, R.id.misempleadosdv, R.id.fragment_asignaciones, R.id.fragment_veterinarias, R.id.fragment_misMascotas
            ), drawerLayout
        )
        val txtcorreoiniciarval = iniciarsesion.valorRolUsuario
        /*val uuidRolMain = iniciarsesion.variablesLogin.uuidRol*/

        if(txtcorreoiniciarval == "9AEC296BDC494D83B611986353B7F4E1"){
            navView.menu.findItem(R.id.fragment_asignaciones).isVisible = true
        }else{
            navView.menu.findItem(R.id.fragment_asignaciones).isVisible = false
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_gray)
       }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}