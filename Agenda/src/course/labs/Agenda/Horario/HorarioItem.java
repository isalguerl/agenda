package course.labs.Agenda.Horario;

import java.text.SimpleDateFormat;

import android.content.Intent;

public class HorarioItem {
	public static final String ITEM_SEP = System.getProperty("line.separator");

	/* HORAIOS */
	public final static String NAME = "name";
	public final static String FECHAI = "fechai";
	public final static String FECHAF = "fechaf";
	public final static String DAY = "dia";
	private String mName = new String();
	private String mFechai = new String();
	private String mFechaf = new String();
	private String mDia = new String();

	public HorarioItem(String mName, String mFechai, String mFechaf, String mDay) {
		this.mName = mName;
		this.mFechai = mFechai;
		this.mFechaf = mFechaf;
		this.mDia = mDay;
	}

	// Create a new EventoItem from data packaged in an Intent

	public HorarioItem(Intent intent) {
		mName = intent.getStringExtra(HorarioItem.NAME);
		mDia = intent.getStringExtra(HorarioItem.DAY);
		mFechai = intent.getStringExtra(HorarioItem.FECHAI);
		mFechaf = intent.getStringExtra(HorarioItem.FECHAF);

	}

	public String getmDia() {
		return mDia;
	}

	public void setmDia(String mDia) {
		this.mDia = mDia;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmFechai() {
		return mFechai;
	}

	public void setmFechai(String mFechai) {
		this.mFechai = mFechai;
	}

	public String getmFechaf() {
		return mFechaf;
	}

	public void setmFechaf(String mFechaf) {
		this.mFechaf = mFechaf;
	}

	public static void packageIntent(Intent intent, String mName,
			String mFechai, String mFechaf, String mDia) {

		intent.putExtra(HorarioItem.NAME, mName);
		intent.putExtra(HorarioItem.FECHAI, mFechai);
		intent.putExtra(HorarioItem.FECHAF, mFechaf);
		intent.putExtra(HorarioItem.DAY, mDia);

	}

	public String toString() {
		return mName + ITEM_SEP + mFechai + ITEM_SEP
				+ mFechai + ITEM_SEP + mDia;

	}

}
