package RecyclerViewHelpers_HistorialResenas

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassHistorialResenas

class AdaptadorHistorialResenas(var DatosR: List<dataClassHistorialResenas>): RecyclerView.Adapter<ViewHolderHistorialResenas>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistorialResenas {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_historialresenas, parent, false)
        return ViewHolderHistorialResenas(vista)
    }

    override fun getItemCount() = DatosR.size

    override fun onBindViewHolder(holder: ViewHolderHistorialResenas, position: Int) {
        val resena = DatosR[position]
        holder.txtCaliCardHR.text = resena.calificacion.toString()
        holder.txtComentCardHR.text = resena.comentarios
        holder.txtVeterinariaHR.text = resena.vet

        holder.btnBorrarResenaHR.setOnClickListener() {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Quiere eliminar la reseña?")

            //Botones

            builder.setPositiveButton("Si") { dialog, which ->
                eliminarDatos(resena.comentarios, position)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

    //funcion para eliminar datos

    fun eliminarDatos(comentarios: String, position: Int) {

        val listaDatos = DatosR.toMutableList()
        listaDatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val borrarResena = objConexion?.prepareStatement("delete from tbResenas where comentarios = ?")!!
            borrarResena.setString(1, comentarios)
            borrarResena.executeUpdate()

            val commit = objConexion?.prepareStatement("commit")!!
            commit.executeUpdate()
        }

        DatosR = listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }
}