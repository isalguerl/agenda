package course.labs.Agenda.Horario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHorario extends SQLiteOpenHelper {

	public final static String TABLE_NAME = "horario";
	public final static String EVE_NAME = "name";
	public final static String EVE_DAY = "day";
	public static String EVE_FECHAI = "fechai";
	public static String EVE_FECHAF = "fechaf";
	final static String _ID = "_id";
	public final static String[] columns = { _ID, EVE_NAME, EVE_FECHAI,
		EVE_FECHAF,EVE_DAY};
	final static String[] columns2 = { EVE_NAME, EVE_FECHAI,
		EVE_FECHAF };

	final private static String CREATE_CMD =

	"CREATE TABLE horario (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ EVE_NAME + " TEXT NOT NULL," + EVE_FECHAI + " TEXT NOT NULL,"
			+ EVE_FECHAF + " TEXT NOT NULL,"+ EVE_DAY+" TEXT NOT NULL )";

	final private static String NAME = "horario_db";
	final private static Integer VERSION = 1;
	final private Context mContext;

	public DatabaseHorario(Context context) {
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
	public static String getTableName() {
		return TABLE_NAME;
	}
	public static String[] getColumns() {
		return columns;
	}

	public static String[] getColumns2() {
		return columns2;
	}
}
