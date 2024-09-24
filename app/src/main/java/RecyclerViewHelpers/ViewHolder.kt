package RecyclerViewHelpers

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolder(view:View): RecyclerView.ViewHolder(view) {

    var txtNombreEmp = view.findViewById<TextView>(R.id.txtNombreEmp)
    var txtContraEmp = view.findViewById<TextView>(R.id.txtContraEmp)
    var txtCorreoEmp = view.findViewById<TextView>(R.id.txtCorreoEmp)
    val btnBorrarCard = view.findViewById<ImageButton>(R.id.btnBorrarCard)
    val btneditarcard = view.findViewById<ImageView>(R.id.btneditarcard)

}