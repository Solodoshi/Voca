package com.foxberry.voca;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foxberry.voca.databinding.FragmentLangSetBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class LangSettingsFragment extends Fragment {
    private FragmentLangSetBinding binding;
    private SharedPreferences sharedPreferences;
    final ApiService.Api api = ApiService.getApiService();
    private Map<String, ArrayList<String>> langMap = new HashMap();
    private String[] langs = new String[]{};
    LanguageSettingsAdapter adapter;
    LanguageSettingsAdapter adapterTranslate;
    private String currentLanguage;
    private String currentTranslate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lang_set, container, false);
        View root = binding.getRoot();
        adapter = new LanguageSettingsAdapter();
        adapterTranslate = new LanguageSettingsAdapter();
        binding.languagePair.setAdapter(adapter);
        binding.languagePairTranslate.setAdapter(adapterTranslate);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.voca_shared_pref), Context.MODE_PRIVATE);
        binding.languagePair.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentLanguage = adapter.getItem(i);
                Log.i("LanguagesFragment", "new language is " + langs[i]);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.current_language), langs[i]);
                editor.apply();
                ArrayList<String> arrayOfTranslates = langMap.get(langs[i]);
                adapterTranslate.setSource(langMap.get(langs[i]).toArray(new String[]{}));
                Log.i("LanguagesFragment", "translates are " + langMap.get(langs[i]));
                adapterTranslate.notifyDataSetChanged();
                if (!arrayOfTranslates.contains(sharedPreferences.getString(getString(R.string.current_translate), null))) {
                    editor.putString(getString(R.string.current_translate), arrayOfTranslates.get(0));
                    editor.apply();
                }
                ((VocaAcivity) getActivity()).changeLanguage();

            }
        });
        binding.languagePairTranslate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.current_translate), adapterTranslate.getItem(i));
                editor.apply();
                ((VocaAcivity) getActivity()).changeLanguage();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Call<List<String>> call = api.getLanguages(Constants.YANDEX_TRANSLATE_KEY);
        call.enqueue(new retrofit2.Callback<List<String>>() {

            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                for (String languagePair : response.body()) {
                    Log.i("Language Pair ", languagePair);
                }
                for (String langPair : response.body()) {
                    String lang = langPair.substring(0, 2);
                    if (!langMap.containsKey(lang)) {
                        langMap.put(lang, new ArrayList<String>());
                    }
                    langMap.get(lang).add(langPair.substring(3, 5));

                }
                langs = langMap.keySet().toArray(new String[langMap.size()]);
                Log.i("Language Pair Ru", langMap.get("uk").toString());
                adapter.setSource(langs);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    private class LanguageSettingsAdapter extends BaseAdapter {
        ViewHolder viewHolder;
        private String[] adapterSource = new String[]{};

        public void setSource(String[] source) {
            adapterSource = source;
        }

        @Override
        public int getCount() {
            return adapterSource.length;
        }

        @Override
        public String getItem(int i) {
            return adapterSource[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.li_language, null);
                viewHolder = new LangSettingsFragment.ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (LangSettingsFragment.ViewHolder) view.getTag();
            }
            viewHolder.langPair.setText(adapterSource[position]);
            return view;
        }

    }

    private class ViewHolder {
        TextView langPair;

        ViewHolder(View view) {
            langPair = (TextView) view.findViewById(R.id.language);
        }
    }
}
