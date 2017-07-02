package com.shijie.pojo.zhouji.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.entity.User;
import com.shijie.pojo.zhouji.utils.L;
import com.shijie.pojo.zhouji.utils.ShareUtils;
import com.shijie.pojo.zhouji.utils.StaticClass;
import com.shijie.pojo.zhouji.utils.UtilTools;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/30 21:40
 * 描述:  修改个人信息页
 */

public class ModifyUserActivity extends BaseActivity {

    @Bind(R.id.profile_image)
    CircleImageView profileImage;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.mTextInputLayoutName)
    TextInputLayout mTextInputLayoutName;
    @Bind(R.id.et_desc)
    EditText etDesc;
    @Bind(R.id.mTextInputLayoutDesc)
    TextInputLayout mTextInputLayoutDesc;
    @Bind(R.id.mLinearLayout)
    LinearLayout mLinearLayout;
    @Bind(R.id.btn_update)
    Button btnUpdate;
    @Bind(R.id.btn_out)
    Button btnOut;
    @Bind(R.id.tv_back)
    TextView tvBack;

    private BottomSheetDialog bottomSheetDialog;
    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyuser);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {

        UtilTools.textChangedListenser(etName, mTextInputLayoutName, "用户名不能为空");
        UtilTools.textChangedListenser(etDesc, mTextInputLayoutDesc, "");
        UtilTools.setBgColor(this, mLinearLayout);
        etName.setText(ShareUtils.getString(this, StaticClass.USERNAME, ""));
        etDesc.setText(ShareUtils.getString(this, StaticClass.DESC, ""));
        UtilTools.getImageFromSharePre(this,profileImage);

    }

    @OnClick({R.id.profile_image, R.id.btn_update, R.id.btn_out,R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                showBottomSheetDialog();
                break;
            case R.id.btn_update:
                String name = etName.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(this, R.string.dataNull, Toast.LENGTH_SHORT).show();
                }else {
                    updateUserInfo(name, desc);
                }
                break;
            case R.id.btn_out:
                Logout();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    //退出登录
    private void Logout() {
        User.logOut();
        startActivity(new Intent(ModifyUserActivity.this, LoginActivity.class));
    }

    //更新用户信息
    private void updateUserInfo(String name, String desc) {
        User user = new User();
        user.setUsername(name);
        ShareUtils.putString(this, StaticClass.USERNAME, name);
        if (TextUtils.isEmpty(desc)) {
            desc = getString(R.string.nothingDesc);
            user.setDesc(desc);
            ShareUtils.putString(this, StaticClass.DESC, desc);
        } else {
            user.setDesc(desc);
            ShareUtils.putString(this, StaticClass.DESC, desc);
        }
        BmobUser bmobUser = BmobUser.getCurrentUser();
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(ModifyUserActivity.this, R.string.updateSuccess, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModifyUserActivity.this, R.string.updataFailed, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //弹出底部对话框
    private void showBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_modify, null);
        //相机
        view.findViewById(R.id.ll_action_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCamera();
            }
        });
        //图库
        view.findViewById(R.id.ll_action_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPicture();
            }
        });
        //取消
        view.findViewById(R.id.ll_action_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        bottomSheetDialog.dismiss();

    }

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        bottomSheetDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    break;
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        //设置图片
                        setImageToView(data);
                        //将原先图片删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }

                    }
                    break;
            }

        }
    }


    //裁剪图片
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profileImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存头像
        UtilTools.putImageToSharePre(this,profileImage);
    }

}
