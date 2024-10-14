package RecyclerViewHelpersMisAsignaciones

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderMisAsignaciones(view: View): RecyclerView.ViewHolder(view) {
    //En el ViewHolder mando a llamar los elementos de la card
    val txtMotivoCitaMA = view.findViewById<TextView>(R.id.txtMotivoCitaMA)
    val txtMascotaMA = view.findViewById<TextView>(R.id.txtNombreMascotaMA)
    val txtFechaCitaMA = view.findViewById<TextView>(R.id.txtFechaCitaMA)
    val txtDescripcionMA = view.findViewById<TextView>(R.id.txtDescripcionCitaMA)
    val btnEliminarCitaMA = view.findViewById<ImageButton>(R.id.btnEliminarCitaMA)
    val btnTerminarCitaMA = view.findViewById<ImageButton>(R.id.btnTerminarCitaMA)
}