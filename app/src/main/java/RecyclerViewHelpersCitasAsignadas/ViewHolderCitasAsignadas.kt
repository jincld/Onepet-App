package RecyclerViewHelpersCitasAsignadas

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderCitasAsignadas(view:View): RecyclerView.ViewHolder(view) {

    var txtMotivoCitaCAS = view.findViewById<TextView>(R.id.txtMotivoCitaCAS)
    var txtEstadoCAS = view.findViewById<TextView>(R.id.txtEstadoCAS)
    var txtUsuarioCAS = view.findViewById<TextView>(R.id.txtUsuarioCAS)
    var txtMascotaCAS = view.findViewById<TextView>(R.id.txtMascotaCAS)
    var txtFechaCitaCAS = view.findViewById<TextView>(R.id.txtFechaCitaCAS)
    var txtMotivoCitaCAS2 = view.findViewById<TextView>(R.id.txtMotivoCitaCAS2)
    var txtDescripcionCitaCAS = view.findViewById<TextView>(R.id.txtDescripcionCitaCAS)
    var btnEditarCAS = view.findViewById<ImageButton>(R.id.btnEditarCAS)
    var btnRechazarCAS = view.findViewById<ImageButton>(R.id.btnRechazarCAS)
}
