package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import jonathan.orellana.onepetapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion




            }

            notifyDataSetChanged()
        }

        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()

            updateMascota.setString(1, nuevoNombre)
            updateMascota.executeUpdate()

            withContext(Dispatchers.Main){
            }
        }
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")

            //Botones
            builder.setPositiveButton("Si") { dialog, which ->
            }

            builder.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
            //Creamos un Alert Dialog
            val context = holder.itemView.context


            //Agregarle un cuadro de texto para
            //que el usuario escriba el nuevo nombre
            }

            }
            builder.setView(layout)

            //Botones
            builder.setPositiveButton("Actualizar") { dialog, which ->
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}