package example.com.ecodicas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class DicaAdapter(
    private var listaDicas: List<Dica>,
    private val context: Context
) : RecyclerView.Adapter<DicaAdapter.DicaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_dica, parent, false)
        return DicaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = listaDicas[position]
        holder.textViewTitulo.text = dica.titulo
        holder.textViewDescricao.text = dica.descricao

        holder.itemView.setOnClickListener {
            Toast.makeText(context, dica.titulo, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listaDicas.size
    }

    fun filtrarLista(listaFiltrada: List<Dica>) {
        listaDicas = listaFiltrada
        notifyDataSetChanged()
    }

    class DicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        val textViewDescricao: TextView = itemView.findViewById(R.id.textViewDescricao)
    }
}
