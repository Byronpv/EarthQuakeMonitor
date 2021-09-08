package com.example.earthquakemonitor.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquakemonitor.model.data.Earthquake
import com.example.earthquakemonitor.R
import com.example.earthquakemonitor.databinding.EqListItemBinding
import android.content.Context as Context
import kotlin.Int as Int

class EqAdapter(  val context: Context) :
    RecyclerView.Adapter<EqAdapter.EqViewHolder>() {

    //El Adapter se va a encargar de crear las vistas nuevas cuando las necesite y por otro lado,
    //para asignar a una vista el valor que le corresponda

    private var dataList: MutableList<Earthquake> = mutableListOf<Earthquake>()
    //seteamos la lista

    fun setListData(data: MutableList<Earthquake>) {
        Log.d("EqAdapter", "Mostrando las vistas")
        dataList = data
    }

    private val TAG = EqAdapter::class.java.simpleName
    lateinit var onItemClickListener: (Earthquake) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqViewHolder {
        // el método OncreateVH se va a llamar cuando va a crear una vista nueva y no se pueda reciclar
        val binding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))
        //el parent es el recyclerView donde va a estar funcionando el adapter
        Log.d("EqAdapter", "retornado la vista en OnCreate")
        //Se infla la vista y la devolvemos dentro de un ViewHolder (EqViewHolder)
        return EqViewHolder(binding)

    }

    //Se llama cuando queremos actualizar los datos de una vista, independientemente de si la vista ya ha
    //sido creada o se está reciclando
    override fun onBindViewHolder(holder: EqViewHolder, position: Int) {
        Log.d("EqAdapter", "Entrando al onBindViewHolder posición")
        val earthquake: Earthquake = dataList[position]
        holder.bind(earthquake)

    }

    // Número de elementos que tiene la vista
    override fun getItemCount(): Int = dataList.size


    /*
       // El View va a ser el layout eq_list_item, le pasamos ese View para que guarde los views y los
       // pueda reciclar, en consecuencia heredamos de RecyclerView.ViewHolder y le pasamos ese view al padre
      inner class EqViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

           // Se recupera las vistas hijas de esta vista padre, para luego poder modificarlas
           private val eqMagnitudeText: TextView = itemView.findViewById(R.id.eqMagnitudeText)
           private val eqPlaceText: TextView = itemView.findViewById(R.id.eqPlaceText)

           fun bind(earthquake: Earthquake) {
               eqMagnitudeText.text = earthquake.magnitude.toString()
               eqPlaceText.text = earthquake.place
           }


           //val eqImageArrow: ImageView = view.findViewById<ImageView>(R.id.eqArrowImage)
       }
   */

    inner class EqViewHolder(private val binding: EqListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(earthquake: Earthquake) {

            binding.eqMagnitudeText.text = context.getString(R.string.magnitude_format,earthquake.magnitude)
            binding.eqPlaceText.text = earthquake.place

            binding.root.setOnClickListener {
                if (::onItemClickListener.isInitialized) {
                    onItemClickListener(earthquake)
                } else {
                    Log.e(TAG, "onItemClickListener not initialized")
                }
            }

            binding.executePendingBindings()
        }


    }

}