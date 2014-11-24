package course.labs.Agenda.Calificacion;

import course.labs.Agenda.R;
import course.labs.Agenda.Materia.DatabaseMateria;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TransicionCalificacion extends ListActivity {

	private DatabaseMateria mDbHelper;
	private SimpleCursorAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		mDbHelper = new DatabaseMateria(this);

		Cursor c = readArtists();
		mAdapter = new SimpleCursorAdapter(this, R.layout.todo_item, c,
				DatabaseMateria.getColumns2(), new int[] { R.id.titleView, }, 0);

		ListView lv = getListView();
		

		// Nos ponemos a escuchar por si se selecciona algún elemento del
		// ListView
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				TextView textView = (TextView) view
						.findViewById(R.id.titleView);
				String text = textView.getText().toString();
				Intent i = new Intent(TransicionCalificacion.this,
						ManagerCalificacion.class);
				i.putExtra("NAME_MATERIA", text);
				startActivity(i);

			}
		});

		setListAdapter(mAdapter);

	}

	// Returns all artist records in the database
	private Cursor readArtists() {
		return mDbHelper.getWritableDatabase().query(
				DatabaseMateria.getTableName(), DatabaseMateria.getColumns(),
				null, new String[] {}, null, null, null);

	}
	@Override
	protected void onDestroy() {

		mDbHelper.getWritableDatabase().close();
		// mDbHelper.deleteDatabase();
		//
		super.onDestroy();

	}

}
