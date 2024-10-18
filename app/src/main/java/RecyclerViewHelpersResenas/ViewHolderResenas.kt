package RecyclerViewHelpersResenas

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderResenas(view:View): RecyclerView.ViewHolder(view) {
    var txtCaliCard = view.findViewById<TextView>(R.id.txtCaliCard)
    var txtComentCard = view.findViewById<TextView>(R.id.txtComentCard)
    var btnBorrarResena = view.findViewById<ImageButton>(R.id.btnBorrarResena)
}