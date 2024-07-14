package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.agregarmascotaas
import modelo.tbMascotas

class Adaptador (var Datos: List<tbMascotas>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Unir el RecyclerView con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_cardmascotas, parent, false)
        return ViewHolder(vista)
    }

    //Devolver la cantidad de datos que se muestran
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Controlar a la card
        val nombreCard = Datos[position]
        holder.txtNombreMCard.text = nombreCard.nombre_mascota
        //FALTA SPINNER DE GENERO

        //val sexoCard = agregarmascotaas.variableGlobalMascotas.sexo
        //holder.txtGeneroMCard.text = sexoCard.sexo

        //FALTA SPINNER DE ESPECIE

        val razaCard = Datos[position]
        holder.txtRazaMCard.text = razaCard.raza
        val pesoCard = Datos[position]
        holder.txtPesoMCard.text = pesoCard.peso.toString()
        val procesosCard = Datos[position]
        holder.txtProcedimientoMCard.text = procesosCard.procesos_previos
        val añoCard = Datos[position]
        holder.txtAñoMCard.text = añoCard.fecha_nacimiento
        val enfermedadesCard = Datos[position]
        holder.txtEnfermedadesMCard.text = enfermedadesCard.enfermedades_cronicas
        val alergiasCard = Datos[position]
        holder.txtAlergiasMCard.text = alergiasCard.alergias

    }
}