package com.shijie.pojo.zhouji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.entity.User;
import com.shijie.pojo.zhouji.utils.ShareUtils;
import com.shijie.pojo.zhouji.utils.UtilTools;
import com.shijie.pojo.zhouji.view.CustomDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/24 17:16
 * 描述: 登录页面
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.mTextInputLayoutName)
    TextInputLayout mTextInputLayoutName;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.mTextInputLayoutPass)
    TextInputLayout mTextInputLayoutPass;
    @Bind(R.id.save_pasword)
    CheckBox savePasword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    private  CustomDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        //一开始登录的初始状态
        boolean isSave = ShareUtils.getBoolean(this,"savepassword",false);
        savePasword.setChecked(isSave);

        //之后登录进行判断
        if (isSave){
            String name = ShareUtils.getString(this,"username","");
            String password = ShareUtils.getString(this,"password","");
            etName.setText(name);
            etPassword.setText(password);
        }
        //判断文本框输入是否为空
        UtilTools.textChangedListenser(etName,mTextInputLayoutName,"用户名输入不能为空");
        UtilTools.textChangedListenser(etPassword,mTextInputLayoutPass,"密码输入不能为空");

        //对话框
        dialog = new CustomDialog(this,150,150,R.layout.dialog_loding,R.style.Theme_Dialog, Gravity.CENTER);

    }


    @OnClick({ R.id.btn_login, R.id.btn_register, R.id.tv_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String name= etName.getText().toString().trim();
                String password = etPassword.getText().toString().toString();
                signUp(name,password);
                break;
            case R.id.btn_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;
        }
    }

    //登录验证
    private void signUp(String name, String password) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "请正确输入用户名或密码", Toast.LENGTH_SHORT).show();
        }else{
            dialog.show();
            //登录验证
            User user = new User();
            user.setUsername(name);
            user.setPassword(password);
            user.login(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e==null){
                        dialog.dismiss();
                        if (user.getEmailVerified()){
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "请去您的邮箱进行验证", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //默认保存用户名和密码
        ShareUtils.putBoolean(this,"savepassword",savePasword.isChecked());
        if (savePasword.isChecked()){
            ShareUtils.putString(this,"username",etName.getText().toString().trim());
            ShareUtils.putString(this,"password",etPassword.getText().toString().trim());
        }else {
            ShareUtils.deleteOne(this,"username");
            ShareUtils.deleteOne(this,"password");
        }

    }
}
