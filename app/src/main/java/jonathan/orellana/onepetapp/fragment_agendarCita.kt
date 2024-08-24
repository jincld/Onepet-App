package jonathan.orellana.onepetapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion

class fragment_agendarCita : Fragment() {

    private lateinit var txtFechaCita: EditText
    private lateinit var spVetCita: Spinner
    private lateinit var spMascotaCita: Spinner
    private lateinit var txtMotivoCita: EditText
    private lateinit var txtDescripcionCita: EditText
    private lateinit var btnEnviarCita: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_agendar_cita, container, false)

        txtFechaCita = root.findViewById(R.id.txtFechaCita)
        spVetCita = root.findViewById(R.id.spVetCita)
        spMascotaCita = root.findViewById(R.id.spMascotaCita)
        txtMotivoCita = root.findViewById(R.id.txtMotivoCita)
        txtDescripcionCita = root.findViewById(R.id.txtDescripcionCitas)
        btnEnviarCita = root.findViewById(R.id.btnEnviarCita)

        loadVet()
        loadMascotas()

        btnEnviarCita.setOnClickListener {
            val fechaCita = txtFechaCita.text.toString()
            val vetSeleccionado = spVetCita.selectedItem.toString()
            val mascotaSeleccionada = spMascotaCita.selectedItem.toString()
            val motivoCita = txtMotivoCita.text.toString()
            val descripcionCita = txtDescripcionCita.text.toString()

            CoroutineScope(Dispatchers.Main).launch {
                val idVetC = getIdVet(vetSeleccionado)
                val idMascotaC = getIdMascota(mascotaSeleccionada)
                //MODIFICADO
                val idUsuarioOne = iniciarsesion.variablesGlobalesLogin.idDeUsuario



                if (idVetC != null && idMascotaC != null && idUsuarioOne != null) {
                    val result =
                        saveEnviarCita(
                            fechaCita,
                            idVetC,
                            idMascotaC,
                            //MODIFICADO
                            idUsuarioOne,
                            motivoCita,
                            descripcionCita

                        )
                    if (result) {
                        Toast.makeText(
                            requireContext(),
                            "Asignación guardada correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        txtFechaCita.setText("")
                        txtMotivoCita.setText("")
                        txtDescripcionCita.setText("")
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al guardar la asignación",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error: No se pudo obtener los IDs",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return root
    }

    private fun loadVet() {
        CoroutineScope(Dispatchers.IO).launch {
            val vetc = fetchVetDB()
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    vetc
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spVetCita.adapter = adapter
            }
        }
    }

    private fun loadMascotas() {
        CoroutineScope(Dispatchers.IO).launch {
            val mascotac = fetchMascotaDB()
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    mascotac
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spMascotaCita.adapter = adapter
            }
        }
    }

    private suspend fun fetchVetDB(): List<String> = withContext(Dispatchers.IO) {
        val veterinaria = mutableListOf<String>()
        val query = "SELECT nombre_veterinaria FROM tbVeterinarias"
        val objConexion = ClaseConexion().cadenaConexion()

        objConexion?.let {
            try {
                val statement = it.createStatement()
                val resultSet = statement.executeQuery(query)

                while (resultSet.next()) {
                    val nombre_veterinaria = resultSet.getString("nombre_veterinaria")
                    veterinaria.add("$nombre_veterinaria")
                }

                resultSet.close()
                statement.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                it.close()
            }
        }
        veterinaria
    }

    private suspend fun fetchMascotaDB(): List<String> = withContext(Dispatchers.IO) {
        val mascotas = mutableListOf<String>()
        val query = "SELECT nombre_mascota FROM tbMascotas"
        val objConexion = ClaseConexion().cadenaConexion()

        objConexion?.let {
            try {
                val statement = it.createStatement()
                val resultSet = statement.executeQuery(query)

                while (resultSet.next()) {
                    val nombre_mascota = resultSet.getString("nombre_mascota")
                    mascotas.add(nombre_mascota)
                }

                resultSet.close()
                statement.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                it.close()
            }
        }
        mascotas
    }

    private suspend fun getIdVet(nombreVet: String): Int? =
        withContext(Dispatchers.IO) {
            val query =
                "SELECT UUID_Veterinaria FROM tbVeterinarias WHERE nombre_veterinaria =  ?"
            val objConexion = ClaseConexion().cadenaConexion()

            objConexion?.let {
                try {
                    val statement = it.prepareStatement(query)
                    statement.setString(1, nombreVet)
                    val resultSet = statement.executeQuery()

                    if (resultSet.next()) {
                        val idVetC = resultSet.getInt("UUID_Veterinaria")
                        resultSet.close()
                        return@withContext idVetC
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    it.close()
                }
            }
            null
        }

    private suspend fun getIdMascota(nombreMascota: String): Int? =
        withContext(Dispatchers.IO) {
            val query = "SELECT UUID_Mascota FROM tbMascotas WHERE nombre_mascota = ?"
            val objConexion = ClaseConexion().cadenaConexion()

            objConexion?.let {
                try {
                    val statement = it.prepareStatement(query)
                    statement.setString(1, nombreMascota)
                    val resultSet = statement.executeQuery()

                    if (resultSet.next()) {
                        val idMascotaC = resultSet.getInt("UUID_Mascota")
                        resultSet.close()
                        return@withContext idMascotaC
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    it.close()
                }
            }
            null
        }


    //MODIFICADO
/*    private suspend fun getIdUsuarioOne(nombre_usuario: String): Int? = withContext(Dispatchers.IO) {
//        val query = "SELECT UUID_Usuario FROM tbUsuarios WHERE nombre_usuario = ?"
//        val objConexion = ClaseConexion().cadenaConexion()
//
//        objConexion?.let {
//            try {
//                val statement = it.prepareStatement(query)
//                statement.setString(1, nombre_usuario)
//                val resultSet = statement.executeQuery()
//
//                if (resultSet.next()) {
                   val idUsuarioOne = resultSet.getInt("UUID_Usuario")
                   resultSet.close()
                    return@withContext idUsuarioOne
                }
            } catch (e: Exception) {
                e.printStackTrace()
           } finally {
               it.close()
           }
        }
      null
    }*/

    private suspend fun saveEnviarCita(
        fecha_cita: String,
        idVetC: Int,
        idMascotaC: Int,
        //MODIFICADO
        idUsuarioOne: String,
        motivo_cita: String,
        descripcion_motivo: String
    ): Boolean = withContext(Dispatchers.IO) {
        //MODIFICADO
        val idUsuarioOne = iniciarsesion.variablesGlobalesLogin.idDeUsuario
        if (idUsuarioOne != null) {
            val query =
                "INSERT INTO tbCitas (fecha_cita, vet, mascota, usuario, motivo_cita, descripcion_motivo) VALUES (?, ?, ?, ?, ?, ?)"
            val objConexion = ClaseConexion().cadenaConexion()

            objConexion?.let {
                try {
                    val statement = it.prepareStatement(query)
                    statement.setString(1, fecha_cita)
                    statement.setInt(2, idVetC)
                    statement.setInt(3, idMascotaC)
                    statement.setString(4, idUsuarioOne)
                    statement.setString(5, motivo_cita)
                    statement.setString(6, descripcion_motivo)
                    statement.executeUpdate()
                    statement.close()
                    it.close()
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                } finally {
                    it.close()
                }
            } ?: false
        } else {
            false
        }
    }

}