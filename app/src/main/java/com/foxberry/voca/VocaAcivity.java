package com.foxberry.voca;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.foxberry.voca.databinding.ActivityVocaBinding;

public class VocaAcivity extends AppCompatActivity {
      ActivityVocaBinding binding;
    VocaTabFragment vocaTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_voca);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        vocaTabFragment = new VocaTabFragment();
        final FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add ( R.id.fragmentContainer, vocaTabFragment);
        fragmentTransaction.commit();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.voca_shared_pref), MODE_PRIVATE);
        binding.languageSwitch.setText(sharedPreferences.getString(getString(R.string.current_language), Constants.languages[0]));
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWordDialog();
            }
        });
        binding.languageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                fragmentTransaction1.replace(R.id.fragmentContainer, new LangSettingsFragment());
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voca_acivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAddWordDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = new AddWordDialogFragment(/*vocaTabFragment.callback*/);
        Bundle bundle = new Bundle();
        bundle.putParcelable("callback", vocaTabFragment.callback);
        newFragment.setArguments(bundle);
        newFragment.show(ft, "dialog");
    }

    public void changeLanguage(String languagePair){
        binding.languageSwitch.setText(languagePair);
    }
}
