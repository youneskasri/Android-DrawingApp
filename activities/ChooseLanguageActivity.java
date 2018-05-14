package gl2.kasri.younes.paintapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import gl2.kasri.younes.paintapplication.Dev;
import gl2.kasri.younes.paintapplication.R;



abstract class ChooseLanguageActivity extends AppCompatActivity {
    protected ListView myDrawerList;


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

    public void showParametersDialog(){
        final AlertDialog dialog = showCustomDialog(R.layout.dialog_params);

        final Button okButton = dialog.findViewById(R.id.validateButton);
        final EditText nbOfAttemptsTxt = dialog.findViewById(R.id.nbOfAttempts);
        final EditText startingLevelTxt = dialog.findViewById(R.id.startingLevel);
        final EditText startingNumberTxt = dialog.findViewById(R.id.startingNumber);
        if (okButton != null) {
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dev.NUMBER_OF_ATTEMPTS = getIntegerFromTxt(nbOfAttemptsTxt);
                    Dev.STARTING_LEVEL = getIntegerFromTxt(startingLevelTxt);
                    Dev.STARTING_NUMBER = getIntegerFromTxt(startingNumberTxt);

                    safeDismiss(dialog);

                    if (!ChooseLanguageActivity.this.getClass().getSimpleName().equals("GameMainActivity")){
                        showToast(getString(R.string.modif_params_done));
                    }
                }
            });
        }
    }

    public void showChooseLanguageDiaog(){
        final AlertDialog dialog = showCustomDialog(R.layout.diaog_language);

        final TextView chooseArabic = dialog.findViewById(R.id.chooseArabic);
        final TextView chooseFrench = dialog.findViewById(R.id.chooseFrench);
        final TextView chooseEnglish = dialog.findViewById(R.id.chooseEnglish);

        chooseArabic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setLocale("AR");
            }
        });

        chooseFrench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("FR");
            }
        });

        chooseEnglish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setLocale("EN");
            }
        });

    }

    public void showAboutUsDialog(){
        final AlertDialog dialog = showCustomDialog(R.layout.dialog_about);

        final Button okButton = dialog.findViewById(R.id.validateButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safeDismiss(dialog);
            }
        });
    }


    private int getIntegerFromTxt(EditText startingNumberTxt) {
        try {
            String str;
            str = startingNumberTxt.getText().toString();
            return Integer.parseInt(str);
        }catch (Exception e){
            e.printStackTrace();
            return 2;
        }
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void addDrawerItems() {

        String[] osArray = { getString(R.string.LANG) , getString(R.string.config_parameters), getString(R.string.INFO)};

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        myDrawerList.setAdapter(myAdapter);
        myDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Dev.LANG_POS){
                    showChooseLanguageDiaog();
                } else if (position == Dev.CONFIG_PARAMS_POS){
                    showParametersDialog();
                } else if (position == Dev.INFO_POS){
                    showAboutUsDialog();
                }
            }
        });
    }

    public AlertDialog showCustomDialog(int layout) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(layout, null);
        mBuilder.setView(mView);
        AlertDialog dialog = null;
        try {
            dialog = mBuilder.create();
            dialog.show();
        } catch(RuntimeException e){
            e.printStackTrace();
        }
        return dialog;
    }

    public void safeDismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing() && !isFinishing()) {
            try {
                dialog.dismiss();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

}
