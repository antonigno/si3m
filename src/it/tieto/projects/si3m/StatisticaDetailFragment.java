package it.tieto.projects.si3m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticaDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

//    DummyContent.DummyItem mItem;

    public StatisticaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
//        }
	        String item = getArguments().getString(ARG_ITEM_ID);
	        Intent intent = null;
	        if (item.equals("1")){
	          Log.d("DEBUG", "GRAFICO:"+item);
	          intent = new Intent(getActivity().getApplicationContext(),
	          JavaScriptTestActivity.class);
	        }
	        if (item.equals("2")){
		          Log.d("DEBUG", "TABELLA:"+item);
		          intent = new Intent(getActivity().getApplicationContext(),
		          TableViewActivity.class);
		        }
	        if (item.equals("3")){
	        	Log.d("DEBUG", "REPORT:"+item);
	        	intent = new Intent(getActivity().getApplicationContext(),
	                ReportListActivity.class);
	        }
	        startActivity(intent);
        }
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistica_detail, container, false);
//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.statistica_detail)).setText(mItem.content);
//        }
        return rootView;
    }
}
