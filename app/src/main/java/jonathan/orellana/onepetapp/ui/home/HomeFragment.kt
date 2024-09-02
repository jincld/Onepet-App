package jonathan.orellana.onepetapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.databinding.FragmentHomeBinding
import jonathan.orellana.onepetapp.iniciarsesion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val txtInfo1: TextView = root.findViewById(R.id.txtInfo1)
        val txtCont1: TextView = root.findViewById(R.id.txtCont1)
        val txtInfo2: TextView = root.findViewById(R.id.txtInfo2)
        val txtCont2: TextView = root.findViewById(R.id.txtCont2)
        val txtInfo3: TextView = root.findViewById(R.id.txtInfo3)
        val txtCont3: TextView = root.findViewById(R.id.txtCont3)

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
            val txtcorreoiniciarval = iniciarsesion.valorRolUsuario
            val RolUsuarioMainActivity = traerID()
            withContext(Dispatchers.Main){

                    if (txtcorreoiniciarval == RolUsuarioMainActivity){
                        val info1 = "Bienvenido"
                        txtInfo1.text = info1
                        val cont1 = "Te damos la bienvenida a OnePet!"
                        txtCont1.text = cont1

                        val info2 = "Citas"
                        txtInfo2.text = info2
                        val cont2 = "Recuerde revisar las citas de su mascota"
                        txtCont2.text = cont2

                        val info3 = "Mascota"
                        txtInfo3.text = info3
                        val cont3 = "Recuerde dedicarle tiempo de calidad a su mascota"
                        txtCont3.text = cont3
                    }

                }

                println("*******este es el resultado que traigo con el select ROL USUARIO MAIN $RolUsuarioMainActivity")
                println("*************este es el resultado que traigo con el select CORREO INICIAR $txtcorreoiniciarval")

            }





        homeViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}