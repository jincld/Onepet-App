package RecyclerViewHelper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import modelo.dataClassVeterinaria


class AdaptadorVet(private var Datos: List<dataClassVeterinaria>) : RecyclerView.Adapter<ViewHolderVet>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVet {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_cardmv, parent, false)

        return ViewHolderVet(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderVet, position: Int) {
        val producto = Datos[position]
        holder.textView.text = producto.nombre_veterinaria
        holder.textView.text = producto.ubicacion_veterinaria
        holder.textView.text = producto.descripcion_servicios

    }

}