package course.labs.Agenda.Evento;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import course.labs.Agenda.R;
import course.labs.Agenda.Evento.EventoItem.Category;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//@TargetApi(Build.VERSION_CODES.KITKAT)
public class DetailEvento extends Activity {
	private final String ruta_fotos = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
			+ "/misfotos/";
	private File file = new File(ruta_fotos);
	private static final int SEVEN_DAYS = 604800000;
	private EditText mName;
	private EditText mDescription;
	private static TextView DateView, TimeView;
	static Integer years, months, days, hours, minut;
	private Date mDate;
	private RadioGroup mCategoryRadioGroup;
	private RadioButton mDefaultStatusButton;
	private CheckBox mRecordatorio;
	private static String timeString, dateString;
	private Uri mUri;
	private DatabaseEvento mDbHelper;
	private ImageView imagen;
	Toast mToast;
	Bitmap bmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDbHelper = new DatabaseEvento(this);

		setContentView(R.layout.add_evento);
		mName = (EditText) findViewById(R.id.title);

		mDescription = (EditText) findViewById(R.id.editTextDescrip);
		DateView = (TextView) findViewById(R.id.date);
		TimeView = (TextView) findViewById(R.id.time);
		mCategoryRadioGroup = (RadioGroup) findViewById(R.id.radioGroupCategory);
		mRecordatorio = (CheckBox) findViewById(R.id.CheckBox1);
		imagen = (ImageView) findViewById(R.id.imageView1);

		setDefaultDateTime();
		Bundle objeto = getIntent().getExtras();
		Cursor c = mDbHelper.getReadableDatabase().query(
				DatabaseEvento.TABLE_NAME, DatabaseEvento.columns,
				DatabaseEvento._ID + "=?",
				new String[] { objeto.getString("identificador") }, null, null,
				null, null);

