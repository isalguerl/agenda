package course.labs.Agenda.Horario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import course.labs.Agenda.R;
import course.labs.Agenda.Calificacion.DatabaseCalificacion;
import course.labs.Agenda.Evento.AddEvento;
import course.labs.Agenda.Evento.DatabaseEvento;
import course.labs.Agenda.Evento.EventoItem;
import course.labs.Agenda.Evento.ManagerEvento;
import course.labs.Agenda.R.id;
import course.labs.Agenda.R.layout;
import course.labs.Agenda.R.menu;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ManagerHorario extends Activity {
	Button nuevo;
	private static final int MENU_DELETE = Menu.FIRST;

	private static final int ADD_TODO_ITEM_REQUEST = 0;

	private DatabaseHorario mDbHelper;

	private TableLayout country_table;
	private Cursor cLunes, cMartes, cMiercoles, cJueves, cViernes, cSabado,
			cDomingo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_horario);
		nuevo = (Button) findViewById(R.id.button1);
		country_table = (TableLayout) findViewById(R.id.country_table);

		mDbHelper = new DatabaseHorario(this);

		try {
			init();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nuevo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ManagerHorario.this,
						AddHorario.class);
				startActivityForResult(intent, ADD_TODO_ITEM_REQUEST);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");

		return true;
	}
	private void clearAll() {

		mDbHelper.getWritableDatabase().delete(DatabaseHorario.TABLE_NAME, null,
				null);

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		switch (item.getItemId()) {
		case MENU_DELETE:
			clearAll();
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private Cursor readHorarios(String name) {
		return mDbHelper.getReadableDatabase().query(
				DatabaseHorario.TABLE_NAME, DatabaseHorario.columns,
				DatabaseHorario.EVE_DAY + "=?", new String[] { name }, null,
				null, DatabaseHorario.EVE_FECHAI + " ASC",null );
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ADD_TODO_ITEM_REQUEST) {
			if (resultCode == RESULT_OK) {

				HorarioItem HorarioItem = new HorarioItem(data);
				insert(HorarioItem);
			}
		}

	}

	public void insert(HorarioItem horarioItem) {

		ContentValues values = new ContentValues();
		values.put(DatabaseHorario.EVE_NAME, horarioItem.getmName());

		values.put(DatabaseHorario.EVE_FECHAI, horarioItem.getmFechai()
				.toString());

		values.put(DatabaseHorario.EVE_FECHAF, horarioItem.getmFechaf()
				.toString());

		values.put(DatabaseHorario.EVE_DAY, horarioItem.getmDia().toString());

		mDbHelper.getWritableDatabase().insert(DatabaseHorario.TABLE_NAME,
				null, values);
		values.clear();
		// mAdapter.getCursor().requery();
		// mAdapter.notifyDataSetChanged();

	}

	public void init() throws ParseException {

		fillCountryTable();
	}

	private void fillCountryTable() throws ParseException {

		cLunes = readHorarios("Lunes");
		cMartes = readHorarios("Martes");
		cMiercoles = readHorarios("Miercoles");
		cJueves = readHorarios("Jueves");
		cViernes = readHorarios("Viernes");
		cSabado = readHorarios("Sabado");
		cDomingo = readHorarios("Domingo");
		TableRow row;
		TextView t1, t2, t3, t4, t5, t6, t7;
		String[][] calendario = new String[8][7];
		// Converting to dip unit
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				(float) 1, getResources().getDisplayMetrics());

		// para los lunes
		insertarMatriz(cLunes, calendario, 0);
		insertarMatriz(cMartes, calendario, 1);
		insertarMatriz(cMiercoles, calendario, 2);
		insertarMatriz(cJueves, calendario, 3);
		insertarMatriz(cViernes, calendario, 4);
		insertarMatriz(cSabado, calendario, 5);
		insertarMatriz(cDomingo, calendario, 6);
		for (int f = 0; f < 8; f++) {
			for (int c = 0; c < 7; c++) {
				if (calendario[f][c] == null) {
					calendario[f][c] = "";
				}
			}
		}
		CrearIndice();

		for (int fila = 0; fila < 8; fila++) {
			// Log.i(hourFormat.parse(c.getString(inicio)).toString(), "adsa");
			row = new TableRow(this);
			row.setBackgroundColor(Color.parseColor("#0A0A2A"));
			t1 = new TextView(this);
			t2 = new TextView(this);
			t3 = new TextView(this);
			t4 = new TextView(this);
			t5 = new TextView(this);
			t6 = new TextView(this);
			t7 = new TextView(this);

			t1.setText(calendario[fila][0].toString());
			t2.setText(calendario[fila][1].toString());
			t3.setText(calendario[fila][2].toString());
			t4.setText(calendario[fila][3].toString());
			t5.setText(calendario[fila][4].toString());
			t6.setText(calendario[fila][5].toString());
			t7.setText(calendario[fila][6].toString());

			t1.setTypeface(null, 1);
			t2.setTypeface(null, 1);
			t3.setTypeface(null, 1);
			t4.setTypeface(null, 1);
			t5.setTypeface(null, 1);
			t6.setTypeface(null, 1);
			t7.setTypeface(null, 1);

			t1.setTextSize(9);
			t2.setTextSize(9);
			t3.setTextSize(9);
			t4.setTextSize(9);
			t5.setTextSize(9);
			t6.setTextSize(9);
			t7.setTextSize(9);
			t1.setTextColor(Color.WHITE);
			t2.setTextColor(Color.WHITE);
			t3.setTextColor(Color.WHITE);
			t4.setTextColor(Color.WHITE);
			t5.setTextColor(Color.WHITE);
			t6.setTextColor(Color.WHITE);
			t7.setTextColor(Color.WHITE);

			t1.setWidth(85 * dip);
			t2.setWidth(85 * dip);
			t3.setWidth(85 * dip);
			t4.setWidth(85 * dip);
			t5.setWidth(85 * dip);
			t6.setWidth(85 * dip);
			t7.setWidth(85 * dip);

			row.addView(t1);
			row.addView(t2);
			row.addView(t3);
			row.addView(t4);
			row.addView(t5);
			row.addView(t6);
			row.addView(t7);

			country_table.addView(row, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}

	}

	@Override
	protected void onDestroy() {

		mDbHelper.getWritableDatabase().close();
		// mDbHelper.deleteDatabase();
		//
		super.onDestroy();

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		country_table.removeAllViewsInLayout();
		try {
			init();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertarMatriz(Cursor c, String[][] calendario, int col) {
		int i = 0;
		int nameColumn = c.getColumnIndex(DatabaseHorario.EVE_NAME);
		int inicio = c.getColumnIndex(DatabaseHorario.EVE_FECHAI);
		int finaltime = c.getColumnIndex(DatabaseHorario.EVE_FECHAF);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			c.getString(nameColumn);
			c.getString(inicio);
			c.getString(finaltime);
			calendario[i][col] = "______________" + "\n"
					+ c.getString(nameColumn) + "\n" + c.getString(inicio)
					+ "\n" + c.getString(finaltime) + "\n" + "______________";
			i++;
		}
	}

	public void CrearIndice() {
		TableRow row = new TableRow(this);
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				(float) 1, getResources().getDisplayMetrics());
		row.setBackgroundColor(Color.parseColor("#0B2161"));

		TextView t1 = new TextView(this);
		TextView t2 = new TextView(this);
		TextView t3 = new TextView(this);
		TextView t4 = new TextView(this);
		TextView t5 = new TextView(this);
		TextView t6 = new TextView(this);
		TextView t7 = new TextView(this);

		t1.setText("LUNES");
		t2.setText("MARTES");
		t3.setText("MIERCOLES");
		t4.setText("JUEVES");
		t5.setText("VIERNES");
		t6.setText("SABADO");
		t7.setText("DOMINGO");

		t1.setTypeface(null, 1);
		t2.setTypeface(null, 1);
		t3.setTypeface(null, 1);
		t4.setTypeface(null, 1);
		t5.setTypeface(null, 1);
		t6.setTypeface(null, 1);
		t7.setTypeface(null, 1);
		t1.setWidth(150 * dip);
		t2.setWidth(150 * dip);
		t3.setWidth(150 * dip);
		t4.setWidth(150 * dip);
		t5.setWidth(150 * dip);
		t6.setWidth(150 * dip);
		t7.setWidth(150 * dip);
		t1.setTextColor(Color.WHITE);
		t2.setTextColor(Color.WHITE);
		t3.setTextColor(Color.WHITE);
		t4.setTextColor(Color.WHITE);
		t5.setTextColor(Color.WHITE);
		t6.setTextColor(Color.WHITE);
		t7.setTextColor(Color.WHITE);

		t1.setTextSize(15);
		t2.setTextSize(15);
		t3.setTextSize(15);
		t4.setTextSize(15);
		t5.setTextSize(15);
		t6.setTextSize(15);
		t7.setTextSize(15);

		row.addView(t1);
		row.addView(t2);
		row.addView(t3);
		row.addView(t4);
		row.addView(t5);
		row.addView(t6);
		row.addView(t7);

		country_table.addView(row, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}
}
