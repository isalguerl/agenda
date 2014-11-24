package course.labs.Agenda.Calificacion;


import course.labs.Agenda.R;
import course.labs.Agenda.Evento.AddEvento;
import course.labs.Agenda.Evento.DatabaseEvento;
import course.labs.Agenda.Evento.ManagerEvento;
import course.labs.Agenda.Evento.ManagerEvento.DialogFragment;
import course.labs.Agenda.Materia.DatabaseMateria;
import course.labs.Agenda.Materia.MateriaItem;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class ManagerCalificacion extends ListActivity {
	private static final int ADD_TODO_ITEM_REQUEST = 0;
	String FILE_NAME;


	// IDs for menu items
	private static final int MENU_DELETE = Menu.FIRST;
	private static DatabaseCalificacion mDbHelper;
	private static SimpleCursorAdapter mAdapter;
	private String nombreMateria;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);

		Bundle objeto = getIntent().getExtras();
		nombreMateria = objeto.getString("NAME_MATERIA");
		mDbHelper = new DatabaseCalificacion(this);
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		TextView HeaderView = (TextView) inflater.inflate(R.layout.footer_view,
				null);

		// TODO - Add footerView to ListView
		getListView().addHeaderView(HeaderView);

		HeaderView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO - Attach Listener to FooterView. Implement onClick().
				
				Intent intent = new Intent(getApplicationContext(),
						AddCalificacion.class);
				intent.putExtra("MATERIA", nombreMateria);
				startActivityForResult(intent, ADD_TODO_ITEM_REQUEST);

			}
		});

		ListView lv = getListView();

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) view
						.findViewById(R.id.titleView);
				String text = textView.getText().toString();

				android.app.FragmentManager fm = getFragmentManager();
				DialogFragment dfm = DialogFragment.getNewInstance(text);
				dfm.show(fm, "Dialog");
				return false;
			}
		});

		Cursor c = readArtists(nombreMateria);

		mAdapter = new SimpleCursorAdapter(this, R.layout.todo_item, c,
				DatabaseCalificacion.columns2, new int[] { R.id.titleView,
						R.id.TeacherView, R.id.EmailView, R.id.textViewNombre }, 0);

		setListAdapter(mAdapter);

	}

	private Cursor readArtists(String name) {
		return mDbHelper.getReadableDatabase().query(DatabaseCalificacion.TABLE_NAME, DatabaseCalificacion.columns,
				DatabaseCalificacion.MATERIA_NAME + "=?",
				new String[] { name }, null, null, null, null);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// TODO - Check result code and request code.
		// If user submitted a new MateriaItem
		// Create a new MateriaItem from the data Intent
		// and then add it to the adapter
		if (requestCode == ADD_TODO_ITEM_REQUEST) {
			if (resultCode == RESULT_OK) {
				CalificacionItem calificacionItem = new CalificacionItem(data);
				insert(calificacionItem);
				// mAdapter.add(materiaItem);
			}
		}

	}

	// Do not modify below here

	@Override
	public void onResume() {
		super.onResume();

		// Load saved ToDoItems, if necessary

	}

	@Override
	protected void onPause() {
		super.onPause();

		// Save ToDoItems

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
		return true;
	}
	@Override
	protected void onDestroy() {

		mDbHelper.getWritableDatabase().close();
		// mDbHelper.deleteDatabase();
		//
		super.onDestroy();

	}
	private void clearAll() {

		mDbHelper.getWritableDatabase().delete(DatabaseCalificacion.TABLE_NAME, null,
				null);

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_DELETE:
			clearAll();
			mAdapter.getCursor().requery();
			mAdapter.notifyDataSetChanged();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void insert(CalificacionItem calificacionItem) {

		ContentValues values = new ContentValues();
		values.put(DatabaseCalificacion.MATERIA_NAME, calificacionItem.getmMateria());


		values.put(DatabaseCalificacion.CALIFICACION_NAME, calificacionItem.getTitle());

		values.put(DatabaseCalificacion.CALIFICACION, calificacionItem.getmCalification());

		values.put(DatabaseCalificacion.PESO, calificacionItem.getmWeight());
		mDbHelper.getWritableDatabase().insert(DatabaseCalificacion.TABLE_NAME,
				null, values);
		values.clear();
		mAdapter.getCursor().requery();
		mAdapter.notifyDataSetChanged();

	}
	public static class DialogFragment extends android.app.DialogFragment {

		public static DialogFragment getNewInstance(String Title) {

			DialogFragment df = new DialogFragment();
			Bundle args = new Bundle();
			args.putString("nombre", Title);
			df.setArguments(args);
			return df;

		}

		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity())
					.setTitle(
							"¿Desea eliminar "
									+ getArguments().getString("nombre") + " ?")
					.setPositiveButton("Aceptar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									mDbHelper.getWritableDatabase().delete(
											DatabaseCalificacion.TABLE_NAME,
											DatabaseCalificacion.CALIFICACION_NAME + "=?",
											new String[] { getArguments()
													.getString("nombre") });
									mAdapter.getCursor().requery();
									mAdapter.notifyDataSetChanged();
								}
							})
					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
								}
							}).create();
		}
	}

}
