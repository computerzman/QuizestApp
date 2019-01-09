package com.quizest.quizestapp.DialogPackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.quizest.quizestapp.ActivityPackage.MainActivity;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import java.util.Objects;

public class Congratsdialog extends DialogFragment {

    TextView tv_result;
    Button quiz;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      // setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_congrats_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (getActivity() != null && isAdded()) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View in = inflater.inflate(R.layout.layout_congrats_dialog, null);
            tv_result = in.findViewById(R.id.tv_result);
            quiz = in.findViewById(R.id.btn_quiz);
            quiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            tv_result.setText(String.valueOf(Util.TOTAL_POINT));
            builder.setView(in);
        }

        return builder.create();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*get the dialog and set animation with the custom animation created in the anim folder */
     /*   getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;*/
    }


  /*  @Override public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();



        if (dialog != null)
        {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
*/


}




