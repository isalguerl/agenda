package course.labs.Agenda.Calificacion;

import android.content.Intent;

public class CalificacionItem {
	public static final String ITEM_SEP = System.getProperty("line.separator");

	/* MATERIAS */
	public final static String TITLE = "title";
	public final static String CALIFICATION = "calificacion";
	public final static String WEIGHT = "peso";
	public final static String MATERIA = "materia";

	private String mTitle = new String();
	private String mCalification = new String();
	private String mWeight = new String();
	private String mMateria = new String();

	

	// Create a new CalificacionItem from data packaged in an Intent

	public CalificacionItem(String mTitle, String mCalification,
			String mWeight, String mMateria) {
		super();
		this.mTitle = mTitle;
		this.mCalification = mCalification;
		this.mWeight = mWeight;
		this.mMateria = mMateria;
	}

	public CalificacionItem(Intent intent) {
		mTitle = intent.getStringExtra(CalificacionItem.TITLE);
		mCalification = intent.getStringExtra(CalificacionItem.CALIFICATION);
		mWeight = intent.getStringExtra(CalificacionItem.WEIGHT);
		mMateria = intent.getStringExtra(CalificacionItem.MATERIA);
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getmCalification() {
		return mCalification;
	}

	public void setmCalification(String mCalification) {
		this.mCalification = mCalification;
	}

	public String getmWeight() {
		return mWeight;
	}

	public void setmWeight(String mWeight) {
		this.mWeight = mWeight;
	}

	public String getmMateria() {
		return mMateria;
	}

	public void setmMateria(String mMateria) {
		this.mMateria = mMateria;
	}

	public static void packageIntent(Intent intent, String title,
			String CalificationString, String WeightString, String MateriaString) {

		intent.putExtra(CalificacionItem.TITLE, title);
		intent.putExtra(CalificacionItem.CALIFICATION, CalificationString);
		intent.putExtra(CalificacionItem.WEIGHT, WeightString);
		intent.putExtra(CalificacionItem.MATERIA, MateriaString);

	}

	public String toString() {
		return mTitle + ITEM_SEP + mCalification + ITEM_SEP + mWeight + ITEM_SEP
				+ mMateria;

	}

}