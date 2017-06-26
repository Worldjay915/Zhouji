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
import com.shijie.pojo.zhouji.utils.ShareUtils;
import com.shijie.pojo.zhouji.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/25 10:51
 * 描述:
 */

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.et_user)
    EditText etUser;
    @Bind(R.id.mTextInputLayoutUser)
    TextInputLayout mTextInputLayoutUser;
    @Bind(R.id.et_desc)
    EditText etDesc;
    @Bind(R.id.mTextInputLayoutDesc)
    TextInputLayout mTextInputLayoutDesc;
    @Bind(R.id.et_password1)
    EditText etPassword1;
    @Bind(R.id.mTextInputLayoutPass)
    TextInputLayout mTextInputLayoutPass;
    @Bind(R.id.et_password2)
    EditText etPassword2;
    @Bind(R.id.mTextInputLayoutRePass)
    TextInputLayout mTextInputLayoutRePass;
    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.mTextInputLayoutReEmail)
    TextInputLayout mTextInputLayoutReEmail;
    @Bind(R.id.btnRegisted)
    Button btnRegisted;
    @Bind(R.id.tv_back)
    TextView tvBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    //初始化控件
    private void initView() {
        UtilTools.textChangedListenser(etUser, mTextInputLayoutUser, "用户名不能为空");
        UtilTools.textChangedListenser(etDesc, mTextInputLayoutDesc, "");
        UtilTools.textChangedListenser(etPassword1, mTextInputLayoutPass, "密码不能为空");
        UtilTools.textChangedListenser(etPassword2, mTextInputLayoutRePass, "确认密码不能为空");
        UtilTools.textChangedListenser(etEmail, mTextInputLayoutReEmail, "邮件不能为空");
    }

    @OnClick({R.id.tv_back, R.id.btnRegisted})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.btnRegisted:
               signIn();
                break;
        }
    }

    private void signIn() {
        String username = etUser.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String password1 = etPassword1.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        //判断两次密码输入是否不一致
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password1)
                ||TextUtils.isEmpty(password2)||TextUtils.isEmpty(email)){
            Toast.makeText(this, "输入信息有误", Toast.LENGTH_SHORT).show();
        }else{
            if (password1.equals(password2)) {
                //判断简介是否为空
                if (TextUtils.isEmpty(desc)) {
                    desc = "这个人比较懒，什么都没有写...";
                    ShareUtils.putString(this, "desc", "desc");
                } else {
                    ShareUtils.putString(this, "desc", "desc");
                }
                //注册
                User user = new User();
                user.setUsername(username);
                user.setDesc(desc);
                user.setPassword(password2);
                user.setEmail(email);
                //利用Bmob api 注册
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this, "您已注册成功！", Toast.LENGTH_SHORT).show();
                            finish();
                            Toast.makeText(RegisterActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
               }
            }
       }
}
