package RecyclerViewHelper

import RecyclerViewHelperVetUser.ViewHolderVetUser
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.Ver_veterinarias_usuario
import jonathan.orellana.onepetapp.vet_para_asignar_cita
import modelo.dataClassVeterinaria


class AdaptadorVetUser(private var Datos: List<dataClassVeterinaria>, private val fragment: Ver_veterinarias_usuario) : RecyclerView.Adapter<ViewHolderVetUser>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVetUser {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_card_ver_vet_user, parent, false)

        return ViewHolderVetUser(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderVetUser, position: Int) {
        val producto = Datos[position]
        holder.txtNombreVet.text = producto.nombre_veterinaria
        holder.txtUbicacionVet.text = producto.ubicacion_veterinaria
        holder.txtDescripcionVetCard.text = producto.descripcion_servicios


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantalla = Intent(context, vet_para_asignar_cita::class.java)
            pantalla.putExtra(
                "Nombre",
                producto.nombre_veterinaria
            )
            pantalla.putExtra(
                "Ubicacion",
                producto.ubicacion_veterinaria
            )
            pantalla.putExtra(
                "NIT",
                producto.nit
            )
            pantalla.putExtra(
                "Contacto",
                producto.contacto_veterinaria
            )
            pantalla.putExtra(
                "Correo",
                producto.correo_veterinaria
            )
            pantalla.putExtra(
                "Descripcion",
                producto.descripcion_servicios
            )

            context.startActivity(pantalla)

        }


    }
}
