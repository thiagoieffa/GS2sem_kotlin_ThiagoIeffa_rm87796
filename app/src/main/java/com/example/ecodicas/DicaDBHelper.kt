package example.com.ecodicas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DicaDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "ecodicas.db"
        const val DATABASE_VERSION = 1

        const val TABLE_DICAS = "dicas"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_DICAS = ("CREATE TABLE $TABLE_DICAS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_TITULO TEXT, "
                + "$COLUMN_DESCRICAO TEXT)")
        db.execSQL(CREATE_TABLE_DICAS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DICAS")
        onCreate(db)
    }

}
