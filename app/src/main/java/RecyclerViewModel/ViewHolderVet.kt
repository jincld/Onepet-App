package RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderVet(view: View) : RecyclerView.ViewHolder(view) {
    val txtNombreVet: TextView = view.findViewById(R.id.txtNombreVetCard)
    val txtUbicacionVet: TextView = view.findViewById(R.id.txtUbicacionVetCard)
    val txtDescripcionVetCard: TextView = view.findViewById(R.id.txtServiciosVetCard)
    val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)

}
