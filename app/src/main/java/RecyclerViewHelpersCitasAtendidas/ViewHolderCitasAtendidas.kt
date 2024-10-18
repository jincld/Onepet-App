package RecyclerViewHelpersCitasAtendidas

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderCitasAtendidas(view:View): RecyclerView.ViewHolder(view) {

    var txtMotivoCitaHC = view.findViewById<TextView>(R.id.txtMotivoCitaHC)
    var txtEstadoHc = view.findViewById<TextView>(R.id.txtEstadoHc)
    var txtUsuarioHC = view.findViewById<TextView>(R.id.txtUsuarioHC)
    var txtMascotaHC = view.findViewById<TextView>(R.id.txtMascotaHC)
    var txtFechaCitaHC = view.findViewById<TextView>(R.id.txtFechaCitaHC)
    var txtMotivoCitaHC2 = view.findViewById<TextView>(R.id.txtMotivoCitaHC2)
    var txtDescripcionCitaHC = view.findViewById<TextView>(R.id.txtDescripcionCitaHC)
    var txtDetalleCitaHC = view.findViewById<TextView>(R.id.txtDetalleCitaHC)
}