package RecyclerViewHelpers

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
    var txtNombreEmp = view.findViewById<TextView>(R.id.txtNombreEmp)
    var txtCorreoEmp = view.findViewById<TextView>(R.id.txtCorreoEmp)
    var txtContraEmp = view.findViewById<TextView>(R.id.txtContraEmp )
    val btnBorrarCard: ImageButton = view.findViewById(R.id.btnBorrarCard)
}