package course.labs.Agenda.Horario;

import course.labs.Agenda.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListaDias extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] aplicaciones = getResources().getStringArray(R.array.dias);

		this.setListAdapter(new ArrayAdapter<String>(this,
				R.layout.etiqueta_list_view, R.id.etiqueta, aplicaciones));

		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String dia = ((TextView) view).getText().toString();
				
				Log.i("SADAS", dia);
				Intent data;
				switch (dia) {
				case "Lunes":
					data = new Intent();
					data.putExtra("dia", dia.toString());
					setResult(RESULT_OK, data);
					finish();
					break;
				case "Martes":
					data = new Intent();
					data.putExtra("dia", dia);
					setResult(RESULT_OK, data);
					finish();
					break;
				case "Miercoles":
					data = new Intent();
					data.putExtra("dia", dia);
					setResult(RESULT_OK, data);
					finish();
					break;
				case "Jueves":
					data = new Intent();
					data.putExtra("dia", dia);
					setResult(RESULT_OK, data);
					finish();
					break;
				case "Viernes":
					data = new Intent();
					data.putExtra("dia", dia);
					setResult(RESULT_OK, data);
					finish();
					break;
				case "Sabado":
					data = new Intent();
					data.putExtra("dia", dia);
					setResult(RESULT_OK, data);
					finish();
					break;
				case "Domingo":
					data = new Intent();
					data.putExtra("dia", dia);
					setResult(RESULT_OK, data);
					finish();
					break;

				default:
					break;
				}

			}
		});
	}

}
