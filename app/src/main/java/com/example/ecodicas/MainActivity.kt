
package example.com.ecodicas

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewDicas: RecyclerView
    private lateinit var dicaAdapter: DicaAdapter
    private var listaDicas: ArrayList<Dica> = ArrayList()
    private lateinit var searchViewDicas: SearchView
    private lateinit var dbHelper: DicaDBHelper
    private lateinit var buttonEquipe: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewDicas = findViewById(R.id.recyclerViewDicas)
        searchViewDicas = findViewById(R.id.searchViewDicas)

        dbHelper = DicaDBHelper(this)

        carregarDicas()

        inserirDicasNoBanco()

        dicaAdapter = DicaAdapter(listaDicas, this)
        recyclerViewDicas.adapter = dicaAdapter
        recyclerViewDicas.layoutManager = LinearLayoutManager(this)


        searchViewDicas.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filtrarDicas(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarDicas(newText ?: "")
                return false
            }
        })

        buttonEquipe = findViewById(R.id.buttonEquipe)
        buttonEquipe.setOnClickListener {
            val intent = Intent(this@MainActivity, EquipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun carregarDicas() {

        listaDicas.add(Dica("Desligue aparelhos que não estão em uso", "Aparelhos eletrônicos consomem energia mesmo em modo de espera. Desconecte quando não for usar."))
        listaDicas.add(Dica("Use lâmpadas LED", "Lâmpadas LED consomem menos energia e duram mais que as incandescentes."))

    }

    private fun filtrarDicas(texto: String) {
        val listaFiltrada = listaDicas.filter {
            it.titulo.contains(texto, ignoreCase = true) || it.descricao.contains(texto, ignoreCase = true)
        }
        dicaAdapter.filtrarLista(listaFiltrada)
    }

    private fun inserirDicasNoBanco() {
        val db = dbHelper.writableDatabase
        val values = ContentValues()

        values.put(DicaDBHelper.COLUMN_TITULO, "Desligue aparelhos que não estão em uso")
        values.put(DicaDBHelper.COLUMN_DESCRICAO, "Aparelhos eletrônicos consomem energia mesmo em modo de espera. Desconecte quando não for usar.")
        db.insert(DicaDBHelper.TABLE_DICAS, null, values)

        values.put(DicaDBHelper.COLUMN_TITULO, "Use lâmpadas LED")
        values.put(DicaDBHelper.COLUMN_DESCRICAO, "Lâmpadas LED consomem menos energia e duram mais que as incandescentes.")
        db.insert(DicaDBHelper.TABLE_DICAS, null, values)

        db.close()
    }

    private fun carregarDicasDoBanco(): ArrayList<Dica> {
        val dicas = ArrayList<Dica>()
        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.query(DicaDBHelper.TABLE_DICAS, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val titulo = cursor.getString(cursor.getColumnIndexOrThrow(DicaDBHelper.COLUMN_TITULO))
                val descricao = cursor.getString(cursor.getColumnIndexOrThrow(DicaDBHelper.COLUMN_DESCRICAO))
                dicas.add(Dica(titulo, descricao))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return dicas
    }
}
