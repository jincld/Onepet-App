package RecyclerViewHelpers

import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    //En el ViewHolder mando a llamar los elementos de la card
    val txtNombreMCard:TextView = view.findViewById<EditText>(R.id.txtNombreMascota)

    val txtEspecieMCard:TextView= view.findViewById<EditText>(R.id.spEspecie)
    val txtGeneroMCard:TextView = view.findViewById<EditText>(R.id.spGenero)

    val txtPesoMCard:TextView = view.findViewById<EditText>(R.id.txtPesoMascotas)
    val txtAñoMCard:TextView = view.findViewById<EditText>(R.id.txtAñoMascota)
    val txtEnfermedadesMCard:TextView = view.findViewById<EditText>(R.id.txtEnfermedadesCM)
    val txtProcedimientoMCard:TextView = view.findViewById<EditText>(R.id.txtProcedimientosMAscotas)
    val txtRazaMCard:TextView = view.findViewById<EditText>(R.id.txtRazaMascota)
    val txtAlergiasMCard:TextView = view.findViewById<EditText>(R.id.txtAlergiasMascotas)
}