package course.labs.Agenda;


import course.labs.Agenda.R;
import course.labs.Agenda.Calendario.ManagerCalendario;
import course.labs.Agenda.Calificacion.TransicionCalificacion;
import course.labs.Agenda.Evento.ManagerEvento;
import course.labs.Agenda.Horario.ManagerHorario;
import course.labs.Agenda.Materia.ManagerMateria;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;


public class MainActivity extends Activity {
	public static Integer NOTIFICATION_ID=0;

	ImageView image_materia, image_evento, image_calificaciones,image_Horario;
	Button botonCalendario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
	     tabHost.setup();
	     TabHost.TabSpec spec = tabHost.newTabSpec("Menu");
	     spec.setContent(R.id.tab1);
	     spec.setIndicator("Menu");
	     tabHost.addTab(spec);
	     
	   
	     
	     tabHost.setup();
	     TabHost.TabSpec spec3 = tabHost.newTabSpec("Calendario");
	     spec3.setContent(R.id.tab4);
	     spec3.setIndicator("Calendario");
	     tabHost.addTab(spec3);
	     
	     tabHost.setup();
	     TabHost.TabSpec spec5 = tabHost.newTabSpec("Información");
	     spec5.setContent(R.id.tab5);
	     spec5.setIndicator("Información");
	     tabHost.addTab(spec5);
	     
	     botonCalendario=(Button) findViewById(R.id.button1);
	     image_materia=(ImageView)findViewById(R.id.imageView1);
	     image_evento=(ImageView)findViewById(R.id.imageView3);
	     image_calificaciones=(ImageView)findViewById(R.id.imageView4);
	     image_Horario=(ImageView)findViewById(R.id.imageView2);

	     botonCalendario.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(MainActivity.this, ManagerCalendario.class);
				startActivity(i);
			}
		});
	     
	     image_Horario.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i= new Intent(MainActivity.this, ManagerHorario.class);
					startActivity(i);
				}
			});
	     
	     image_materia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(MainActivity.this, ManagerMateria.class);
				i.putExtra("categoria", "Materia.txt");
				startActivity(i);
			}
		});
	     image_evento.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(MainActivity.this, ManagerEvento.class);
				i.putExtra("categoria", "Evento.txt");
				startActivity(i);
			}
		});
	     image_calificaciones.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(MainActivity.this, TransicionCalificacion.class);
				i.putExtra("categoria", "Materia.txt");
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
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

	public static Integer getNOTIFICATION_ID() {
		return NOTIFICATION_ID;
	}

	public static void setNOTIFICATION_ID() {
		NOTIFICATION_ID ++;
	}
}
