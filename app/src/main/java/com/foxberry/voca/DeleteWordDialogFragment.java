package com.foxberry.voca;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foxberry.voca.dto.TranslateUnit;


public class DeleteWordDialogFragment extends DialogFragment {
private static TranslateUnit translateUnit;
    private DatabaseHelper databaseHelper;
    static Callback callback;
    private static String flag;

    static DeleteWordDialogFragment getInstance(TranslateUnit translateUnit, Callback callback, String flag) {
        DeleteWordDialogFragment.callback = callback;
        DeleteWordDialogFragment dialogFragment = new DeleteWordDialogFragment();
        DeleteWordDialogFragment.translateUnit = translateUnit;
        DeleteWordDialogFragment.flag = flag;
        return dialogFragment;
    }

    public DeleteWordDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(getActivity());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_word_dialog, container, false);
        final TextView word = (TextView) view.findViewById(R.id.del_word);
        final TextView translate = (TextView) view.findViewById(R.id.del_translate);
        final TextView flagView = (TextView) view.findViewById(R.id.flag);
        word.setText(translateUnit.getWord());
        translate.setText(translateUnit.getTranslate());
        flagView.setText(flag);
        Button buttonAdd = (Button) view.findViewById(R.id.button_delete);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteWordPair(translateUnit);
                callback.updateList();
                getActivity().getFragmentManager().popBackStack();
            }
        });
        return view;

    }

}
