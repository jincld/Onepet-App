package RecyclerViewHelpersResenas

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassResenas

class AdaptadorResenas(var DatosR: List<dataClassResenas>): RecyclerView.Adapter<ViewHolderResenas>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderResenas {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_resenas, parent, false)
        return ViewHolderResenas(vista)
    }

    override fun getItemCount() = DatosR.size

    override fun onBindViewHolder(holder: ViewHolderResenas, position: Int) {
        val resena = DatosR[position]
        holder.txtCaliCard.text = resena.calificacion.toString()
        holder.txtComentCard.text = resena.comentarios

        holder.btnBorrarResena.setOnClickListener() {

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