package RecyclerViewHelpers

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
    var txtNombreEmp = view.findViewById<TextView>(R.id.txtNombre_empleado)
    var txtCorreoEmp = view.findViewById<TextView>(R.id.txtCorreo_empleado)
    var txtContraEmp = view.findViewById<TextView>(R.id.txtContra_empleado)
    val btnBorrarCard: ImageButton = view.findViewById(R.id.btn)
}