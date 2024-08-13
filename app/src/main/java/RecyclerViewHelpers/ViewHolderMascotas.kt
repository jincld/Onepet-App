package RecyclerViewHelpers

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import modelo.tbMascotas

class ViewHolderMascotas(view: View): RecyclerView.ViewHolder(view) {

    //En el ViewHolder mando a llamar los elementos de la card
    val txtNombreMascota = view.findViewById<TextView>(R.id.txtNombreMascota)
    val txtRazaMascotaCard = view.findViewById<TextView>(R.id.txtRazaMascotaCard)
    val txtSexoMascota = view.findViewById<TextView>(R.id.txtSexoMascota)
    val txtProcesosPrevios = view.findViewById<TextView>(R.id.txtProcesosPrevios)
    val txtAlergiasMascotas = view.findViewById<TextView>(R.id.txtAlergiasMascotas)
    val txtEnfermedadesCM = view.findViewById<TextView>(R.id.txtEnfermedadesCM)
    val txtFechaNacimiento = view.findViewById<TextView>(R.id.txtFechaNacimiento)
    val txtPesoMascotas = view.findViewById<TextView>(R.id.txtPesoMascotas)
    val txtNombreEspecie = view.findViewById<TextView>(R.id.txtNombreEspecie)
    val txtDueno = view.findViewById<TextView>(R.id.txtDueno)
    val btnEliminarMCard = view.findViewById<Button>(R.id.btnEliminarMCard)
    val btnActualizarMCard = view.findViewById<Button>(R.id.btnActualizarMCard)
}