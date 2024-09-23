package RecyclerViewHelper

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.fragment_veterinarias
import jonathan.orellana.onepetapp.ActualizarVetActivity
import modelo.dataClassVeterinaria


class AdaptadorVet(private var Datos: List<dataClassVeterinaria>, private val fragment:fragment_veterinarias) : RecyclerView.Adapter<ViewHolderVet>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVet {
        val vista =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_item_cardmv, parent, false)

        return ViewHolderVet(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderVet, position: Int) {
        val producto = Datos[position]
        holder.txtNombreVet.text = producto.nombre_veterinaria
        holder.txtUbicacionVet.text = producto.ubicacion_veterinaria
        holder.txtDescripcionVetCard.text = producto.descripcion_servicios

//mostrar datos
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantalla = Intent(context, ActualizarVetActivity::class.java)
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
