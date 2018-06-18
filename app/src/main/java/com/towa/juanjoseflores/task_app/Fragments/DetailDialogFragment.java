package com.towa.juanjoseflores.task_app.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.towa.juanjoseflores.task_app.R;

import org.w3c.dom.Text;

public class DetailDialogFragment extends DialogFragment {


        public static DetailDialogFragment newInstance(String LongDesc, String ShortDesc) {
            DetailDialogFragment frag = new DetailDialogFragment();
            Bundle args = new Bundle();
            args.putString("LongDesc", LongDesc);
            args.putString("ShortDesc", ShortDesc);
            frag.setArguments(args);
            return frag;
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String strLongDesc = getArguments().getString("LongDesc");
        String strShortDesc = getArguments().getString("ShortDesc");

        View v = inflater.inflate(R.layout.dialog_fragment_detail,container);
        TextView tvLongDesc = v.findViewById(R.id.tv_longDescriptioin);
        tvLongDesc.setText(strLongDesc);
        TextView tvShortDesc = v.findViewById(R.id.tv_shortDescriptioin);
        tvShortDesc.setText(strShortDesc);
        return v;
    }

}