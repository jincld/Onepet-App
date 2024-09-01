package RecyclerViewHelpers

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderHistoCitas (view: View): RecyclerView.ViewHolder(view) {
    //En el ViewHolder mando a llamar los elementos de la card
    val txtMotivoCitaCH = view.findViewById<TextView>(R.id.txtMotivoCitaCH)
    val txtVeterinariaCH = view.findViewById<TextView>(R.id.txtVeterinariaCCH)
    val txtMascotaCH = view.findViewById<TextView>(R.id.txtMascotaCH)
    val txtFechaCitaCH = view.findViewById<TextView>(R.id.txtFechaCitaCH)
    val txtMotivoCitaCC2H = view.findViewById<TextView>(R.id.txtMotivoCitaCC2H)
    val txtDescripcionCH = view.findViewById<TextView>(R.id.txtDescripcionCitaCH)
}