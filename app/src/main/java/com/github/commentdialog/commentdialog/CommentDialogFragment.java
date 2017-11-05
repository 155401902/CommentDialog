package com.github.commentdialog.commentdialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 输入对话框：评论框
 */
public class CommentDialogFragment extends DialogFragment {

    public static String MESSAGE_HINT = "";
    private CommentDialogSendListener mDialogListener;

    public CommentDialogFragment() {

    }

    private void setDialogListener(CommentDialogSendListener listener) {
        mDialogListener = listener;
    }


    public interface CommentDialogSendListener {
        /**
         * 定义一个与Activity通信的接口，使用该DialogFragment的Activity须实现该接口
         */
        void sendComment(CommentDialogFragment fragment, String message);
    }

    public static CommentDialogFragment newInstance(String message){
        //创建一个带有参数的Fragment实例
        CommentDialogFragment fragment = new CommentDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_HINT, message);
        fragment.setArguments(bundle);//把参数传递给该DialogFragment
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        if (null != window) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_comment_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editComment = (EditText) view.findViewById(R.id.et_comment_dialog_input);
        final LinearLayout send = (LinearLayout) view.findViewById(R.id.ll_comment_dialog_send);

        // 把传递过来的数据设置给EditText
        editComment.setHint(getArguments().getString(MESSAGE_HINT));
        editComment.requestFocus();
        editComment.post(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) getActivity().getSystemService(Context
                        .INPUT_METHOD_SERVICE)).showSoftInput(editComment, 0);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentStr = editComment.getText().toString();
                CommentDialogSendListener listener = (CommentDialogSendListener) getActivity();
                // 对话框与Activity间通信，传递数据给实现了DialogFragmentDataListener接口的Activity
                listener.sendComment(CommentDialogFragment.this, commentStr);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        if (null != window) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

        final Resources res = getResources();
        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        if (titleDividerId > 0) {
            final View titleDivider = getDialog().findViewById(titleDividerId);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(res.getColor(android.R.color.transparent));
            }
        }
    }

}
