package course.labs.Agenda.Evento;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import course.labs.Agenda.R;
import course.labs.Agenda.Evento.EventoItem.Category;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
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

public class AddEvento extends Activity {
	private final String ruta_fotos = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
			+ "/misfotos/";
	private File file = new File(ruta_fotos);
	final static int cons = 0;
	Bitmap bmp;
	private Boolean foto = false;
	static Integer years, months, days, hours, minut;

	private String name = "";

	private EditText mName;
	private EditText mDescription;
	private static TextView DateView, TimeView;

	private Date mDate;
	private RadioGroup mCategoryRadioGroup;
	private RadioButton mDefaultStatusButton;
	private CheckBox mRecordatorio;
	private static String timeString, dateString;
	Toast mToast;
	ImageView imagen;
	private Uri uri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_evento);
		imagen = (ImageView) findViewById(R.id.imageView1);
		file.mkdirs();
		mName = (EditText) findViewById(R.id.title);
		mDescription = (EditText) findViewById(R.id.editTextDescrip);
		DateView = (TextView) findViewById(R.id.date);
		TimeView = (TextView) findViewById(R.id.time);
		mCategoryRadioGroup = (RadioGroup) findViewById(R.id.radioGroupCategory);
		mRecordatorio = (CheckBox) findViewById(R.id.CheckBox1);
		setDefaultDateTime();

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
		final Button camaraButton = (Button) findViewById(R.id.buttonCamera);
		camaraButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foto=true;
				String file = ruta_fotos + getCode() + ".jpg";
				File mi_foto = new File(file);
				try {
					mi_foto.createNewFile();
				} catch (IOException ex) {
					Log.e("Error", "Error: " + ex);
				}
				uri = Uri.fromFile(mi_foto);
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(i, 0);
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
					if (foto==false){
						uri=uri.parse("vacio");
					EventoItem.packageIntentF(data, nameString, descripString,
							fullDate, category,uri);
}
					else{

						EventoItem.packageIntentF(data, nameString, descripString,
								fullDate, category,uri);}
					// Package MateriaItem data into an Intent
					if (mRecordatorio.isChecked()) {

						Intent intent = new Intent(AddEvento.this,
								OneShotAlarm.class);
						PendingIntent sender = PendingIntent.getBroadcast(
								AddEvento.this, 0, intent, 0);

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
					setResult(RESULT_OK, data);
					finish();
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.add_evento, menu);
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

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());

		setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));

		DateView.setText(dateString);

		setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				c.get(Calendar.MILLISECOND));

		TimeView.setText(timeString);
		
		years = c.get(Calendar.YEAR);
		months =c.get(Calendar.MONTH);
		days = c.get(Calendar.DAY_OF_MONTH);
		hours = c.get(Calendar.HOUR_OF_DAY);
		minut = c.get(Calendar.MINUTE);

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
			years=year;
			months=month;
			days=day;
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			setDateString(year, monthOfYear, dayOfMonth);
			years = year;
			months = monthOfYear;
			days = dayOfMonth;
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
			hours = hourOfDay;
			minut = minute;
			TimeView.setText(timeString);
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
			InputStream is = getContentResolver().openInputStream(uri);
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

}
