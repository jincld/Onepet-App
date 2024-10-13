package RecyclerViewHelpersMisMascotas

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolderMisMascotas(view: View): RecyclerView.ViewHolder(view) {
    //En el ViewHolder mando a llamar los elementos de la card
    val txtNombreMascotaMM = view.findViewById<TextView>(R.id.txtNombreMascotaMM)
    val txtYearMM = view.findViewById<TextView>(R.id.txtYearMM)
    val txtEspecieMM = view.findViewById<TextView>(R.id.txtEspecieMM)
    val txtRazaMM = view.findViewById<TextView>(R.id.txtRazaMM)
    val txtGeneroMM = view.findViewById<TextView>(R.id.txtGeneroMM)
    val txtPesoMM = view.findViewById<TextView>(R.id.txtPesoMM)
    val txtEnfermedadesMM = view.findViewById<TextView>(R.id.txtEnfermedadesMM)
    val txtAlergiasMM = view.findViewById<TextView>(R.id.txtAlergiasMM)
    val txtProcesosMM = view.findViewById<TextView>(R.id.txtProcesosMM)
    val btnEditarMascotaMM = view.findViewById<ImageButton>(R.id.btnEditarMascotaMM)
    val btnBorrarMascotaMM = view.findViewById<ImageButton>(R.id.btnBorrarMascotaMM)
}