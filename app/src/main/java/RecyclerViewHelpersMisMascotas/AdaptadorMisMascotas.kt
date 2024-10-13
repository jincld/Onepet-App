package RecyclerViewHelpersMisMascotas

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassMisMascotas

class AdaptadorMisMascotas (var Datos: List<dataClassMisMascotas>): RecyclerView.Adapter<ViewHolderMisMascotas>(){

    fun actualicePantalla(UUID_mascota: String, nuevonombre_mascota:String, nuevaraza: String, nuevosprocesos_previos: String, nuevasalergias: String, nuevasenfermedades_cronicas: String, nuevafecha_nacimiento: String, nuevopeso: Int) {
        val index = Datos.indexOfFirst { it.UUID_mascota == UUID_mascota }
        if (index != -1) {
            Datos[index].nombre_mascota = nuevonombre_mascota
            Datos[index].raza = nuevaraza
            Datos[index].procesos_previos = nuevosprocesos_previos
            Datos[index].alergias = nuevasalergias
            Datos[index].enfermedades_cronicas = nuevasenfermedades_cronicas
            Datos[index].fecha_nacimiento = nuevafecha_nacimiento
            Datos[index].peso = nuevopeso
            notifyDataSetChanged()
            // println("Actualizando pantalla para UUID: $uuid, Nombre: $nuevoNombre, Contraseña: $nuevacontra, Correo: $nuevocorreo")
        } else {
            println("No se encontró la mascota con UUID: $UUID_mascota")
        }
    }
    fun actualizarDato( UUID_mascota: String, nuevonombre_mascota:String, nuevaraza: String, nuevosprocesos_previos: String, nuevasalergias: String, nuevasenfermedades_cronicas: String, nuevafecha_nacimiento: String, nuevopeso: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val updateMascota = objConexion?.prepareStatement("update tbMascotas set nombre_mascota = ?, raza = ?, procesos_previos = ?, alergias = ?, enfermedades_cronicas = ?, fecha_nacimiento = ?, peso = ? where UUID_mascota = ?")!!
            updateMascota.setString(1, nuevonombre_mascota)
            updateMascota.setString(2, nuevaraza)
            updateMascota.setString(3, nuevosprocesos_previos)
            updateMascota.setString(4, nuevasalergias)
            updateMascota.setString(5, nuevasenfermedades_cronicas)
            updateMascota.setString(6, nuevafecha_nacimiento)
            updateMascota.setString(7, nuevopeso.toString())
            updateMascota.setString(8, UUID_mascota)
            updateMascota.executeUpdate()

            withContext(Dispatchers.Main) {
                actualicePantalla(UUID_mascota, nuevonombre_mascota, nuevaraza, nuevosprocesos_previos, nuevasalergias, nuevasenfermedades_cronicas, nuevafecha_nacimiento, nuevopeso)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMisMascotas {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_mismascotas, parent, false)
        return ViewHolderMisMascotas(vista)
    }
    override fun getItemCount() = Datos.size
    override fun onBindViewHolder(holder: ViewHolderMisMascotas, position: Int) {
        val mascota = Datos[position]
        holder.txtNombreMascotaMM.text = mascota.nombre_mascota
        holder.txtRazaMM.text = mascota.raza
        holder.txtProcesosMM.text = mascota.procesos_previos
        holder.txtAlergiasMM.text = mascota.alergias
        holder.txtEnfermedadesMM.text = mascota.enfermedades_cronicas
        holder.txtYearMM.text = mascota.fecha_nacimiento
        holder.txtGeneroMM.text = mascota.sexo
        holder.txtEspecieMM.text = mascota.especie
        holder.txtPesoMM.text = mascota.peso.toString()

        holder.btnBorrarMascotaMM.setOnClickListener() {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar mascota")
            builder.setMessage("¿Quiere eliminar a esta mascota?")

            //Botones

            builder.setPositiveButton("Si") { dialog, which ->
                eliminarDatos(mascota.nombre_mascota, position)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        holder.btnEditarMascotaMM.setOnClickListener{
            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            builder.setTitle("Editar mascota")
            builder.setMessage("¿Desea editar a esta mascota?")

            //Agregarle un cuadro de texto para los nuevos datos
            val cuadroTextoNombre = EditText(context).apply { setText(mascota.nombre_mascota) }
            val cuadroTextoRaza = EditText(context).apply { setText(mascota.raza) }
            val cuadroTextoProcesos = EditText(context).apply { setText(mascota.procesos_previos) }
            val cuadroTextoAlergias = EditText(context).apply { setText(mascota.alergias) }
            val cuadroTextoEnfermedades = EditText(context).apply { setText(mascota.enfermedades_cronicas) }
            val cuadroTextoFecha = EditText(context).apply { setText(mascota.fecha_nacimiento) }
            val cuadroTextoPeso = EditText(context).apply { setText(mascota.peso.toString()) }

            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(cuadroTextoNombre)
                addView(cuadroTextoRaza)
                addView(cuadroTextoProcesos)
                addView(cuadroTextoAlergias)
                addView(cuadroTextoEnfermedades)
                addView(cuadroTextoFecha)
                addView(cuadroTextoPeso)
            }

            builder.setView(layout)

            builder.setPositiveButton("Actualizar") { dialog, which ->

                val uuidMascota = mascota.UUID_mascota
                val nombre = cuadroTextoNombre.text.toString()
                val raza = cuadroTextoRaza.text.toString()
                val procesos = cuadroTextoProcesos.text.toString()
                val alergias = cuadroTextoAlergias.text.toString()
                val enfermedades = cuadroTextoEnfermedades.text.toString()
                val fecha = cuadroTextoFecha.text.toString()
                val peso = cuadroTextoPeso.text.toString()
                var hayerrores = false

                if (nombre.isEmpty()) {
                    cuadroTextoNombre.error = "Debe de completar este campo"
                    hayerrores = true
                } else {
                    cuadroTextoNombre.error = null
                }

                if (raza.isEmpty()) {
                    cuadroTextoRaza.error = "Debe de completar este campo"
                    hayerrores = true
                } else {
                    cuadroTextoRaza.error = null
                }

                if (procesos.isEmpty()) {
                    cuadroTextoProcesos.error = "Debe de completar este campo"
                    hayerrores = true
                } else {
                    cuadroTextoProcesos.error = null
                }

                if (alergias.isEmpty()) {
                    cuadroTextoAlergias.error = "Debe de completar este campo"
                    hayerrores = true
                } else {
                    cuadroTextoAlergias.error = null
                }

                if (enfermedades.isEmpty()) {
                    cuadroTextoEnfermedades.error = "Debe de completar este campo"
                    hayerrores = true
                } else {
                    cuadroTextoEnfermedades.error = null
                }

                if (fecha.isEmpty()) {
                    cuadroTextoFecha.error = "Debe de completar este campo"
                    hayerrores = true
                } else {
                    cuadroTextoFecha.error = null
                }

                if (peso.isEmpty()) {
                    cuadroTextoPeso.error = "Debe de completar este campo"
                    hayerrores = true
                } else {
                    cuadroTextoPeso.error = null
                }

                if (!hayerrores) {
                    actualizarDato(uuidMascota, nombre, raza, procesos, alergias, enfermedades, fecha, peso.toInt())
                } else {
                    println("Error al actualizar")
                }
            }

            builder.setNegativeButton("Cancelar", null)
            builder.show()

        }
    }
    fun eliminarDatos(NombreMascota: String, position: Int) {

        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val borrarMascota = objConexion?.prepareStatement("delete from tbMascotas where nombre_mascota = ?")!!
            borrarMascota.setString(1, NombreMascota)
            borrarMascota.executeUpdate()

            val commit = objConexion?.prepareStatement("commit")!!
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }

}