package it.tieto.projects.si3m;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GraphicChooser extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    public GraphicChooser() {
    	
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.graphic_chooser, container, false);

        return rootView;
        
    }
    
}
