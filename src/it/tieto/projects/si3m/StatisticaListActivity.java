package it.tieto.projects.si3m;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;


public class StatisticaListActivity extends FragmentActivity
        implements StatisticaListFragment.Callbacks {

    private boolean mTwoPane;
    GraphicChooser graphicChooser = new GraphicChooser();
    TableChooser tableChooser = new TableChooser();
    ReportChooser reportChooser = new ReportChooser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_statistica_list);

        if (findViewById(R.id.statistica_detail_container) != null) {
            mTwoPane = true;
            ((StatisticaListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.statistica_list))
                    .setActivateOnItemClick(true);
            
            LogoFragment logoFragment = new LogoFragment();
            
            getSupportFragmentManager().beginTransaction()
            .add(R.id.statistica_detail_container, logoFragment ).commit();
            
        }
    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
        	
        	if ( id == "1" ) {

                GraphicChooser fragment = new GraphicChooser();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.statistica_detail_container, fragment);
                transaction.addToBackStack(null);
                
                transaction.commit();
                
        	} else if ( id == "2" ){
        		
                TableChooser fragment = new TableChooser();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.statistica_detail_container, fragment);
                transaction.addToBackStack(null);
                
                transaction.commit();
                
        	} else if ( id == "3" ) {
        		ReportChooser fragment = new ReportChooser();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.statistica_detail_container, fragment);
                transaction.addToBackStack(null);
                
                transaction.commit();
//        		Intent intent = new Intent( this, ReportListActivity.class );
//            	startActivity(intent);
                
        	}
        	

        } else {
        	
            Intent detailIntent = new Intent(this, StatisticaDetailActivity.class);
            detailIntent.putExtra(StatisticaDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
            
        }
        
    }
    
    public void startGraphic( View v ) {
    	
    	Intent intent = new Intent( this, JavaScriptTestActivity.class );
    	intent.putExtra("ViewId", v.getId());
    	startActivity(intent);
    	
    }
    
   public void startTable( View v ) {
    	
	   	Intent intent = new Intent( this, TableViewActivity.class );
	   	intent.putExtra("ViewId", v.getId());
	   	startActivity(intent);
   	
    }
   
   public void startReport( View v ) {
   	
	   	Intent intent = new Intent( this, ReportListActivity.class );
	   	intent.putExtra("ViewId", v.getId());
	   	startActivity(intent);
  	
   }
    
}
