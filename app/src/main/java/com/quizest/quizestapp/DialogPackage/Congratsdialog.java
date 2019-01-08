package com.quizest.quizestapp.DialogPackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import java.util.Objects;

public class Congratsdialog extends DialogFragment {

    TextView tv_result;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (getActivity() != null && isAdded()) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View in = inflater.inflate(R.layout.layout_congrats_dialog, null);

            tv_result = in.findViewById(R.id.tv_result);

            tv_result.setText(String.valueOf(Util.TOTAL_POINT));

            builder.setView(in);


        }


        return builder.create();

    }


}
