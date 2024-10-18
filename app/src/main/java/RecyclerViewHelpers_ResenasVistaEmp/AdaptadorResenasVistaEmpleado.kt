package RecyclerViewHelpers_ResenasVistaEmp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import modelo.dataClassResenasVistaEmp

class AdaptadorResenasVistaEmpleado(var DatosR: List<dataClassResenasVistaEmp>): RecyclerView.Adapter<ViewHolderResenasVistaEmp>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderResenasVistaEmp {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_resenasvistaemp, parent, false)
        return ViewHolderResenasVistaEmp(vista)
    }

    override fun getItemCount() = DatosR.size

    override fun onBindViewHolder(holder: ViewHolderResenasVistaEmp, position: Int) {
        val resena = DatosR[position]
        holder.txtCaliVEM.text = resena.calificacion.toString()
        holder.txtComentVEM.text = resena.comentarios

    }

}