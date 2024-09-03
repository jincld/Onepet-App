package RecyclerViewHelpers

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderSoliCitas (view: View): RecyclerView.ViewHolder(view) {
    //En el ViewHolder mando a llamar los elementos de la card
    val txtMotivoCitaCS = view.findViewById<TextView>(R.id.txtMotivoCitaCS)
    val txtVeterinariaCS = view.findViewById<TextView>(R.id.txtVeterinariaCCS)
    val txtMascotaCS = view.findViewById<TextView>(R.id.txtMascotaCS)
    val txtFechaCitaCS = view.findViewById<TextView>(R.id.txtFechaCitaCS)
    val txtMotivoCitaCC2S = view.findViewById<TextView>(R.id.txtMotivoCitaCC2S)
    val txtDescripcionCS = view.findViewById<TextView>(R.id.txtDescripcionCitaCS)
    val btnAceptarCitaS = view.findViewById<Button>(R.id.btnAceptarCS)
    val btnRechazarCitaS= view.findViewById<Button>(R.id.btnRechazarCitaCS)
}