		c.moveToFirst();
		final String id = c.getString(0);
		String name = c.getString(1);
		String fechayhora = c.getString(2);
		String descripcion = c.getString(3);
		String tipo = c.getString(4);
		if (c.getString(5)!="vacio") {
			String uri = c.getString(5);
			mUri = Uri.parse(uri);
			InputStream is;
			try {
				is = getContentResolver().openInputStream(mUri);
				BufferedInputStream bis = new BufferedInputStream(is);
				Bitmap bitmap = BitmapFactory.decodeStream(bis);
				imagen.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		mName.setText(name);
		mDescription.setText(descripcion);
		setCategory(tipo);

		DateView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDatePickerDialog();
			}
		});
		TimeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				mName.setText("");
				mDescription.setText("");
				mName.setHint(R.string.name_event_example);
				mDescription.setHint(R.string.text_descrip);

			}
		});
		final Button camaraButton = (Button) findViewById(R.id.buttonCamera);
		camaraButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String file = ruta_fotos + getCode() + ".jpg";
				File mi_foto = new File(file);
				try {
					mi_foto.createNewFile();
				} catch (IOException ex) {
					Log.e("Error", "Error: " + ex);
				}
				mUri = Uri.fromFile(mi_foto);
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
				startActivityForResult(i, 0);
			}
		});
		// OnClickListener for the Submit Button
		// Implement onClick().

		final Button submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String nameString = mName.getText().toString();
				if (nameString.isEmpty())
					Toast.makeText(getApplicationContext(),
							"¿Nombre del Evento?", Toast.LENGTH_LONG).show();
				else {
					String descripString = mDescription.getText().toString();
					Category category = getCategory();

					String fullDate = dateString + " " + timeString;
					Intent data = new Intent();
					EventoItem.packageIntent(data, nameString, descripString,
							fullDate, category, mUri,id);
					// Package MateriaItem data into an Intent
					if (mRecordatorio.isChecked()) {
						Intent intent = new Intent(DetailEvento.this,
								OneShotAlarm.class);
						PendingIntent sender = PendingIntent.getBroadcast(
								DetailEvento.this, 0, intent, 0);

						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(System.currentTimeMillis());
						calendar.set(Calendar.YEAR, years);
						calendar.set(Calendar.MONTH, months);
						calendar.set(Calendar.DAY_OF_MONTH, days);
						calendar.set(Calendar.HOUR_OF_DAY, hours);
						calendar.set(Calendar.MINUTE, minut);
						calendar.set(Calendar.SECOND, 0);
						AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
						am.set(AlarmManager.RTC_WAKEUP,
								calendar.getTimeInMillis(), sender);
					}
					// TODO - return data Intent and finish
					setResult(2, data);
					finish();
				}

			}
		});

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
		return super.onOptionsItemSelected(item);
	}

	private Category getCategory() {

		switch (mCategoryRadioGroup.getCheckedRadioButtonId()) {
		case R.id.radioactivity: {
			return Category.ACTIVIDAD;
		}
		case R.id.radioexam: {
			return Category.EXAMEN;
		}
		case R.id.radiotask: {
			return Category.TRABAJO;
		}
		default: {
			return Category.ACTIVIDAD;
		}
		}
	}

	private void setDefaultDateTime() {

		// Default is current time + 7 days
		mDate = new Date();
		mDate = new Date(mDate.getTime());

		Calendar c = Calendar.getInstance();
		c.setTime(mDate);

		setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));

		DateView.setText(dateString);

		setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				c.get(Calendar.MILLISECOND));

		TimeView.setText(timeString);
		years = Calendar.YEAR;
		months = Calendar.MONTH;
		days = Calendar.DAY_OF_MONTH;
		hours = Calendar.HOUR_OF_DAY;
		minut = Calendar.MINUTE;
	}

	private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

		// Increment monthOfYear for Calendar/Date -> Time Format setting
		monthOfYear++;
		String mon = "" + monthOfYear;
		String day = "" + dayOfMonth;

		if (monthOfYear < 10)
			mon = "0" + monthOfYear;
		if (dayOfMonth < 10)
			day = "0" + dayOfMonth;

		dateString = year + "-" + mon + "-" + day;
		years = year;
		months = monthOfYear;
		days = dayOfMonth;

	}

	private static void setTimeString(int hourOfDay, int minute, int mili) {
		String hour = "" + hourOfDay;
		String min = "" + minute;

		if (hourOfDay < 10)
			hour = "0" + hourOfDay;
		if (minute < 10)
			min = "0" + minute;

		timeString = hour + ":" + min + ":00";

		hours = hourOfDay;
		minut = minute;

	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Use the current date as the default date in the picker

			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			setDateString(year, monthOfYear, dayOfMonth);

			DateView.setText(dateString);
		}

	}

	// DialogFragment used to pick a MateriaItem deadline time

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

			TimeView.setText(timeString);
		}
	}

	private void setCategory(String id) {
		switch (id) {
		case "ACTIVIDAD": {
			mCategoryRadioGroup.check(R.id.radioactivity);
		}
		case "EXAMEN": {
			mCategoryRadioGroup.check(R.id.radioexam);
		}
		case "TRABAJO": {
			mCategoryRadioGroup.check(R.id.radiotask);
		}
		}
	}

	private void showDatePickerDialog() {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	private void showTimePickerDialog() {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try {
			InputStream is = getContentResolver().openInputStream(mUri);
			BufferedInputStream bis = new BufferedInputStream(is);
			Bitmap bitmap = BitmapFactory.decodeStream(bis);
			imagen.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
		}

	}

	private String getCode() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyymmddhhmmss");
		String date = dateformat.format(new Date());
		String photocode = "pic_" + date;
		return photocode;

	}
	@Override
	protected void onDestroy() {

		mDbHelper.getWritableDatabase().close();
		// mDbHelper.deleteDatabase();
		//
		super.onDestroy();

	}

}
