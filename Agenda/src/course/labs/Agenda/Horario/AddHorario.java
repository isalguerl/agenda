 package course.labs.Agenda.Horario;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import course.labs.Agenda.R;
import course.labs.Agenda.Calificacion.ManagerCalificacion;
import course.labs.Agenda.Calificacion.TransicionCalificacion;
import course.labs.Agenda.Evento.AddEvento;
import course.labs.Agenda.Evento.EventoItem;
import course.labs.Agenda.Evento.OneShotAlarm;
import course.labs.Agenda.Evento.AddEvento.TimePickerFragment;
import course.labs.Agenda.Evento.EventoItem.Category;
import course.labs.Agenda.Materia.DatabaseMateria;
import course.labs.Agenda.R.id;
import course.labs.Agenda.R.layout;
import course.labs.Agenda.R.menu;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddHorario extends Activity {
	private static final int ADD_TODO_ITEM_REQUEST = 0;
	private static int cont=0;
	private static String timeStringInicio, timeStringFinal;
	static Integer years, months, days, hours, minut;

	String Materia;
	TextView materiaElegida, diaSemana;

	private Date mDate;
	private static TextView TimeInicio, TimeFinal;
	private static Integer inifin = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_horario);
		materiaElegida = (TextView) findViewById(R.id.editTextelegida);
		diaSemana = (TextView) findViewById(R.id.editTextDia);
		TimeInicio = (TextView) findViewById(R.id.timeInicio);
		TimeFinal = (TextView) findViewById(R.id.timeFinal);
		setDefaultDateTime();
		materiaElegida.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddHorario.this, ListaMaterias.class);
				startActivityForResult(i, ADD_TODO_ITEM_REQUEST);
			}
		});
		diaSemana.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddHorario.this, ListaDias.class);
				startActivityForResult(i, ADD_TODO_ITEM_REQUEST);
			}
		});

		TimeInicio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inifin = 0;
				showTimePickerDialog();
			}
		});
		TimeFinal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inifin = 1;
				showTimePickerDialog();
			}
		});

		final Button cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO - Implement onClick().
				finish();

			}
		});

		// OnClickListener for the Reset Button

		final Button resetButton = (Button) findViewById(R.id.resetButton);
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO - Reset data fields to default values
				setDefaultDateTime();
				materiaElegida.setText("");
				diaSemana.setHint("Lunes");

			}
		});

		// OnClickListener for the Submit Button
		// Implement onClick().

		final Button submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String nameString = materiaElegida.getText().toString();
				String dayString = diaSemana.getText().toString();
				if (nameString.isEmpty() || dayString.isEmpty())
					Toast.makeText(getApplicationContext(),
							"Elija una materia o dia de la semana", Toast.LENGTH_LONG).show();
				else {
					String Inicio =  timeStringInicio;
					String Final =  timeStringFinal;
					Intent data = new Intent();
					HorarioItem.packageIntent(data, nameString, Inicio, Final,dayString);
					setResult(RESULT_OK, data);
					finish();
				}

			}
		});
	}

	private void setDefaultDateTime() {

		// Default is current time + 7 days
		mDate = new Date();
		mDate = new Date(mDate.getTime());

		Calendar c = Calendar.getInstance();
		c.setTime(mDate);

		setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				c.get(Calendar.MILLISECOND));

		TimeInicio.setText(timeStringInicio);
		TimeFinal.setText(timeStringFinal);

		years = Calendar.YEAR;
		months = Calendar.MONTH;
		days = Calendar.DAY_OF_MONTH;
		hours = Calendar.HOUR_OF_DAY;
		minut = Calendar.MINUTE;
	}

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return
			return new TimePickerDialog(getActivity(), this, hour, minute, true);
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			setTimeString(hourOfDay, minute, 0);
			hours = hourOfDay;
			minut = minute;
			if (inifin == 0)
				TimeInicio.setText(timeStringInicio);
			if (inifin == 1)
				TimeFinal.setText(timeStringFinal);

		}
	}

	private static void setTimeString(int hourOfDay, int minute, int mili) {
		String hour = "" + hourOfDay;
		String min = "" + minute;

		if (hourOfDay < 10)
			hour = "0" + hourOfDay;
		if (minute < 10)
			min = "0" + minute;
		if (inifin == 0)
			timeStringInicio = hour + ":" + min + ":00";
		if (inifin == 1 || cont==0)
			timeStringFinal = hour + ":" + min + ":00";
		cont=1;
		hours = hourOfDay;
		minut = minute;

	}

	private void showTimePickerDialog() {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ADD_TODO_ITEM_REQUEST) {
			if (resultCode == RESULT_OK) {
				Bundle aux = data.getExtras();
				if (aux.getString("Materia") != null)
					materiaElegida.setText(aux.getString("Materia"));
				if (aux.getString("dia") != null)
					diaSemana.setText(aux.getString("dia"));

			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.add_horario, menu);

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
}
