package it.tieto.projects.si3m;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

public class ReportDetailActivity  extends Activity {
	
	// JSON node keys
	private static final String TAG_STAT_TIME = "stat_time";
	private static final String TAG_STAT_DATE = "stat_date";
	private static final String TAG_AREA = "area";
	private static final String TAG_SYSTEM = "system";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_MESSAGE = "message";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
//		ActionBar actionBar = getActionBar();
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		// set the app icon as an action to go home
////		actionBar.setDisplayHomeAsUpEnabled(true);
////		actionBar.setHomeButtonEnabled(true);
////		actionBar.set
//		actionBar.setDisplayShowTitleEnabled(true);
//		
//		actionBar.show();
		
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String stat_time = in.getStringExtra(TAG_STAT_TIME);
        String stat_date = in.getStringExtra(TAG_STAT_DATE);
        String area = in.getStringExtra(TAG_AREA);
        String system = in.getStringExtra(TAG_SYSTEM);
        String description = in.getStringExtra(TAG_DESCRIPTION);
        String message = in.getStringExtra(TAG_MESSAGE);
        
        // Displaying all values on the screen
        TextView lblstat_time = (TextView) findViewById(R.id.stat_time);
//        TextView lbldate_separator = (TextView) findViewById(R.string.date_separator);
        TextView lblstat_date = (TextView) findViewById(R.id.stat_date);
        TextView lblarea = (TextView) findViewById(R.id.area);
        TextView lblsystem = (TextView) findViewById(R.id.system);
        TextView lbldescription = (TextView) findViewById(R.id.description);
        TextView lblmessage = (TextView) findViewById(R.id.message);
        
        lblstat_time.setText(stat_time);
        lblstat_date.setText(stat_date);
        lblarea.setText(area);
        lblsystem.setText(system);
        lbldescription.setText(description);
        lblmessage.setText(message);
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
