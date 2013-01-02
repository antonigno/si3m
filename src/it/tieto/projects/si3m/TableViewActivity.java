package it.tieto.projects.si3m;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class TableViewActivity extends Activity {

	// JSON Node names
	private static final String TAG_COLUMNS = "COLUMNS";
	private static final String TAG_ROWS = "ROWS";
	private static final String TAG_ROW_TITLE = "ROW_TITLE";
	//JSON file name tag
	private static final String TAG_JSON_FILE = "jsonFileName";

	// column list from JSON file
	JSONArray columns = null;
	// row list from JSON file
	JSONArray rows = null;
	// row title from JSON file
	JSONArray row_title = null;
	// input stream for JSON file
	InputStream in_s = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Point size = new Point();
		display.getSize(size);
		int rotation = display.getRotation();
		int screenHeight = metrics.heightPixels;
		int screenWidth = metrics.widthPixels;

		setContentView(R.layout.main_tableview);
		TableLayout tl = (TableLayout) findViewById(R.id.table);
		TableLayout top = (TableLayout) findViewById(R.id.top);
		// qui verrà passato il nome del file come parametro extra
		Bundle b = getIntent().getExtras();
		// b.putString("jsonFileName", "batchupctable.json");
		// String jsonFileName = b.getString(TAG_JSON_FILE);
		String jsonFileName = "batchupctable.json";
		Log.d("DEBUG", "jsonFileName:" + jsonFileName);
		AssetManager mgr = getBaseContext().getAssets();

		try {
			in_s = mgr.open("JSON/" + jsonFileName);
		} catch (IOException e1) {
			Log.e("TableView", "Error opening file " + jsonFileName);
			e1.printStackTrace();
		}

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();
		// Parsing JSON file
		JSONObject json = jParser.getJSONFromFile(in_s);

		try {
			// Getting Array of columns
			columns = json.getJSONArray(TAG_COLUMNS);
			// Getting Array of rows
			rows = json.getJSONArray(TAG_ROWS);
			// Getting row title
			row_title = json.getJSONArray(TAG_ROW_TITLE);
		} catch (JSONException e) {
			Log.e("ERROR",
					"TAG_COLUMNS or TAG_ROWS or TAG_ROW_TITLE non present in JSON");
			e.printStackTrace();
		}

		// First row
		String rowTitle = null;
		try {
			rowTitle = row_title.getString(0);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		JSONObject colObject = new JSONObject();
		for (int c = 0; c < columns.length(); c++) {
			try {
				colObject.putOpt(columns.getString(c), columns.getString(c));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		TableRow first = createTableRow(colObject, rowTitle, columns,
				(screenHeight / rows.length()),
				(screenWidth / (columns.length() + 1)), rotation, true);

		first.setBackgroundColor(Color.CYAN);
		first.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		// add first row to top table
		top.addView(first, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		// other rows
		for (int r = 0; r < rows.length(); r++) {
			try {
				String rowName = rows.getString(r);
				JSONObject data = json.getJSONObject(rowName);
				if (data != null) {
					TableRow tr = createTableRow(data, rowName, columns,
							(screenHeight / rows.length()),
							(screenWidth / (columns.length() + 1)), rotation,
							false);
					tl.addView(tr, new TableLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));
					// separator
					View view = new View(this);
					view.setLayoutParams(new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT, 1));
					view.setBackgroundColor(Color.rgb(51, 51, 51));
					// add row to table
					tl.addView(view);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	// method used to build a a tablerow
	private TableRow createTableRow(JSONObject data, String rowName,
			JSONArray columns, Integer heigthPixels, Integer widthPixels,
			Integer rotation, boolean isFirst) {
		TableRow tr = new TableRow(this);
		// tr.setLayoutParams(new LayoutParams(
		// LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT));
		// first field row name
		TextView first = new TextView(this);
		first.setGravity(Gravity.CENTER_HORIZONTAL);
		first.setText(rowName);
		first.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));
		// style for first row
		if (!isFirst) {
			first.setTextColor(Color.CYAN);
		}
		first.setWidth(widthPixels);
		tr.addView(first);
		for (int i = 0; i < columns.length(); i++) {
			try {
				// one field per column
				TextView tv = new TextView(this);
				tv.setGravity(Gravity.CENTER_HORIZONTAL);
				tv.setGravity(Gravity.CENTER_VERTICAL);
				if (rotation == Surface.ROTATION_0
						| rotation == Surface.ROTATION_180) {
					tv.setHeight((Integer) heigthPixels);
					tv.setWidth(widthPixels);
				}
				String text = data.getString(columns.getString(i));
				// if column text end with the keyword "NOK" visualize it in red
				if (text.endsWith("NOK")) {
					tv.setBackgroundColor(Color.RED);
				}
				tv.setText("  " + text + "  ");
				tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));
				tr.addView(tv);
			} catch (JSONException e) {
				e.printStackTrace();
				return tr;
			}
		}
		return tr;
	}
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
	    if (item.getItemId() == android.R.id.home) {
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	

}