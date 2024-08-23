package RecyclerViewHelper

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bryan.miranda.crudbryan2a.R

class ViewHolderVetUser(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.txtMascotaCard)
}
