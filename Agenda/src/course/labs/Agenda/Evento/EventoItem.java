package course.labs.Agenda.Evento;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.R.string;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class EventoItem {
	public static final String ITEM_SEP = System.getProperty("line.separator");
	public static Boolean foto=false;
	public enum Category {
		ACTIVIDAD, EXAMEN, TRABAJO
	};

	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.US);

	/* EVENTOS */
	public final static String NAME = "name";
	public final static String DESCRIPTION = "description";
	public final static String DATE = "date";
	public final static String URI = "uri";
	public final static String ID = "id";
	public final static String CATEGORY = "category";

	private String mName = new String();
	private String mDescrp = new String();
	private Date mDate = new Date();
	private Uri mUri ;
	private Category mCategory = Category.ACTIVIDAD;

	public EventoItem(String mName, String mDescrp, Date mDate,
			Category mCategory, Uri mUri) {
		this.mName = mName;
		this.mDescrp = mDescrp;
		this.mDate = mDate;
		this.mCategory = mCategory;
		this.mUri = mUri;
	}

	// Create a new EventoItem from data packaged in an Intent

	public EventoItem(Intent intent) {

		mName = intent.getStringExtra(EventoItem.NAME);

		mDescrp = intent.getStringExtra(EventoItem.DESCRIPTION);
		mCategory = Category
				.valueOf(intent.getStringExtra(EventoItem.CATEGORY));

		try {
			mDate = EventoItem.FORMAT.parse(intent
					.getStringExtra(EventoItem.DATE));
		} catch (java.text.ParseException e) {
			mDate = new Date();

		}
	
			mUri = Uri.parse(intent.getStringExtra(EventoItem.URI));
		
	}
	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public Uri getmUri() {
		return mUri;
	}

	public void setmUri(Uri mUri) {
		this.mUri = mUri;
	}

	public String getmDescrp() {
		return mDescrp;
	}

	public void setmDescrp(String mDescrp) {
		this.mDescrp = mDescrp;
	}

	public Date getmDate() {
		return mDate;
	}

	public void setmDate(Date mDate) {
		this.mDate = mDate;
	}

	public Category getmCategory() {
		return mCategory;
	}

	public void setmCategory(Category mCategory) {
		this.mCategory = mCategory;
	}

	public static void packageIntent(Intent intent, String mName,
			String mDescrp, String mDate, Category mCategory,Uri mUri,String id) {
		intent.putExtra(EventoItem.ID, id);

		intent.putExtra(EventoItem.NAME, mName);
		intent.putExtra(EventoItem.DATE, mDate);
		intent.putExtra(EventoItem.DESCRIPTION, mDescrp);
		intent.putExtra(EventoItem.CATEGORY, mCategory.toString());
		intent.putExtra(EventoItem.URI, mUri.toString());

	}

	public static void packageIntentF(Intent intent, String mName,
			String mDescrp, String mDate, Category mCategory, Uri mUri) {

		intent.putExtra(EventoItem.NAME, mName);
		intent.putExtra(EventoItem.DATE, mDate);
		intent.putExtra(EventoItem.DESCRIPTION, mDescrp);
		intent.putExtra(EventoItem.CATEGORY, mCategory.toString());

			intent.putExtra(EventoItem.URI, mUri.toString());
			
	}

	public String toString() {
		return mName + ITEM_SEP + FORMAT.format(mDate) + ITEM_SEP + mDescrp
				+ ITEM_SEP + mCategory;

	}

}
