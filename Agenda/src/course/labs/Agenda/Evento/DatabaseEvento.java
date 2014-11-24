package course.labs.Agenda.Evento;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseEvento extends SQLiteOpenHelper {

	final static String TABLE_NAME = "evento";
	final static String EVE_NAME = "name";
	final static String EVE_FECHA = "fecha";
	final static String DESCRIP_NAME = "descripcion";
	final static String EVE_TIPO = "tipo";
	final static String URI = "uri";
	final static String _ID = "_id";
	final static String[] columns = { _ID, EVE_NAME, EVE_FECHA,
		DESCRIP_NAME, EVE_TIPO,URI };
	final static String[] columns2 = { EVE_NAME, EVE_FECHA,
		 EVE_TIPO };

	final private static String CREATE_CMD =

	"CREATE TABLE evento (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ EVE_NAME + " TEXT NOT NULL," + EVE_FECHA + " TEXT NOT NULL,"
			+ DESCRIP_NAME + " TEXT NOT NULL, " + EVE_TIPO + " TEXT NOT NULL, " + URI +" TEXT NOT NULL)";

	final private static String NAME = "evento_db";
	final private static Integer VERSION = 1;
	final private Context mContext;

	public DatabaseEvento(Context context) {
		super(context, NAME, null, VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CMD);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// N/A
	}

	void deleteDatabase() {
		mContext.deleteDatabase(NAME);
	}
}
