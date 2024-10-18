package RecyclerViewHelpers_ResenasVistaEmp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderResenasVistaEmp(view:View): RecyclerView.ViewHolder(view) {
    var txtCaliVEM = view.findViewById<TextView>(R.id.txtCaliVEM)
    var txtComentVEM= view.findViewById<TextView>(R.id.txtComentVEM)
}