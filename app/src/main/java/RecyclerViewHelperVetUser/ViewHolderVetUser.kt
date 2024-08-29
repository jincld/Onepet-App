package RecyclerViewHelperVetUser

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderVetUser(view: View) : RecyclerView.ViewHolder(view) {
    val txtNombreVetUser: TextView = view.findViewById(R.id.txtNombreVetCardUser)
    val txtUbicacionVetUser: TextView = view.findViewById(R.id.txtUbicacionVetCardUser)
    val txtDescripcionVetCardUser: TextView = view.findViewById(R.id.txtServiciosVetCardUser)

}
