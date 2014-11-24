package course.labs.Agenda.Materia;


import course.labs.Agenda.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMateria extends Activity {
	private static final String TAG = "Lab-UserInterface";
	private EditText mTitleText, mAbreText, mTeacherText, mEmailText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_todo);

		mTitleText = (EditText) findViewById(R.id.title);
		mAbreText = (EditText) findViewById(R.id.EditText03);
		mTeacherText = (EditText) findViewById(R.id.EditText01);
		mEmailText = (EditText) findViewById(R.id.EditText02);

		// OnClickListener for the Cancel Button, 

		final Button cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered cancelButton.OnClickListener.onClick()");

				//TODO - Implement onClick().  
				finish();

			}
		});

		//OnClickListener for the Reset Button

		final Button resetButton = (Button) findViewById(R.id.resetButton);
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered resetButton.OnClickListener.onClick()");

				//TODO - Reset data fields to default values
				
				mTitleText.setText("");
				mTitleText.setHint(R.string.enter_title_string);
				mAbreText.setText("");
				mTeacherText.setText("");
				mTeacherText.setHint(R.string.enter_optional_string);
				mEmailText.setText("");
				mEmailText.setHint(R.string.enter_optional_string);
			
			
			}
		});

		// OnClickListener for the Submit Button
		// Implement onClick().
		
		final Button submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered submitButton.OnClickListener.onClick()");

				String titleString = mTitleText.getText().toString();
				if (titleString.isEmpty())
					Toast.makeText(getApplicationContext(), "¿Nombre de la Asignatura?", Toast.LENGTH_LONG).show();	
				else{
				String AbreString = mAbreText.getText().toString();
				String TeacherString = mTeacherText.getText().toString();
				String EmailString = mEmailText.getText().toString();

				// Package MateriaItem data into an Intent
				Intent data = new Intent();
				MateriaItem.packageIntent(data,titleString, AbreString, TeacherString, EmailString);

				//TODO - return data Intent and finish
				setResult(RESULT_OK, data);
				finish();
				}
				
				
			}
		});
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
