package course.labs.Agenda.Materia;

import java.io.File;

import course.labs.Agenda.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//@TargetApi(Build.VERSION_CODES.KITKAT)
public class DetailMateria extends Activity {
	private final String ruta_fotos = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
			+ "/misfotos/";
	private File file = new File(ruta_fotos);
	private static final int SEVEN_DAYS = 604800000;
	private EditText mName, mProfesor, mEmail, mAbre;
	private DatabaseMateria mDbHelper;
	Toast mToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDbHelper = new DatabaseMateria(this);

		setContentView(R.layout.add_todo);
		mName = (EditText) findViewById(R.id.title);

		mProfesor = (EditText) findViewById(R.id.EditText01);
		mEmail = (EditText) findViewById(R.id.EditText02);
		mAbre = (EditText) findViewById(R.id.EditText03);
		Bundle objeto = getIntent().getExtras();
		Cursor c = mDbHelper.getReadableDatabase().query(
				DatabaseMateria.TABLE_NAME, DatabaseMateria.columns,
				DatabaseMateria._ID + "=?",
				new String[] { objeto.getString("identificador") }, null, null,
				null, null);

		c.moveToFirst();
		final String id = c.getString(0);
		String name = c.getString(1);
		String abre = c.getString(2);
		String prof = c.getString(3);
		String email = c.getString(4);

		mName.setText(name);
		mAbre.setText(abre);
		mProfesor.setText(prof);
		mEmail.setText(email);

		

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

				mName.setText("");
				mEmail.setText("");
				mAbre.setText("");
				mProfesor.setText("");

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
							"¿Nombre de la Materia?", Toast.LENGTH_LONG).show();
				else {
					String abre = mAbre.getText().toString();
					String email = mEmail.getText().toString();
					String prof = mProfesor.getText().toString();
					Intent data = new Intent();
					MateriaItem.packageIntentDetails(data, nameString, abre, prof,
							email,id);
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

	@Override
	protected void onDestroy() {

		mDbHelper.getWritableDatabase().close();
		// mDbHelper.deleteDatabase();
		//
		super.onDestroy();

	}

}
