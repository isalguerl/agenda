package course.labs.Agenda.Calendario;

import java.text.SimpleDateFormat;
import java.util.Date;

import course.labs.Agenda.R;
import course.labs.Agenda.Calendario.CalendarView.OnDispatchDateSelectListener;
import course.labs.Agenda.Calificacion.DatabaseCalificacion;
import course.labs.Agenda.Horario.DatabaseHorario;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class ManagerCalendario extends Activity implements
		OnDispatchDateSelectListener {
	private TextView mTextDate;
	private SimpleDateFormat mFormat;
	private static DatabaseHorario mDbHelper;
	private Cursor c;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_calendario);
		mDbHelper = new DatabaseHorario(this);

		mTextDate = (TextView) findViewById(R.id.display_date);

		mFormat = new SimpleDateFormat("EEEE d MMMM yyyy");

		((CalendarView) findViewById(R.id.calendar))
				.setOnDispatchDateSelectListener(this);
	}

	@SuppressWarnings("deprecation")
	public String dia(Date date) {
		switch (date.getDay()){
		case 1:
			return "Lunes";
		case 2:
			return "Martes";
		case 3:
			return "Miercoles";
		case 4:
			return "Jueves";
		case 5:
			return "Viernes";
		case 6:
			return "Sabado";
		case 0:
			return "Domingo";
			default:
				break;
		}
		return null;

	}
	private Cursor readArtists(String name) {
		return mDbHelper.getReadableDatabase().query(DatabaseHorario.TABLE_NAME, DatabaseHorario.columns,
				DatabaseHorario.EVE_DAY + "=?",
				new String[] { name }, null, null, null, null);

	}
	@Override
	public void onDispatchDateSelect(Date date) {
		dia(date);
		c=readArtists(dia(date));
		mTextDate.setText(insertarMatriz(c));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public String insertarMatriz(Cursor c) {
		String dia="";
		int nameColumn = c.getColumnIndex(DatabaseHorario.EVE_NAME);
		int inicio = c.getColumnIndex(DatabaseHorario.EVE_FECHAI);
		int finaltime = c.getColumnIndex(DatabaseHorario.EVE_FECHAF);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			c.getString(nameColumn);
			c.getString(inicio);
			c.getString(finaltime);
			dia=dia.concat("______________" + "\n"
					+ c.getString(nameColumn) + "\n" + c.getString(inicio)
					+ "\n" + c.getString(finaltime) + "\n" + "______________");
		}
		return dia;
	}
	@Override
	protected void onDestroy() {

		mDbHelper.getWritableDatabase().close();
		// mDbHelper.deleteDatabase();
		//
		super.onDestroy();

	}
}
