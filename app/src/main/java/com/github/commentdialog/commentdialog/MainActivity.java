package com.github.commentdialog.commentdialog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements CommentDialogFragment.CommentDialogSendListener{

    private Button mBtShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtShowDialog = findViewById(R.id.bt_show_dialog);

        mBtShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentInputDialog("");
            }
        });


    }

    @Override
    public void sendComment(CommentDialogFragment fragment, String message) {
        Toast.makeText(this, "点击了发送", Toast.LENGTH_SHORT).show();
    }

    private void showCommentInputDialog(String toUser) {
        if (TextUtils.isEmpty(toUser)) {
            toUser = "回复楼主";
        }

        FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag("InputDialogFragment");
        if (fragment != null) {
            // 为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            mFragTransaction.remove(fragment);
        }
        CommentDialogFragment dialogFragment = CommentDialogFragment.newInstance(toUser);
        // 显示一个Fragment并且给该Fragment添加一个Tag，可通过findFragmentByTag找到该Fragment
        dialogFragment.show(mFragTransaction, "InputDialogFragment");
    }

}
