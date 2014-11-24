package course.labs.Agenda.Materia;


import android.content.Intent;

// Do not modify 

public class MateriaItem {
	public static final String ITEM_SEP = System.getProperty("line.separator");

	/* MATERIAS */
	public final static String TITLE = "title";
	public final static String ABREV = "abreviatura";
	public final static String TEACHER = "profesor";
	public final static String EMAIL = "xxx@xxx.com";
	public final static String _ID = "id";
	private String mTitle = new String();
	private String mAbrev = new String();
	private String mTeacher = new String();
	private String mEmail = new String();

	public MateriaItem(String title, String abrev, String teacher, String email) {
		this.mTitle = title;
		this.mAbrev = abrev;
		this.mTeacher = teacher;
		this.mEmail = email;
	}

	// Create a new MateriaItem from data packaged in an Intent

	public MateriaItem(Intent intent) {
		mTitle = intent.getStringExtra(MateriaItem.TITLE);
		mAbrev = intent.getStringExtra(MateriaItem.ABREV);
		mTeacher = intent.getStringExtra(MateriaItem.TEACHER);
		mEmail = intent.getStringExtra(MateriaItem.EMAIL);
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getmAbrev() {
		return mAbrev;
	}

	public void setmAbrev(String mAbrev) {
		this.mAbrev = mAbrev;
	}

	public String getmTeacher() {
		return mTeacher;
	}

	public void setmTeacher(String mTeacher) {
		this.mTeacher = mTeacher;
	}

	public String getmEmail() {
		return mEmail;
	}

	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	// Take a set of String data values and
	// package them for transport in an Intent

	public static void packageIntent(Intent intent, String title,
			String AbreString, String TeacherString, String EmailString) {

		intent.putExtra(MateriaItem.TITLE, title);
		intent.putExtra(MateriaItem.ABREV, AbreString);
		intent.putExtra(MateriaItem.TEACHER, TeacherString);
		intent.putExtra(MateriaItem.EMAIL, EmailString);

	}
	public static void packageIntentDetails(Intent intent, String title,
			String AbreString, String TeacherString, String EmailString,String id) {

		intent.putExtra(MateriaItem.TITLE, title);
		intent.putExtra(MateriaItem.ABREV, AbreString);
		intent.putExtra(MateriaItem.TEACHER, TeacherString);
		intent.putExtra(MateriaItem.EMAIL, EmailString);
		intent.putExtra(MateriaItem._ID, id);
	}
	public String toString() {
		return mTitle + ITEM_SEP + mTeacher + ITEM_SEP + mAbrev + ITEM_SEP
				+ mEmail;

	}

}
