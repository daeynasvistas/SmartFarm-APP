package pt.ipg.SmartFarmAPP.UI.Fragment.Dialog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import pt.ipg.SmartFarmAPP.R;

public class AddNodeDialog extends DialogFragment {

        private static final String TAG = "AddNodeDialog";

        public interface OnInputSelected{
            void sendInput(String input);
        }
        public OnInputSelected mOnInputSelected;

        //widgets
        private EditText mInput;
        private TextView mActionOk, mActionCancel;

        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_addnode, container, false);
            mActionOk = view.findViewById(R.id.action_ok);
            mActionCancel = view.findViewById(R.id.action_cancel);
            mInput = view.findViewById(R.id.input);

            mActionCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: closing dialog");
                    getDialog().dismiss();
                }
            });

            mActionOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: capturing input.");

                    String input = mInput.getText().toString();
                    if(!input.equals("")){
                        mOnInputSelected.sendInput(input);
                    }
                    getDialog().dismiss();
                }
            });

            return view;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try{
                mOnInputSelected = (OnInputSelected) getTargetFragment();
            }catch (ClassCastException e){
                Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
            }
        }
    }
