package course.labs.Agenda.Evento;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import course.labs.Agenda.R;
import course.labs.Agenda.Calificacion.DatabaseCalificacion;
import course.labs.Agenda.Materia.AddMateria;
import course.labs.Agenda.Materia.DatabaseMateria;
import course.labs.Agenda.Materia.ManagerMateria;

public class ManagerEvento extends ListActivity {

	// Add a EventoItem Request Code
	private static final int ADD_TODO_ITEM_REQUEST = 0;
	String FILE_NAME;

	private static final String TAG = "Lab-UserInterface";

	// IDs for menu items
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int MENU_DUMP = Menu.FIRST + 1;
	private static DatabaseEvento mDbHelper;
	private static SimpleCursorAdapter mAdapter;
	// EventoListAdapter mAdapter;
	String categoria;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mDbHelper = new DatabaseEvento(this);
		getListView().setHeaderDividersEnabled(true);

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
				String [] items= {"Agenda", "Agenda del sistema"};
				AlertDialog.Builder builder = new AlertDialog.Builder(ManagerEvento.this);
				builder.setTitle("Seleccione App");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// TODO Auto-generated method stub
						switch(item){
						case 0:
							Intent intent = new Intent(getApplicationContext(),
									AddEvento.class);
							startActivityForResult(intent, ADD_TODO_ITEM_REQUEST);
							break;
						case 1:
						//	String rrule =  "FREQ=DAILY;COUNT=4;BYDAY=MO";//se repite los 4 proximos lunes
							Intent i = new Intent(Intent.ACTION_INSERT)
							        .setData(Events.CONTENT_URI);
							startActivity(i);
							break;
						}
					}
				});
				AlertDialog nombrealert = builder.create();
				nombrealert.show();
			}
		});

		Cursor c = readEvents();
		mAdapter = new SimpleCursorAdapter(this, R.layout.evento_item, c,
				DatabaseEvento.columns2, new int[] { R.id.textNameEvent,
						R.id.textDate, R.id.textCategory }, 0);

		setListAdapter(mAdapter);
		ListView lv = getListView();
		// detalles del evento SIN COMPLETAR
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) view
						.findViewById(R.id.textNameEvent);
				String text = textView.getText().toString();

				android.app.FragmentManager fm = getFragmentManager();
				DialogFragment dfm = DialogFragment.getNewInstance(text);
				dfm.show(fm, "Dialog");
				return false;
			}
		});

		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						DetailEvento.class);
				intent.putExtra("identificador", Long.toString(id));
				startActivityForResult(intent, ADD_TODO_ITEM_REQUEST);

			}
		});

		
	}

	// Returns all artist records in the database
	private Cursor readEvents() {
		return mDbHelper.getWritableDatabase()
				.query(DatabaseEvento.TABLE_NAME, DatabaseEvento.columns, null,
						new String[] {}, null, null, null);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// log("Entered onActivityResultEvent()");

		// TODO - Check result code and request code.
		// If user submitted a new EventoItem
		// Create a new EventoItem from the data Intent
		// and then add it to the adapter
		if (requestCode == ADD_TODO_ITEM_REQUEST) {
			if (resultCode == RESULT_OK) {

				EventoItem EventoItem = new EventoItem(data);
				insert(EventoItem);
			}
			if (resultCode == 2) {
				EventoItem EventoItem = new EventoItem(data);

				actualizar(EventoItem,data.getStringExtra("id"));
			}
		}

	}

	public void insert(EventoItem eventoItem) {

		ContentValues values = new ContentValues();
		values.put(DatabaseEvento.EVE_NAME, eventoItem.getmName());

		values.put(DatabaseEvento.EVE_FECHA, eventoItem.getmDate().toString());

		values.put(DatabaseEvento.DESCRIP_NAME, eventoItem.getmDescrp());

		values.put(DatabaseEvento.EVE_TIPO, eventoItem.getmCategory()
				.toString());
			values.put(DatabaseEvento.URI, eventoItem.getmUri().toString());
		mDbHelper.getWritableDatabase().insert(DatabaseEvento.TABLE_NAME, null,
				values);

		values.clear();
		mAdapter.getCursor().requery();
		mAdapter.notifyDataSetChanged();

	}
	public void actualizar(EventoItem eventoItem,String id) {

		ContentValues values = new ContentValues();
		values.put(DatabaseEvento.EVE_NAME, eventoItem.getmName());

		values.put(DatabaseEvento.EVE_FECHA, eventoItem.getmDate().toString());

		values.put(DatabaseEvento.DESCRIP_NAME, eventoItem.getmDescrp());

		values.put(DatabaseEvento.EVE_TIPO, eventoItem.getmCategory()
				.toString());
			values.put(DatabaseEvento.URI, eventoItem.getmUri().toString());
		mDbHelper.getWritableDatabase().update(DatabaseEvento.TABLE_NAME, values, DatabaseEvento._ID + " = ?",
	            new String[] { id});

		values.clear();
		mAdapter.getCursor().requery();
		mAdapter.notifyDataSetChanged();

	}
	

	private void clearAll() {

		mDbHelper.getWritableDatabase().delete(DatabaseEvento.TABLE_NAME, null,
				null);

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
		//menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log");
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
									mDbHelper.getWritableDatabase().delete(
											DatabaseEvento.TABLE_NAME,
											DatabaseEvento.EVE_NAME + "=?",
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
