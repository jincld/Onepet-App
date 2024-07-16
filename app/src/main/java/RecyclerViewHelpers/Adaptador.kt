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

class Adaptador (var Datos: List<tbMascotas>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Unir el RecyclerView con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_cardmascotas, parent, false)
        return ViewHolder(vista)
    }

    /////////////////// TODO: Eliminar datos
    fun eliminarDatos(nombreMascota: String, posicion: Int){
        //Actualizo la lista de datos y notifico al adaptador
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            //1- Creamos un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear una variable que contenga un PrepareStatement
            val deleteMascota = objConexion?.prepareStatement("delete from tbMascotas where nombreMascota = ?")!!
            deleteMascota.setString(1, nombreMascota)
            deleteMascota.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        // Notificar al adaptador sobre los cambios
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    fun actualicePantalla(nuevoNombre: String, nuevaRaza: String, nuevosProcesosP: String, nuevaAlergia: String, nuevaEnfermedadC: String, nuevaFechaNacimiento: String, nuevoPeso: Int, nuevoUUID: String ){
        val index = Datos.indexOfFirst { it.UUID_mascota == nuevoUUID }
        Datos[index].nombre_mascota = nuevoNombre
        notifyDataSetChanged()
    }

    fun obtenerUUIDMascota(): String? {

        val mascotaGlobalEscrito = agregarmascotaas.variablesGlobalesMascota.variableMascotaGlobal
        val objConexion = ClaseConexion().cadenaConexion()

        val traerUUIDMascota = objConexion?.prepareStatement("Select UUID_mascota from tbMascotas where nombre_mascota = ?")!!
        traerUUIDMascota.setString(1, mascotaGlobalEscrito)
        val resultSet = traerUUIDMascota.executeQuery()

        var uuidmascota: String? = null

        if (resultSet?.next() == true) {
            uuidmascota = resultSet.getString("UUID_mascota")
            println("este es el uuid traido desde el if $uuidmascota")
        }

        println("este es el uuid traido desde la funcion $uuidmascota")
        return uuidmascota
    }

    //////////////////////TODO: Actalizar datos
    fun actualizarDato(nuevoNombre: String, nuevaRaza: String, nuevosProcesosP: String, nuevaAlergia: String, nuevaEnfermedadC: String, nuevaFechaNacimiento: String, nuevoPeso: Int, nuevoUUID: String ){
        GlobalScope.launch(Dispatchers.IO){

            //1- Creo un objeto de la clase de conexion
            val objConexion = ClaseConexion().cadenaConexion()

            val uuidMascotaTraido = obtenerUUIDMascota()

            //2- creo una variable que contenga un PrepareStatement
            val updateMascota = objConexion?.prepareStatement("Update tbMascotas set nombre_mascota =  ?, raza = ? , procesos_previos = ?, alergias = ?, enfermedades_cronicas = ?, fecha_nacimiento = ?, peso = ? where nombre_mascota = ?")!!
            updateMascota.setString(1, nuevoNombre)
            updateMascota.setString(2, nuevaRaza)
            updateMascota.setString(3, nuevosProcesosP)
            updateMascota.setString(4, nuevaAlergia)
            updateMascota.setString(5, nuevaEnfermedadC)
            updateMascota.setString(6, nuevaFechaNacimiento)
            updateMascota.setInt(7, nuevoPeso)

            updateMascota.setString(8, uuidMascotaTraido )
            println("este es el uuid traido antes del execute  $uuidMascotaTraido")

            updateMascota.executeUpdate()

            withContext(Dispatchers.Main){
                actualicePantalla(nuevoNombre, nuevaRaza, nuevosProcesosP, nuevaAlergia, nuevaEnfermedadC, nuevaFechaNacimiento, nuevoPeso, nuevoUUID)
            }
        }
    }

    //Devolver la cantidad de datos que se muestran
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Controlar a la card
        val controlCard = Datos[position]
        holder.txtNombreMCard.text = controlCard.nombre_mascota
        //SPINNER DE GENERO
//        holder.txtGeneroMCard.text = controlCard.sexo
//        //SPINNER DE ESPECIE
//        holder.txtEspecieMCard.text = controlCard.especie
        holder.txtRazaMCard.text = controlCard.raza
        holder.txtProcedimientoMCard.text = controlCard.procesos_previos
        holder.txtAñoMCard.text = controlCard.fecha_nacimiento
        holder.txtEnfermedadesMCard.text = controlCard.enfermedades_cronicas
        holder.txtAlergiasMCard.text = controlCard.alergias
        holder.txtPesoMCard.text = controlCard.peso

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
                    //ERROR AQUI
                    pesoMascota.text.toString().toInt(),
                    //FALTA nuevoUUID
                    obtenerUUIDMascota().toString()
                )
                builder.setMessage("Datos Actualizados")
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}