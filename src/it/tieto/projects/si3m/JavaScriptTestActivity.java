package it.tieto.projects.si3m;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class JavaScriptTestActivity extends Activity {
    /** Called when the activity is first created. */
    InputStream in_s = null;
    
    public final static String TIPO_GRAFICO = "it.tieto.projects.si3m.JavaScriptTestActivity.TIPO_GRAFICO";
    
    @Override    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	
        Intent detailIntent = getIntent();
        
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        WebView webView=new WebView(this);

        final class ChartDataLoader{
        	
        	public String getData() { 

        		// qui verrà passato il nome del file come parametro extra
        		//Bundle b = getIntent().getExtras();
        		// b.putString("jsonFileName", "batchupctable.json");
        		// String jsonFileName = b.getString(TAG_JSON_FILE);
        		String jsonFileName = "ricariche.json";
        		AssetManager mgr = getBaseContext().getAssets();
        		try {
        			in_s = mgr.open("JSON/" + jsonFileName);
        		} catch (IOException e1) {
	        		Log.e("TableView", "Error opening file " + jsonFileName);
	        		e1.printStackTrace();
        		}

        		// Creating JSON Parser instance
        		JSONParser jParser = new JSONParser();
        		// Parsing JSON file}
        		JSONObject jsondata = jParser.getJSONFromFile(in_s);

        		
        		return jsondata.toString();
        		
        	}
        	
        }
        
        webView.addJavascriptInterface(new ChartDataLoader(), "dataLoader");
		
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
        setContentView(webView);            

        Bundle b = getIntent().getExtras();
		int viewId = b.getInt("ViewId");
//		Log.d("DEBUG", "viewId:"+viewId+" R.id.textView1:"+R.id.textView1+
//				"R.id.textView2:"+R.id.textView2+
//				"R.id.textView3:"+R.id.textView3);
		switch (viewId) {
		case R.id.textView1:
			Log.d("DEBUG", "pressed graphic textView1");
			webView.loadUrl("file:///android_asset/index.html");
			break;
		case R.id.textView2:
			Log.d("DEBUG", "pressed graphic textView2");
			webView.loadUrl("file:///android_asset/npress.html");
			break;
			
		case R.id.textView3:
			Log.d("DEBUG", "pressed graphic textView3");
			webView.loadUrl("file:///android_asset/ricbnk.html");
			break;
		default:
			break;
		}            
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