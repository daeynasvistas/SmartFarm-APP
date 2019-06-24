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
import android.widget.Toast;

import pt.ipg.SmartFarmAPP.R;

public class AddNodeDialog extends DialogFragment {

        private static final String TAG = "AddNodeDialog";

        public interface OnInputSelected{
            void sendInput(String modelo, String mac);
        }
        public OnInputSelected mOnInputSelected;

        //widgets
        private EditText mModel;
        private EditText mMAC;
        private TextView mActionOk, mActionCancel;

        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_addnode, container, false);
            mActionOk = view.findViewById(R.id.action_ok);
            mActionCancel = view.findViewById(R.id.action_cancel);
            mModel = view.findViewById(R.id.model);
            mMAC = view.findViewById(R.id.mac);

            mActionCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: closing dialog");
                    Toast.makeText(getActivity(), "Node NÃO inserido", Toast.LENGTH_SHORT).show();

                    getDialog().dismiss();
                }
            });

            mActionOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: capturing input.");

                    String modelo = mModel.getText().toString();
                    String mac = mModel.getText().toString();

                    if(!modelo.equals("")){
                        mOnInputSelected.sendInput(modelo, mac);
                    }else{
                        Toast.makeText(getActivity(), "Node NÃO inserido. Deve indicar o modelo", Toast.LENGTH_SHORT).show();
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
