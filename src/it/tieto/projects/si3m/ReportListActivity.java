package it.tieto.projects.si3m;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class ReportListActivity extends ListActivity {

	// JSON Node names
	private static final String TAG_COLUMNS = "COLUMNS";
	private static final String TAG_AREAS = "AREAS";
	private static final String TAG_FILTERS = "FILTERS";
	
	// specific for this report list
	private static final String TAG_STAT_TIME = "stat_time";
	private static final String TAG_STAT_DATE = "stat_date";
	private static final String TAG_AREA = "area";
	private static final String TAG_SYSTEM = "system";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_MESSAGE = "message";

	// JSON file name tag
	private static final String TAG_JSON_FILE = "jsonFileName";

	// column list from JSON file
	JSONArray columns = null;
	// row list from JSON file
	JSONArray areas = null;
	// row title from JSON file
	JSONArray filters = null;
	// input stream for JSON file
	InputStream in_s = null;
	// String[] converted columns JSONArray
	List<String> columnList = new ArrayList<String>();
	
	static ArrayList<String> filterList = new ArrayList<String>();
	
	static int filterIndex = 0;
	
	// filter maps: filterMaps will contain singleFilterMap s
	static HashMap<String, Object> filterMap = new HashMap<String, Object>();
	
	static HashMap<String, String > settetFilterMap = new HashMap<String, String>();
	
	public static String setFilter(int index){
		Log.d("DEBUG", "scelta:"+filterList.get(index));
//		filterIndex = index;
		return filterList.get(index);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_report);
		String jsonFileName = null;
		// JSAONArray containing area data
		JSONArray areaReports = null;

		// HashMap<String, String> singleFilterMap = new HashMap<String,
		// String>();

		// qui verrà passato il nome del file come parametro extra
		Bundle b = getIntent().getExtras();
		int viewId = b.getInt("ViewId");
//		Log.d("DEBUG", "viewId:"+viewId+" R.id.textView1:"+R.id.textView1+
//				"R.id.textView2:"+R.id.textView2+
//				"R.id.textView3:"+R.id.textView3);
		switch (viewId) {
		case R.id.textView1:
			Log.d("DEBUG", "pressed report textView1");
			jsonFileName = "report_attivita.json";
			break;
		case R.id.textView2:
			Log.d("DEBUG", "pressed report textView2");
			jsonFileName = "report_attivita.json";
			break;
			
		case R.id.textView3:
			Log.d("DEBUG", "pressed report textView3");
			jsonFileName = "report_attivita.json";
			break;

		default:
			break;
		}
		Log.d("DEBUG", "Sono arrivato a cercare il nome del json");
		AssetManager mgr = getBaseContext().getAssets();

		try {
			in_s = mgr.open("JSON/" + jsonFileName);
		} catch (IOException e1) {
			Log.e("ReportListActivity", "Error opening file " + jsonFileName);
			e1.printStackTrace();
		}

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();
		// Parsing JSON file
		JSONObject json = jParser.getJSONFromFile(in_s);

		try {
			// Getting Array of columns
			columns = json.getJSONArray(TAG_COLUMNS);

			// build String[] columnList
			// add area to column List

			columnList.add("area");
			for (int i = 0; i < columns.length(); i++) {
				columnList.add(columns.getString(i));
			}

			// Getting Array of rows
			areas = json.getJSONArray(TAG_AREAS);
			// Getting filters
			filters = json.getJSONArray(TAG_FILTERS);
		} catch (JSONException e) {
			Log.e("ERROR",
					"TAG_COLUMNS or TAG_AREAS or TAG_FILTERS non present in JSON");
			e.printStackTrace();
		}

		// initializing filterMap and settedFilterMap
		// Filter map structure:
		// filterMap["data"]["20121205"]-> "useless_value"
		// TODO inizializzare il settedFilterMap
		// settedFilterMap["data"] -> "20121205"
		for (int i = 0; i < filters.length(); i++) {
			HashMap<String, String> singleFilterMap = new HashMap<String, String>();
			try {
				String filterName = filters.getString(i);
				// inizializzo la mappa dei filtri in modo che mostri
				// una label riconoscibile
				singleFilterMap.put("Filtra per "+filterName, "useless_value");
				filterMap.put(filterName, singleFilterMap);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		HashMap<String, Object> areaList = null;
		// TODO
		final ArrayList<HashMap<String, Object>> areaReportList = new ArrayList<HashMap<String, Object>>();

		for (int a = 0; a < areas.length(); a++) {
			// object containing all data for a specific area

			String areaName = null;
			try {
				areaName = areas.getString(a);
				areaReports = json.getJSONArray(areaName);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			for (int rep = 0; rep < areaReports.length(); rep++) {
				JSONObject report = null;
				try {
					report = areaReports.getJSONObject(rep);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// parsing single report throught columns key
				// creating new HashMap
				HashMap<String, Object> map = new HashMap<String, Object>();
				// add area name to map
				map.put("area", areaName);
				boolean filteredFlag = false;
				for (int col = 0; col < columns.length(); col++) {
					
					String columnName = null;
					try {
						columnName = columns.getString(col);
						// TODO levato: l'ho messo dopo
						map.put(columnName, report.getString(columnName));

						if (filterMap.get(columnName) != null) {
							HashMap<String, String> singleFilterMap = (HashMap<String, String>) filterMap
									.get(columnName);
							String cn = singleFilterMap.get(report.getString(columnName));
							if (cn == null) {
								singleFilterMap.put(
										report.getString(columnName),
										"useless_value");
								filterMap.put(columnName, singleFilterMap);
								filteredFlag = true;
							}
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
				if (filteredFlag){
					areaReportList.add(map);
				}
			}

		}
		

//		String[] columnArray = columnList
//				.toArray(new String[columnList.size()]);
		// TODO rimuovere e mettere i giorni o il sistema (filtri)
		// String[] menuList = {"Filtra per data","1","2"};
		// gets the activity's default ActionBar
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// set the app icon as an action to go home
		actionBar.setDisplayHomeAsUpEnabled(true);

		// TODO ciclare per tutti i filtri, qui per esempio prendo per data
		HashMap<String, String> singleFilterMap = (HashMap<String, String>) filterMap
				.get("data");
		// Log.d("DEBUG:", singleFilterMap.toString());
		filterList = new ArrayList<String>(
				singleFilterMap.keySet());
		

//		SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_dropdown_item, filterList);
		SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, filterList);
		// SpinnerAdapter spinnerAdapter = new SimpleAdapter(this, data,
		// resource, from, to)

		actionBar.setListNavigationCallbacks(spinnerAdapter, 
				new OnNavigationListener() {
					
					@Override
					public boolean onNavigationItemSelected(int itemPosition,
							long itemId) {
//						String resId = getResources().getString((int) itemId);
						String filter = ReportListActivity.setFilter(itemPosition);
						// TODO PROVA
						// areaList.put(areaName, areaReportList);
						ArrayList<HashMap<String, Object>> filteredAreaReportList = 
								filterReportList(areaReportList, "data", filter);

						Context context = getApplicationContext();
						
						ListAdapter adapter = new SimpleAdapter(context, filteredAreaReportList,
								R.layout.report_list, columnList.toArray(new String[columnList
										.size()]), new int[] { R.id.area, R.id.stat_date,
										R.id.stat_time, R.id.system, R.id.description,
										R.id.message });

						// TODO questa serve per incapsulare le tre liste dei sistemi
						// android.widget.WrapperListAdapter
						setListAdapter(adapter);
						return false;
					}		
				}
			);

		// actionBar.setSelectedNavigationItem(position);
		actionBar.show();


		// TODO questa serve per incapsulare le tre liste dei sistemi
		// android.widget.WrapperListAdapter
//		setListAdapter(adapter);
		

		/**
		 * Updating parsed JSON data into ListView
		 * */

		// selecting single ListView item
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
				// getting values from selected ListItem
				String stat_time = ((TextView) view
						.findViewById(R.id.stat_time)).getText().toString();
				String stat_date = ((TextView) view
						.findViewById(R.id.stat_date)).getText().toString();
				String area = ((TextView) view
						.findViewById(R.id.area)).getText().toString();
				String system = ((TextView) view
						.findViewById(R.id.system)).getText().toString();
				String description = ((TextView) view
						.findViewById(R.id.description)).getText().toString();
				String message = ((TextView) view
						.findViewById(R.id.message)).getText().toString();


				// String timestamp = ((TextView)
				// view.findViewById(R.id.timestamp)).getText().toString();
				// String stat_extra = ((TextView)
				// view.findViewById(R.id.stat_extra)).getText().toString();
				Log.i("DEBUG", "2");
				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						ReportDetailActivity.class);
				 in.putExtra(TAG_STAT_TIME, stat_time);
				 in.putExtra(TAG_STAT_DATE, stat_date);
				 in.putExtra(TAG_AREA, area);
				 in.putExtra(TAG_SYSTEM, system);
				 in.putExtra(TAG_DESCRIPTION, description);
				 in.putExtra(TAG_MESSAGE, message);
				Log.i("DEBUG", "3");
				startActivity(in);
				Log.i("DEBUG", "4");

			}
		
		
		});
		
		
	
	}
	
	ArrayList<HashMap<String, Object>> areaReportList = new ArrayList<HashMap<String, Object>>();


	private ArrayList<HashMap<String, Object>> filterReportList(
			ArrayList<HashMap<String, Object>> areaReportList,
			String filter, String value){
		if (value.equals("Filtra per data")){
			Log.d("Filtrato", value);
			return areaReportList;
		}
		HashMap<String, Object> report = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> filteredReportMap = 
				new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < areaReportList.size(); i++){
			report = areaReportList.get(i);
			Log.d("areaReportList", report.get(filter).toString());
			if (report.get(filter).equals(value)){
				Log.d("areaReportList FOUND", report.toString());
				filteredReportMap.add(report);
			}
		}
		return filteredReportMap;
	}
	

	OnNavigationListener mOnNavigationListener = new OnNavigationListener() {

		@Override
		public boolean onNavigationItemSelected(int itemPosition,
				long itemId) {
			return false;
		}
		
	};
	

//	 @Override
//	 public boolean onCreateOptionsMenu(Menu menu) {
//		 MenuInflater inflater = getMenuInflater();
//		 inflater.inflate(R.menu.listmenu, menu);
//		 return true;
//	 }
	
//	 @Override
//	 public boolean onOptionsItemSelected(MenuItem item) {
//		Log.d("DEBUGSPINNER", ""+item.getItemId());
//		switch (item.getItemId()) {
//		case android.R.layout.simple_spinner_dropdown_item:
//			Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT)
//					.show();
//			break;
//		case R.id.menuitem1:
//			Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT)
//					.show();
//			break;
//		case R.id.menuitem2:
//			Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT)
//					.show();
//			break;
//
//		default:
//			break;
//		}
////		Toast.makeText(this, item.getItemId(), Toast.LENGTH_SHORT);
////		Log.d("DEBUG", "item id:"+item.getItemId());
////		Toast.makeText(this, "prova", Toast.LENGTH_SHORT);
//		return true;
//	 }

}
