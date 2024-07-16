package RecyclerViewHelpers

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import modelo.tbMascotas

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    //En el ViewHolder mando a llamar los elementos de la card
    val txtNombreMCard = view.findViewById<TextView>(R.id.txtNombreMascota)

//    val txtEspecieMCard= view.findViewById<TextView>(R.id.txtEspecieMascotaCard)
//    val txtGeneroMCard= view.findViewById<TextView>(R.id.txtGeneroCard)

    val txtPesoMCard = view.findViewById<TextView>(R.id.txtPesoMascotaCard)
    val txtAñoMCard = view.findViewById<TextView>(R.id.txtAñoMascota)
    val txtEnfermedadesMCard = view.findViewById<TextView>(R.id.txtEnfermedadesCM)
    val txtProcedimientoMCard= view.findViewById<TextView>(R.id.txtProcedimientosMAscotas)
    val txtRazaMCard = view.findViewById<TextView>(R.id.txtRazaMascota)
    val txtAlergiasMCard = view.findViewById<TextView>(R.id.txtAlergiasMascotas)

    val btnActualizarMCard = view.findViewById<Button>(R.id.btnActualizarMCard)
    val btnEliminarMCard = view.findViewById<Button>(R.id.btnEliminarMCard)
}