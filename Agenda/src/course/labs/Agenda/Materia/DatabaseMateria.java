package course.labs.Agenda.Materia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseMateria extends SQLiteOpenHelper {

	final static String TABLE_NAME = "materia";
	final static String MATERIA_NAME = "name";
	final static String ABREV_NAME = "abreviatura";
	final static String TEACHER_NAME = "teacher";
	final static String EMAIL = "email";
	final static String _ID = "_id";
	final static String[] columns = { _ID, MATERIA_NAME, ABREV_NAME,
			TEACHER_NAME, EMAIL };
	final static String[] columns2 = { MATERIA_NAME,
		TEACHER_NAME, EMAIL };
	final static String[] columns3 = { MATERIA_NAME };

	public static String getTableName() {
		return TABLE_NAME;
	}

	public static String getMateriaName() {
		return MATERIA_NAME;
	}

	public static String getAbrevName() {
		return ABREV_NAME;
	}

	public static String getTeacherName() {
		return TEACHER_NAME;
	}

	public static String getEmail() {
		return EMAIL;
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
	
	public static String[] getColumns3() {
		return columns3;
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

	"CREATE TABLE materia (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MATERIA_NAME + " TEXT NOT NULL," + ABREV_NAME + " TEXT NOT NULL,"
			+ TEACHER_NAME + " TEXT NOT NULL, " + EMAIL + " TEXT NOT NULL)";

	final private static String NAME = "materia_db";
	final private static Integer VERSION = 1;
	final private Context mContext;

	public DatabaseMateria(Context context) {
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
