package RecyclerViewHelpers

import android.view.View
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    //En el ViewHolder mando a llamar los elementos de la card
    val txtNombreMCard = view.findViewById<EditText>(R.id.txtNombreMascota)
    val txtEspecieMCard = view.findViewById<Spinner>(R.id.spEspecie)
    val txtGeneroMCard = view.findViewById<Spinner>(R.id.spGenero)
    val txtPesoMCard = view.findViewById<EditText>(R.id.txtPesoMascotas)
    val txtAñoMCard = view.findViewById<EditText>(R.id.txtAñoMascota)
    val txtEnfermedadesMCard = view.findViewById<EditText>(R.id.txtEnfermedadesCM)
    val txtProcedimientoMCard = view.findViewById<EditText>(R.id.txtProcedimientosMAscotas)
    val txtRazaMCard = view.findViewById<EditText>(R.id.txtRazaMascota)
    val txtAlergiasMCard = view.findViewById<EditText>(R.id.txtAlergiasMascotas)


}