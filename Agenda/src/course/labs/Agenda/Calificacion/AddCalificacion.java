package course.labs.Agenda.Calificacion;

import course.labs.Agenda.R;
import course.labs.Agenda.Materia.MateriaItem;
import course.labs.Agenda.R.id;
import course.labs.Agenda.R.layout;
import course.labs.Agenda.R.menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCalificacion extends Activity {
	private static final String TAG = "Lab-UserInterface";
	private EditText mTitleText, mCalificationText, mWeightText;
	String nombreMateria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_clasificacion);
		
		Bundle objeto = getIntent().getExtras();
		nombreMateria = objeto.getString("MATERIA");
		
		mTitleText = (EditText) findViewById(R.id.title);
		mCalificationText = (EditText) findViewById(R.id.EditText01);
		mWeightText = (EditText) findViewById(R.id.EditText02);

		final Button cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered cancelButton.OnClickListener.onClick()");

				// TODO - Implement onClick().
				finish();

			}
		});

		// OnClickListener for the Reset Button

		final Button resetButton = (Button) findViewById(R.id.resetButton);
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				log("Entered resetButton.OnClickListener.onClick()");
				mTitleText.setText("");
				mTitleText.setHint(R.string.enter_title_text);
				mCalificationText.setText("");
				mWeightText.setText("");

			}
		});

		final Button submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered submitButton.OnClickListener.onClick()");

				String WeightString = mWeightText.getText().toString();
				String titleString = mTitleText.getText().toString();
				String calificationString = mCalificationText.getText()
						.toString();
				if (calificationString.isEmpty() || titleString.isEmpty())
					Toast.makeText(getApplicationContext(),
							"Introduzca Nombre o calificación", Toast.LENGTH_LONG)
							.show();
				else {
					

					// Package MateriaItem data into an Intent
					Intent data = new Intent();
					CalificacionItem.packageIntent(data, titleString,
							calificationString, WeightString, nombreMateria);

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
		//getMenuInflater().inflate(R.menu.add_clasificacion, menu);
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

	private void log(String msg) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.i(TAG, msg);
	}
}
