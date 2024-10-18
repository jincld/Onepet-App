package RecyclerViewHelpers_HistorialAsignaciones

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderHistorialAsignaciones(view: View): RecyclerView.ViewHolder(view) {
    //En el ViewHolder mando a llamar los elementos de la card
    val txtMotivoCitaHAE = view.findViewById<TextView>(R.id.txtMotivoCitaHAE)
    val txtEstadoHAE = view.findViewById<TextView>(R.id.txtEstadoHAE)
    val txtMascotaHAE = view.findViewById<TextView>(R.id.txtMascotaHAE)
    val txtFechaCitaHAE = view.findViewById<TextView>(R.id.txtFechaCitaHAE)
    val txtDescripcionCitaHAE = view.findViewById<TextView>(R.id.txtDescripcionCitaHAE)
    var txtDetalleHA = view.findViewById<TextView>(R.id.txtDetalleHA)
}