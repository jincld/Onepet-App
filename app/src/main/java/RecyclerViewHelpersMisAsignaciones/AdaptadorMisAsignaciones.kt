package RecyclerViewHelpersMisAsignaciones

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.rechazarcitadv1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassMisAsignaciones

class AdaptadorMisAsignaciones (var Datos: List<dataClassMisAsignaciones>): RecyclerView.Adapter<ViewHolderMisAsignaciones>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMisAsignaciones {
        //Unir el RecyclerView con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_mis_asignaciones, parent, false)
        return ViewHolderMisAsignaciones(vista)
    }

    fun cancelarCitaEmpMA(motivo_cita: String) {

        val listaDatos = Datos.toMutableList()

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val cambiarEstadoEmp = objConexion?.prepareStatement("Update tbCitasEmp set estado ='Rechazada' where motivo_cita")!!
            cambiarEstadoEmp.setString(1, motivo_cita)
            cambiarEstadoEmp.executeUpdate()

            val cambiarEstado = objConexion?.prepareStatement("Update tbCitas set estado ='Rechazada' where motivo_cita")!!
            cambiarEstado.setString(1, motivo_cita)
            cambiarEstado.executeUpdate()

            val commit = objConexion?.prepareStatement("commit")!!
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyDataSetChanged()

    }

    //Devolver la cantidad de datos que se muestran
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMisAsignaciones, position: Int) {
        //Controlar a la card
        val controlCard = Datos[position]

        holder.txtMotivoCitaMA.text = controlCard.motivo_cita
        holder.txtFechaCitaMA.text = controlCard.fecha_cita
        holder.txtMascotaMA.text = controlCard.mascota
        holder.txtDescripcionMA.text = controlCard.descripcion_motivo

        //todo: clic al boton de eliminar
        holder.btnEliminarCitaMA.setOnClickListener {

           /* //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Cancelar")
            builder.setMessage("¿Desea cancelar la cita?")

            //Botones
            builder.setPositiveButton("Si") { dialog, which ->
                cancelarCitaEmpMA(controlCard.motivo_cita)
            }

            builder.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()*/

            val context = holder.itemView.context
            val pantallaRechazar = Intent(context, rechazarcitadv1::class.java)
            pantallaRechazar.putExtra("motivo_cita", controlCard.motivo_cita)
            pantallaRechazar.putExtra("fecha_cita", controlCard.fecha_cita)
            pantallaRechazar.putExtra("usuario", controlCard.usuario)
            pantallaRechazar.putExtra("descripcion_motivo", controlCard.descripcion_motivo)
            context.startActivity(pantallaRechazar)
        }

    }
}