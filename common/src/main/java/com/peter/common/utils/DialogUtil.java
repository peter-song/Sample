package com.peter.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.peter.common.R;

/**
 * Created by songzhongkun on 15/11/2 11:26.
 */
public class DialogUtil {

    private Dialog dialog;

    public Dialog createDialog(Activity context, String title, String msg,
                               final OnButtonListener onButtonListener) {
        return createDialog(context, title, msg, false, onButtonListener);
    }

    public Dialog createDialog(Activity context, String title, String msg,
                               boolean hideCancel, final OnButtonListener onButtonListener) {

        if (context == null || context.isFinishing()) {
            return null;
        }

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        } else if (dialog == null) {
            dialog = new Dialog(context, R.style.Dialog);
        }

        View view = View.inflate(context, R.layout.dialog_multiterm, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        TextView btn_ok = (TextView) view.findViewById(R.id.tv_ok);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        if (hideCancel) {
            tv_cancel.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(title) || title == null) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }

        if (TextUtils.isEmpty(msg) || msg == null) {
            tv_msg.setVisibility(View.GONE);
        } else {
            tv_msg.setText(msg);
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onButtonListener.okButton();
                dialog.cancel();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onButtonListener.cancelButton();
                dialog.cancel();
            }
        });

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();

        return dialog;
    }

    public interface OnButtonListener {
        void okButton();

        void cancelButton();
    }
}
