package RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.cardVeterinaria)
    val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)

}
