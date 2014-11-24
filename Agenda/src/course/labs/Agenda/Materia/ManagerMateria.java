package course.labs.Agenda.Materia;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import course.labs.Agenda.R;
import course.labs.Agenda.Evento.DatabaseEvento;
import course.labs.Agenda.Evento.DetailEvento;
import course.labs.Agenda.Evento.EventoItem;
import course.labs.Agenda.Evento.ManagerEvento.DialogFragment;

public class ManagerMateria extends ListActivity {

	// Add a MateriaItem Request Code
	private static final int ADD_TODO_ITEM_REQUEST = 0;

	// IDs for menu items
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int MENU_DUMP = Menu.FIRST + 1;
	private static DatabaseMateria mDbHelper;
	private static SimpleCursorAdapter mAdapter;
	Button insert;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		mDbHelper = new DatabaseMateria(this);
		// Put divider between ToDoItems and FooterView
		getListView().setHeaderDividersEnabled(true);
		Log.i("asdsa", "asdasd");

		// TODO - Inflate footerView for footer_view.xml file
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
						AddMateria.class);
				startActivityForResult(intent, ADD_TODO_ITEM_REQUEST);

			}
		});

		Cursor c = readArtists();
		mAdapter = new SimpleCursorAdapter(this, R.layout.todo_item, c,
				DatabaseMateria.columns2, new int[] { R.id.titleView,
						R.id.TeacherView, R.id.EmailView }, 0);

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

		// Nos ponemos a escuchar por si se selecciona algÃºn elemento del
		// ListView
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(getApplicationContext(),
						DetailMateria.class);
				intent.putExtra("identificador", Long.toString(id));
				startActivityForResult(intent, ADD_TODO_ITEM_REQUEST);

			}
		});

		setListAdapter(mAdapter);

	}

	// Returns all artist records in the database
	private Cursor readArtists() {
		return mDbHelper.getWritableDatabase().query(
				DatabaseMateria.TABLE_NAME, DatabaseMateria.columns, null,
				new String[] {}, null, null, null);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ADD_TODO_ITEM_REQUEST) {
			if (resultCode == RESULT_OK) {
				MateriaItem materiaItem = new MateriaItem(data);
				insert(materiaItem);
			}
			if (resultCode == 2) {
				MateriaItem Item = new MateriaItem(data);

				actualizar(Item, data.getStringExtra("id"));
			}
		}

	}
	public void actualizar(MateriaItem eventoItem,String id) {

		ContentValues values = new ContentValues();
		values.put(DatabaseMateria.MATERIA_NAME, eventoItem.getTitle());

		values.put(DatabaseMateria.ABREV_NAME, eventoItem.getmAbrev());

		values.put(DatabaseMateria.EMAIL, eventoItem.getmEmail());

		values.put(DatabaseMateria.TEACHER_NAME, eventoItem.getmTeacher()
				.toString());
		mDbHelper.getWritableDatabase().update(DatabaseMateria.TABLE_NAME, values, DatabaseMateria._ID + " = ?",
	            new String[] { id});

		values.clear();
		mAdapter.getCursor().requery();
		mAdapter.notifyDataSetChanged();

	}
	public void insert(MateriaItem materiaItem) {

		ContentValues values = new ContentValues();
		values.put(DatabaseMateria.MATERIA_NAME, materiaItem.getTitle());

		Log.i("sdsd", materiaItem.getTitle());

		values.put(DatabaseMateria.ABREV_NAME, materiaItem.getmAbrev());
		Log.i("sdsd", materiaItem.getmAbrev());

		values.put(DatabaseMateria.TEACHER_NAME, materiaItem.getmTeacher());
		Log.i("sdsd", materiaItem.getmTeacher());

		values.put(DatabaseMateria.EMAIL, materiaItem.getmEmail());
		Log.i("sdsd", materiaItem.getmEmail());
		
		mDbHelper.getWritableDatabase().insert(DatabaseMateria.TABLE_NAME,
				null, values);
		values.clear();
		mAdapter.getCursor().requery();
		mAdapter.notifyDataSetChanged();


	}


	private void clearAll() {

		mDbHelper.getWritableDatabase().delete(DatabaseMateria.TABLE_NAME,
				null, null);

	}

	// Close database
	@Override
	protected void onDestroy() {

		mDbHelper.getWritableDatabase().close();
		// mDbHelper.deleteDatabase();
		//
		super.onDestroy();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
		// menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_DELETE:
			clearAll();
			mAdapter.getCursor().requery();
			mAdapter.notifyDataSetChanged();
			return true;
		case MENU_DUMP:
			// dump();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
									mDbHelper
											.getWritableDatabase()
											.delete(DatabaseMateria.TABLE_NAME,
													DatabaseMateria.MATERIA_NAME
															+ "=?",
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