package com.foxberry.voca;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.foxberry.voca.dto.TranslateUnit;
import com.foxberry.voca.dto.TranslateWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AddWordDialogFragment extends DialogFragment {
    public static final String TAG = "AddWordDialogFragment";
    DatabaseHelper databaseHelper;
    static Callback callback;
    private List<String> synonyms = new ArrayList<>();
    SynonymsListAdapter adapter;
    EditText word;
    Button buttonAdd;
    final ApiService.Api api = ApiService.getApiService();


    public AddWordDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(getActivity());
        View view = inflater.inflate(R.layout.dial_frag_add_word, container, false);
        callback = getArguments().getParcelable("callback");
        word = (EditText) view.findViewById(R.id.word);
        word.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    translateWord(textView.getText().toString());
                }
                return false;
            }
        });
        final ListView synonymsList = (ListView) view.findViewById(R.id.list_synonyms);
        adapter = new SynonymsListAdapter();
        synonymsList.setAdapter(adapter);
        synonymsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    replaceSynonyms(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        buttonAdd = (Button) view.findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateUnit translateUnit = new TranslateUnit(0, word.getText().toString(), synonyms.get(0));
                databaseHelper.addWordPair(translateUnit);
                callback.updateList();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void translateWord(String word) {
        Call<TranslateWrapper> call = api.getTranslateWrapper(Constants.YANDEX_TRANSLATE_KEY,
                getActivity().getSharedPreferences(getString(R.string.voca_shared_pref),Context.MODE_PRIVATE).getString(getString(R.string.current_language), Constants.languages[0]), word);
        call.enqueue(new retrofit2.Callback<TranslateWrapper>() {
            @Override
            public void onResponse(Call<TranslateWrapper> call, Response<TranslateWrapper> response) {
                if (response.body().getDef()!=null) {
                    synonyms = response.body().getDef().getTranslates();
                    Log.i(TAG, "onResponce " + synonyms.size());
                    adapter.notifyDataSetChanged();
                    buttonAdd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<TranslateWrapper> call, Throwable t) {
                Log.i(TAG, "onFailure " + t.getMessage()
                        + " getLocalizedMessage " + t.getLocalizedMessage()
                        + " getStackTrace " + t.getStackTrace());

            }
        });
    }

    private void replaceSynonyms(int position) {
        int synonymPosition = synonyms.indexOf(adapter.getItem(position));
        String tmp = synonyms.get(synonymPosition);
        synonyms.set(synonymPosition, synonyms.get(0));
        synonyms.set(0, tmp);

    }

    private class SynonymsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return synonyms.size();
        }

        @Override
        public String getItem(int position) {
            return synonyms.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e(getClass().getSimpleName(), getItem(position));
            View view = convertView;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.li_synonym, null);
            }
            TextView synonym = (TextView) view.findViewById(R.id.text1);
            synonym.setText(getItem(position));
            Log.e(getClass().getSimpleName(), getItem(position));
            return view;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Window window = getDialog().getWindow();
        window.setLayout(width * 2 / 3, height * 2 / 3);
        window.setGravity(Gravity.CENTER);
    }
}
