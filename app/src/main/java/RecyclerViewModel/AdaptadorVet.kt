package RecyclerViewHelper

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.agregar_vet
import jonathan.orellana.onepetapp.fragment_veterinarias
import jonathan.orellana.onepetapp.item_card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassVeterinaria


class AdaptadorVet(private var Datos: List<dataClassVeterinaria>) : RecyclerView.Adapter<ViewHolderVet>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVet {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_cardmv, parent, false)

        return ViewHolderVet(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderVet, position: Int) {
        val producto = Datos[position]
        holder.txtNombreVet.text = producto.nombre_veterinaria
        holder.txtUbicacionVet.text = producto.ubicacion_veterinaria
        holder.txtDescripcionVetCard.text = producto.descripcion_servicios


        holder.itemView.setOnClickListener {
            val navController = Navigation.findNavController(holder.itemView)
            val action = R.id.action_agregar_vet_to_veterinarias
            navController.navigate(action)
        }

        holder.imgBorrar.setOnClickListener {
            fun eliminarVet() {
                GlobalScope.launch(Dispatchers.IO) {
                    // creamos un objeto de la clase conexion

                    val objConexion = ClaseConexion().cadenaConexion()
                    println("estamos dentro de una corrutina")

                    val nombrevet = agregar_vet.VariablesGlobalesVeterinaria.NombreVet
                    println("este es el nombre de la vet que quiero eliminar ${nombrevet}")

                    // 2- Crear una variable que contenga un preparestatement (donde se mete el código de sqlserver
                    val deleteVeterinaria = objConexion?.prepareStatement("delete from tbVeterinarias where nombre_veterinaria = ?")!!
                    deleteVeterinaria.setString(1,nombrevet)
                    deleteVeterinaria.executeUpdate()


                    val commit = objConexion.prepareStatement("commit")!!
                    commit.executeUpdate()
                }


            }
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
                builder.setTitle("Eliminar")
                builder.setMessage("Estas seguro que quieres eliminar tu veterinaria?")

                builder.setPositiveButton("Si") { dialog, which ->
                    eliminarVet()
                    Toast.makeText(context, "Datos eliminados", Toast.LENGTH_SHORT).show()


                }
                builder.setNegativeButton("no") { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
            }
        }

    }

