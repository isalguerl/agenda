package course.labs.Agenda.Horario;

import course.labs.Agenda.R;
import course.labs.Agenda.Materia.DatabaseMateria;
import course.labs.Agenda.R.id;
import course.labs.Agenda.R.layout;
import course.labs.Agenda.R.menu;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListaMaterias extends ListActivity {
	private DatabaseMateria mDbHelper;
	private SimpleCursorAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mDbHelper = new DatabaseMateria(this);
		Cursor c = readMaterias();
		mAdapter = new SimpleCursorAdapter(this, R.layout.horario_materias, c,
				DatabaseMateria.getColumns3(), new int[] { R.id.textViewMateria, }, 0);
		 
		ListView lv = getListView();
			
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					TextView textView = (TextView) view
							.findViewById(R.id.textViewMateria);
					String Materia = textView.getText().toString();
					Intent data = new Intent();
					data.putExtra("Materia", Materia);
					setResult(RESULT_OK, data);
					finish();


				}
			});
			setListAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.lista_materias, menu);
		return true;
	}
	private Cursor readMaterias() {
		return mDbHelper.getWritableDatabase().query(
				DatabaseMateria.getTableName(), DatabaseMateria.getColumns(),
				null, new String[] {}, null, null, null);

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
