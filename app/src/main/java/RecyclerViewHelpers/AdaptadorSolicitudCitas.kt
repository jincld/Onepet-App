package RecyclerViewHelpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.asignarcitadv1
import jonathan.orellana.onepetapp.rechazarcitadv1
import modelo.dataClassSoliC
import javax.mail.FetchProfile.Item

class AdaptadorSolicitudCitas (var Datos: List<dataClassSoliC>): RecyclerView.Adapter<ViewHolderSoliCitas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSoliCitas {
        //Unir el RecyclerView con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_solicitudes_c, parent, false)
        return ViewHolderSoliCitas(vista)
    }

    //Devolver la cantidad de datos que se muestran
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderSoliCitas, position: Int) {
        //Controlar a la card
        val controlCard = Datos[position]

        holder.txtMotivoCitaCS.text = controlCard.motivo_cita
        holder.txtFechaCitaCS.text = controlCard.fecha_cita
        holder.txtVeterinariaCS.text = controlCard.vet
        holder.txtMascotaCS.text = controlCard.mascota
        holder.txtMotivoCitaCC2S.text = controlCard.motivo_cita
        holder.txtDescripcionCS.text = controlCard.descripcion_motivo

        //Cambiar de pantalla a la pantalla de detalle
        holder.btnAceptarCitaS.setOnClickListener {
            val context =holder.itemView.context

            val pantallaAsignar = Intent(context, asignarcitadv1::class.java)
            //enviar a la otra pantalla todos mis valores
            pantallaAsignar.putExtra("motivo_cita", controlCard.motivo_cita)
            pantallaAsignar.putExtra("fecha_cita", controlCard.fecha_cita)
            pantallaAsignar.putExtra("usuario", controlCard.usuario)
            pantallaAsignar.putExtra("descripcion_motivo", controlCard.descripcion_motivo)
            context.startActivity(pantallaAsignar)

        }

        holder.btnRechazarCitaS.setOnClickListener {
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