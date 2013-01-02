package it.tieto.projects.si3m;

import it.tieto.projects.si3m.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class StatisticaDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistica_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(StatisticaDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(StatisticaDetailFragment.ARG_ITEM_ID));
            StatisticaDetailFragment fragment = new StatisticaDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.statistica_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, StatisticaListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
