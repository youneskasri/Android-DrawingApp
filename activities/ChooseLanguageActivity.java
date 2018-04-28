package gl2.kasri.younes.paintapplication.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Locale;

import gl2.kasri.younes.paintapplication.Dev;
import gl2.kasri.younes.paintapplication.R;

/**
 * Created by admin on 28/04/2018.
 */

abstract class ChooseLanguageActivity extends AppCompatActivity {
    protected ListView myDrawerList;
    private ArrayAdapter<String> myAdapter;


    public void setLocale(String lang ) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, this.getClass());
        startActivity(refresh);
        finish();

    }

    protected void addDrawerItems() {
        String[] osArray = { getString(R.string.LANG), getString(R.string.AR), getString(R.string.FR), getString(R.string.EN) };
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        myDrawerList.setAdapter(myAdapter);
        myDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Dev.LANG_AR_POS){
                    setLocale("AR");
                } else if (position == Dev.LANG_FR_POS){
                    setLocale("FR");
                } else if (position == Dev.LANG_EN_POS){
                    setLocale("EN");
                }
            }
        });
    }
}
