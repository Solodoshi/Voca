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

public class LangSettingsFragment extends Fragment {
    private FragmentLangSetBinding binding;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lang_set, container, false);
        View root = binding.getRoot();
        binding.languagePair.setAdapter(new LanguageSettingsAdapter());
        binding.languagePair.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("LanguagesFragment", "new language is " + Constants.languages[i]);
                sharedPreferences = getActivity().getSharedPreferences(getString(R.string.voca_shared_pref), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.current_language), Constants.languages[i]);
                editor.apply();
                ((VocaAcivity) getActivity()).changeLanguage(Constants.languages[i]);
            }
        });
        return root;
    }

    private class LanguageSettingsAdapter extends BaseAdapter {
        ViewHolder viewHolder;

        @Override
        public int getCount() {
            return Constants.languages.length;
        }

        @Override
        public String getItem(int i) {
            return Constants.languages[i];
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
            viewHolder.langPair.setText(Constants.languages[position]);
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
