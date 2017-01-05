package com.foxberry.voca;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.foxberry.voca.dto.TranslateUnit;

import java.util.List;

public class VocaTabFragment extends Fragment {
    DatabaseHelper databaseHelper;
    ListView listView;
    List<TranslateUnit> translateUnits;
    VocaTabAdapter adapter;
    Callback callback = new ListChangeCallback();

    public VocaTabFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(getActivity());
        translateUnits = databaseHelper.getAllWordPairs();
        View view = inflater.inflate(R.layout.frag_voca_tab, container,false);
        listView = (ListView) view.findViewById(R.id.list_voca);
        adapter = new VocaTabAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteWordDialog(adapter.getItem(position), "any row");
            }
        });
        return view;
    }

    private class VocaTabAdapter extends BaseAdapter {

        ViewHolder viewHolder;
        @Override
        public int getCount() {
            return translateUnits.size();
        }

        @Override
        public TranslateUnit getItem(int position) {
            return translateUnits.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.li_word_to_add, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.word.setText(translateUnits.get(position).getWord());
            viewHolder.word.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteWordDialog(translateUnits.get(position), "word");
                }
            });
            viewHolder.translate.setText(translateUnits.get(position).getTranslate());
            viewHolder.translate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteWordDialog(translateUnits.get(position), "translate");
                }
            });
            return view;
        }
    }
    private class ViewHolder{
        TextView word;
        TextView translate;
        public ViewHolder(View view){
            word = (TextView) view.findViewById(R.id.column_word);
            translate = (TextView) view.findViewById(R.id.column_translate);
        }
    }
    private void showDeleteWordDialog(TranslateUnit translateUnit, String  flag){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("delete_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = DeleteWordDialogFragment.getInstance(translateUnit, callback, flag);
        newFragment.show(ft, "dialog");
    }
     public class ListChangeCallback implements Callback, Parcelable{

         @Override
         public void updateList() {
             getActivity().runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     translateUnits = databaseHelper.getAllWordPairs();
                     adapter.notifyDataSetChanged();
                 }
             });
         }
         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel dest, int flags) {

         }
     }
}
