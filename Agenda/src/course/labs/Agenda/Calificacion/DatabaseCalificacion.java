package course.labs.Agenda.Calificacion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseCalificacion extends SQLiteOpenHelper {

	final static String TABLE_NAME = "calificacion";
	final static String MATERIA_NAME = "materia";
	final static String CALIFICACION = "calificacion";
	final static String PESO = "peso";
	final static String CALIFICACION_NAME = "calificacionName";
	final static String _ID = "_id";
	public final static String[] columns = { _ID, MATERIA_NAME, CALIFICACION, PESO,
			CALIFICACION_NAME };
	final static String[] columns2 = { CALIFICACION_NAME,
			 CALIFICACION, MATERIA_NAME,  PESO };

	public static String getTableName() {
		return TABLE_NAME;
	}

	public static String getMateriaName() {
		return MATERIA_NAME;
	}

	public static String getId() {
		return _ID;
	}

	public static String[] getColumns() {
		return columns;
	}

	public static String[] getColumns2() {
		return columns2;
	}

	public static String getCreateCmd() {
		return CREATE_CMD;
	}

	public static String getName() {
		return NAME;
	}

	public static Integer getVersion() {
		return VERSION;
	}

	public Context getmContext() {
		return mContext;
	}

	final private static String CREATE_CMD =

	"CREATE TABLE calificacion (" + _ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + MATERIA_NAME
			+ " TEXT NOT NULL," + CALIFICACION + " TEXT NOT NULL," + PESO
			+ " TEXT NOT NULL, " + CALIFICACION_NAME + " TEXT NOT NULL)";

	final private static String NAME = "calificacion_db";
	final private static Integer VERSION = 1;
	final private Context mContext;

	public DatabaseCalificacion(Context context) {
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
