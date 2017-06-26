package com.shijie.pojo.zhouji.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.entity.User;
import com.shijie.pojo.zhouji.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/25 10:36
 * 描述: 修改密码页
 */

public class ForgetPasswordActivity extends BaseActivity {


    @Bind(R.id.et_old_password)
    EditText etOldPassword;
    @Bind(R.id.mTextInputLayoutOld)
    TextInputLayout mTextInputLayoutOld;
    @Bind(R.id.et_new_password1)
    EditText etNewPassword1;
    @Bind(R.id.mTextInputLayoutNew)
    TextInputLayout mTextInputLayoutNew;
    @Bind(R.id.et_new_password2)
    EditText etNewPassword2;
    @Bind(R.id.mTextInputLayoutReNew)
    TextInputLayout mTextInputLayoutReNew;
    @Bind(R.id.btn_update_password)
    Button btnUpdatePassword;
    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.mTextInputLayoutEmail)
    TextInputLayout mTextInputLayoutEmail;
    @Bind(R.id.btn_set_email)
    Button btnSetEmail;
    @Bind(R.id.tv_back)
    TextView tvBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        UtilTools.textChangedListenser(etOldPassword, mTextInputLayoutOld, "密码为空");
        UtilTools.textChangedListenser(etNewPassword1, mTextInputLayoutNew, "密码为空");
        UtilTools.textChangedListenser(etNewPassword2, mTextInputLayoutReNew, "密码为空");
        UtilTools.textChangedListenser(etEmail, mTextInputLayoutEmail, "邮箱地址为空");
    }

    @OnClick({R.id.btn_update_password, R.id.btn_set_email,R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_update_password:
                String oldpassword = etOldPassword.getText().toString().trim();
                String newpassword1 = etNewPassword1.getText().toString().trim();
                String newpassword2 = etNewPassword2.getText().toString().trim();
                modifyPassword(oldpassword, newpassword1, newpassword2);
                break;
            case R.id.btn_set_email:
                String email = etEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(this, "请输入正确的邮箱号", Toast.LENGTH_SHORT).show();
                }else {
                    modifyPasswordByEmail(email);
                }
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    private void modifyPassword(String oldpassword, String newpassword1, String newpassword2) {
        if (newpassword1.equals(newpassword2)) {
            //修改密码
            User.updateCurrentUserPassword(oldpassword, newpassword1, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(ForgetPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, "修改密码失败，请检查输入是否有误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "新密码输入不一致", Toast.LENGTH_SHORT).show();
        }
    }

    private void modifyPasswordByEmail(String email) {
        User.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(ForgetPasswordActivity.this, "邮件发送成功，注意查收", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "邮件发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
