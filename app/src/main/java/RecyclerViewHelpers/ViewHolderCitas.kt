package RecyclerViewHelpers

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderCitas(view: View): RecyclerView.ViewHolder(view) {
    //En el ViewHolder mando a llamar los elementos de la card
    val txtMotivoCitaC = view.findViewById<TextView>(R.id.txtMotivoCitaC)
    val txtVeterinariaCC = view.findViewById<TextView>(R.id.txtVeterinariaCC)
    val txtMascotaC = view.findViewById<TextView>(R.id.txtMascotaC)
    val txtFechaCitaC = view.findViewById<TextView>(R.id.txtFechaCitaC)
    val txtMotivoCitaCC2 = view.findViewById<TextView>(R.id.txtMotivoCitaCC2)
    val txtDescripcionC = view.findViewById<TextView>(R.id.txtDescripcionCitaC)
    val btnEliminarCitaC = view.findViewById<Button>(R.id.btnEliminarCitaC)
    val btnEditarC= view.findViewById<Button>(R.id.btnEditarCitaC)
}