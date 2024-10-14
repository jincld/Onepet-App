package RecyclerViewHelpers_HistorialResenas

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderHistorialResenas(view:View): RecyclerView.ViewHolder(view) {

    var txtCaliCardHR = view.findViewById<TextView>(R.id.txtCaliCardHR)
    var txtComentCardHR = view.findViewById<TextView>(R.id.txtComentCardHR)
    var txtVeterinariaHR = view.findViewById<TextView>(R.id.txtVeterinariaHR)
    var btnBorrarResenaHR = view.findViewById<ImageButton>(R.id.btnBorrarResenaHR)

}