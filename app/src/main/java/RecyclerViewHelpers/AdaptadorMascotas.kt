package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.agregarmascotaas
import jonathan.orellana.onepetapp.iniciarsesion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tbMascotas

class AdaptadorMascotas (var Datos: List<tbMascotas>): RecyclerView.Adapter<ViewHolderMascotas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMascotas {
        //Unir el RecyclerView con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_cardmascotas, parent, false)
        return ViewHolderMascotas(vista)
    }

    /////////////////// TODO: Eliminar datos
    fun eliminarDatos(nombreMascota: String, posicion: Int){
        GlobalScope.launch(Dispatchers.IO){
            //1- Creamos un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear una variable que contenga un PrepareStatement
            val deleteMascota = objConexion?.prepareStatement("delete from tbMascotas where nombre_mascota = ?")!!
            deleteMascota.setString(1, nombreMascota)
            deleteMascota.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                val listaDatos = Datos.toMutableList()
                listaDatos.removeAt(posicion)
                notifyItemRemoved(posicion)
                notifyDataSetChanged()
            }
        }
    }

    fun actualicePantalla(UUID_mascota: String, nuevoNombre: String, nuevaRaza: String, nuevosProcesosP: String, nuevaAlergia: String, nuevaEnfermedadC: String, nuevaFechaNacimiento: String, nuevoPeso: Double){
        val index = Datos.indexOfFirst { it.UUID_mascota == UUID_mascota }
        if(index != -1) {
            Datos[index].nombre_mascota = nuevoNombre
            Datos[index].raza = nuevaRaza
            Datos[index].procesos_previos = nuevosProcesosP
            Datos[index].alergias = nuevaAlergia
            Datos[index].enfermedades_cronicas = nuevaEnfermedadC
            Datos[index].fecha_nacimiento = nuevaFechaNacimiento
            Datos[index].peso = nuevoPeso.toString()
            notifyDataSetChanged()
        }
    }

    //////////////////////TODO: Actualizar datos
    fun actualizarDato(nuevoNombre: String, nuevaRaza: String, nuevosProcesosP: String, nuevaAlergia: String, nuevaEnfermedadC: String, nuevaFechaNacimiento: String, nuevoPeso: Double, UUID_mascota: String) {
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()

            val updateMascota = objConexion?.prepareStatement("Update tbMascotas set nombre_mascota =  ?, raza = ? , procesos_previos = ?, alergias = ?, enfermedades_cronicas = ?, fecha_nacimiento = ?, peso = ? where UUID_mascota = ?")!!
            updateMascota.setString(1, nuevoNombre)
            updateMascota.setString(2, nuevaRaza)
            updateMascota.setString(3, nuevosProcesosP)
            updateMascota.setString(4, nuevaAlergia)
            updateMascota.setString(5, nuevaEnfermedadC)
            updateMascota.setString(6, nuevaFechaNacimiento)
            updateMascota.setDouble(7, nuevoPeso)
            updateMascota.setString(8, UUID_mascota)
            updateMascota.executeUpdate()

            val commit = objConexion.prepareStatement("COMMIT")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualicePantalla(UUID_mascota, nuevoNombre, nuevaRaza, nuevosProcesosP, nuevaAlergia, nuevaEnfermedadC, nuevaFechaNacimiento, nuevoPeso)
            }
        }
    }

    //Devolver la cantidad de datos que se muestran
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMascotas, position: Int) {
        //Controlar a la card
        val controlCard = Datos[position]

        holder.txtNombreMascota.text = controlCard.nombre_mascota
        holder.txtRazaMascotaCard.text = controlCard.raza
        holder.txtSexoMascota.text = controlCard.sexo
        holder.txtProcesosPrevios.text = controlCard.procesos_previos
        holder.txtAlergiasMascotas.text = controlCard.alergias
        holder.txtEnfermedadesCM.text = controlCard.enfermedades_cronicas
        holder.txtFechaNacimiento.text = controlCard.fecha_nacimiento
        holder.txtPesoMascotas.text = controlCard.peso.toString()
        holder.txtNombreEspecie.text = controlCard.nombre_especie
        holder.txtDueno.text = controlCard.nombre_usuario

        //todo: clic al boton de eliminar
        holder.btnEliminarMCard.setOnClickListener {

            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Desea eliminar la mascota?")

            //Botones
            builder.setPositiveButton("Si") { dialog, which ->
                eliminarDatos(controlCard.nombre_mascota, position)
            }

            builder.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        //Todo: boton de Actualizar
        holder.btnActualizarMCard.setOnClickListener{
            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar")
            builder.setMessage("¿Desea actualizar la mascota?")

            //Agregarle un cuadro de texto para
            //que el usuario escriba el nuevo nombre
            val nombreMascota = EditText(context).apply {
                setText(controlCard.nombre_mascota)
            }
            val razaMascota = EditText(context).apply {
                setText(controlCard.raza)
            }
            val procesosPMascota = EditText(context).apply {
                setText(controlCard.procesos_previos)
            }
            val alergiasMascota = EditText(context).apply {
                setText(controlCard.alergias)
            }
            val enfermedadesCMascota = EditText(context).apply {
                setText(controlCard.enfermedades_cronicas)
            }
            val fechanacimientoMascota = EditText(context).apply {
                setText(controlCard.fecha_nacimiento)
            }
            val pesoMascota = EditText(context).apply {
                setText(controlCard.peso)
            }

            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(nombreMascota)
                addView(razaMascota)
                addView(procesosPMascota)
                addView(alergiasMascota)
                addView(enfermedadesCMascota)
                addView(fechanacimientoMascota)
                addView(pesoMascota)
            }
            builder.setView(layout)

            //Botones
            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarDato(
                    nombreMascota.text.toString(),
                    razaMascota.text.toString(),
                    procesosPMascota.text.toString(),
                    alergiasMascota.text.toString(),
                    enfermedadesCMascota.text.toString(),
                    fechanacimientoMascota.text.toString(),
                    pesoMascota.text.toString().toDouble(),
                    controlCard.UUID_mascota
                )
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}