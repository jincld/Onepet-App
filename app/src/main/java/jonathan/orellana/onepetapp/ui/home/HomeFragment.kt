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

        CoroutineScope(Dispatchers.IO).launch {
            val txtcorreoiniciarval = iniciarsesion.valorRolUsuario
            val rolDuenoMascotaMainActivity = traerID_Dueno_Mascota()
            val rolEmpleadoMainActivity = traerID_Empleado()
            val rolDuenoVetMainActivity = traerID_Dueno_Vet()
            val rolSecretarioMainActivity = traerID_Secretariot()

            withContext(Dispatchers.Main){

                //Dueño mascota
                    if (txtcorreoiniciarval == rolDuenoMascotaMainActivity){
                        val info1 = "Bienvenido"
                        txtInfo1.text = info1
                        val cont1 = "Te damos la bienvenida a OnePet!"
                        txtCont1.text = cont1

                        val info2 = "Citas"
                        txtInfo2.text = info2
                        val cont2 = "Recuerda revisar las citas de tu mascota"
                        txtCont2.text = cont2

                        val info3 = "Mascota"
                        txtInfo3.text = info3
                        val cont3 = "Recuerda dedicarla tiempo de calidad a tu mascota"
                        txtCont3.text = cont3
                    }

                //Empleado
                if (txtcorreoiniciarval == rolEmpleadoMainActivity){
                    val info1 = "Bienvenido"
                    txtInfo1.text = info1
                    val cont1 = "Te damos la bienvenida a OnePet!"
                    txtCont1.text = cont1

                    val info2 = "Asignaciones"
                    txtInfo2.text = info2
                    val cont2 = "Recuerda revisar tus asignaciones"
                    txtCont2.text = cont2

                    val info3 = "Recordatorio"
                    txtInfo3.text = info3
                    val cont3 = "Recuerda dar tu mejor trabajo"
                    txtCont3.text = cont3
                }

                //Dueño de veterinaria
                if (txtcorreoiniciarval == rolDuenoVetMainActivity){
                    val info1 = "Bienvenido"
                    txtInfo1.text = info1
                    val cont1 = "Te damos la bienvenida a la adminsitración de tu veterinaria"
                    txtCont1.text = cont1

                    val info2 = "Empleados"
                    txtInfo2.text = info2
                    val cont2 = "Recuerda revisar el trabajo de tus empleados"
                    txtCont2.text = cont2

                    val info3 = "Reseñas"
                    txtInfo3.text = info3
                    val cont3 = "Recuerda de revisar las reseñas de tu veterinaria"
                    txtCont3.text = cont3
                }

                //Secretario
                if (txtcorreoiniciarval == rolSecretarioMainActivity){
                    val info1 = "Bienvenido"
                    txtInfo1.text = info1
                    val cont1 = "Te damos la bienvenida a la adminsitración de la veterinaria"
                    txtCont1.text = cont1

                    val info2 = "Asignaciones"
                    txtInfo2.text = info2
                    val cont2 = "Recuerda revisar las asignaciones a los empleados"
                    txtCont2.text = cont2

                    val info3 = "Citas"
                    txtInfo3.text = info3
                    val cont3 = "Recuerda revisar las citas recibidas"
                    txtCont3.text = cont3
                }

                }


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