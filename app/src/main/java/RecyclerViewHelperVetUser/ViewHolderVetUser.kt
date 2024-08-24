package RecyclerViewHelperVetUser

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderVetUser(view: View) : RecyclerView.ViewHolder(view) {
    val txtNombreVet: TextView = view.findViewById(R.id.txtNombreVetCardUser)
    val txtUbicacionVet: TextView = view.findViewById(R.id.txtUbicacionVetCardUser)
    val txtDescripcionVetCard: TextView = view.findViewById(R.id.txtServiciosVetCardUser)

}
