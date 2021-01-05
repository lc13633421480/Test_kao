package com.example.test_kao.ui.me;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.test_kao.R;
import com.example.test_kao.app.Constants;
import com.example.test_kao.base.BaseActivity;
import com.example.test_kao.bean.login.TokenBean;
import com.example.test_kao.bean.me.LogOutBean;
import com.example.test_kao.bean.me.UserInfoBean;
import com.example.test_kao.interfaces.me.IMe;
import com.example.test_kao.presenter.me.UserInfoPresenter;
import com.example.test_kao.utils.BitmapUtils;
import com.example.test_kao.utils.GlideEngine;
import com.example.test_kao.utils.SpUtils;
import com.example.test_kao.utils.SystemUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoDetailActivity extends BaseActivity<UserInfoPresenter> implements IMe.View, View.OnClickListener {
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.txt_username)
    TextView txtUsername;
    @BindView(R.id.txt_nickname)
    TextView txtNickname;

    String bucketName = "2002aaa";
    String ossPoint = "http://oss-cn-beijing.aliyuncs.com";

    String key = "LTAI4G68osGeb6ck3m4ogpKA";  //appkey
    String secret = "MNIJ56CV0j5E1zS667tVlZxrbbGbxo";  //密码
    @BindView(R.id.layout_nickname)
    ConstraintLayout layoutNickname;
    @BindView(R.id.txt_birthday)
    TextView txtBirthday;
    @BindView(R.id.txt_input)
    EditText txtInput;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.layout_input)
    ConstraintLayout layoutInput;
    @BindView(R.id.layout_birthday)
    ConstraintLayout layoutBirthday;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.img_arrow_lt)
    ImageView imgArrowlt;
    @BindView(R.id.btn_token)
    Button btnToken;
    private OSS ossClient;
    boolean b = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_userinfodetail;
    }

    @Override
    protected UserInfoPresenter createPrenter() {
        return new UserInfoPresenter(this);
    }


    @Override
    protected void initView() {
        initOss();

        imgAvatar.setOnClickListener(this);
        layoutNickname.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        layoutBirthday.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        imgArrowlt.setOnClickListener(this);
        btnToken.setOnClickListener(this);
        String avatar = SpUtils.getInstance().getString("avatar");
        String username = SpUtils.getInstance().getString("username");
        String nickname = SpUtils.getInstance().getString("nickname");
        int birthday = SpUtils.getInstance().getInt("birthday");
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(this).load(avatar).apply(new RequestOptions().circleCrop()).into(imgAvatar);
        }
        if (!TextUtils.isEmpty(username)) {
            txtUsername.setText(username);
        }
        if (!TextUtils.isEmpty(nickname)) {
            txtNickname.setText(nickname);
        }
        if (!TextUtils.isEmpty(birthday + "")) {
            txtBirthday.setText(birthday + "");
        }
    }

    //初始化OSS
    private void initOss() {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(key, secret, "");
        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。
        ossClient = new OSSClient(getApplicationContext(), ossPoint, credentialProvider);
    }

    @Override
    protected void initData() {
    }

    //跳转到相册
    private void openPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() == 0) return;
                //获取本地图片的选择地址，上传到服务器
                //头像的压缩和二次采样
                //把选中的图片插入到列表
                try {
                    for (int i = 0; i < selectList.size(); i++) {
                        Bitmap scaleBitmp = BitmapUtils.getScaleBitmap(selectList.get(i).getPath(), Constants.HEAD_WIDTH, Constants.HEAD_HEIGHT);
                        //Bitmap转uri
                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), scaleBitmp, null, null));
                        //uri转字符串
                        String path = getRealPathFromUri(this, uri);
                        uploadHead(path);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);

        }
    }

    //uri转字符串的方法
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void uploadHead(String path) {
        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
        PutObjectRequest put = new PutObjectRequest(bucketName, fileName, path);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                //上次进度
                Log.i("oss_upload", currentSize + "/" + totalSize);
                // 进度百分比的计算
                // int p = (int) (currentSize/totalSize*100);
                if (currentSize == totalSize) {
                    //完成
                    String headUrl = request.getUploadFilePath();
                    //
                    Log.i("HeadUrl", headUrl);
                    //request.getUploadFilePath()
                }

            }
        });
        OSSAsyncTask task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                //成功的回调中读取相关的上传文件的信息  生成一个url地址
                String url = ossClient.presignPublicObjectURL(request.getBucketName(), request.getObjectKey());
                //TODO 刷新显示到界面上
                updateHead(url);
                //sp的保存
//                SpUtils.getInstance().setValue("img", url);
                //TODO 调用服务器接口，把url上传到服务器的接口
                Map<String, String> map = new HashMap<>();
                map.put("avatar", url);
                presenter.updateUserInfo(map);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    private void updateHead(String url) {
        imgAvatar.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(imgAvatar).load(url).apply(new RequestOptions().circleCrop()).into(imgAvatar);
            }
        });
    }

    //上传成功后的结果
    @Override
    public void getResult(UserInfoBean userInfoBean) {
        if (userInfoBean.getErrno() == 0) {
            SystemUtils.closeSoftKeyBoard(this);
            layoutInput.setVisibility(View.GONE);
        } else if (userInfoBean.getErrno() == 606) {
            Toast.makeText(this, "无效token,重新登录或更新token", Toast.LENGTH_SHORT).show();
            layoutInput.setVisibility(View.GONE);
            btnToken.setVisibility(View.VISIBLE);
        }
    }

    //退出登录的结果
    @Override
    public void getResult(LogOutBean logOutBean) {
        int errno = logOutBean.getErrno();
        if (errno == 0) {
            showToast("退出成功");
            //清空Sp的值
            SpUtils.getInstance().delete();
            Intent intent = new Intent();
            setResult(89, intent);
            finish();
        }
    }

    //token刷新后的结果
    @Override
    public void getResult(TokenBean tokenBean) {
        String token = tokenBean.getData();
        SpUtils.getInstance().setValue("token",token);
        btnToken.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_avatar:
                openPhoto();
                break;
            case R.id.layout_nickname:
                //打开输入的状态
                showInput();
                b = false;
                break;
            case R.id.btn_save:
                String nickname = txtInput.getText().toString();
                String birthday = txtInput.getText().toString();

                if (b == false) {
                    if (!TextUtils.isEmpty(nickname)) {
                        Map<String, String> map = new HashMap<>();
                        map.put("nickname", nickname);
                        presenter.updateUserInfo(map);
                        txtNickname.setText(nickname);
                    }
                }
                if (b == true) {
                    if (!TextUtils.isEmpty(birthday)) {
                        Map<String, String> map = new HashMap<>();
                        map.put("birthday", birthday);
                        presenter.updateUserInfo(map);
                        txtBirthday.setText(birthday + "");
                    }
                }
                break;
            case R.id.layout_birthday:
                //打开输入的状态
                showInput();
                b = true;
                break;
            case R.id.btn_back:
                presenter.logout();
                showToast("已退出登录");
                break;
            case R.id.img_arrow_lt:
                finish();
                break;
            case R.id.btn_token:
                //获取token
//                SpUtils.getInstance().remove("token");
                presenter.refreshToken();
                break;
        }
    }

    private void showInput() {
        layoutInput.setVisibility(View.VISIBLE);
        txtInput.setFocusable(true);
        SystemUtils.openSoftKeyBoard(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
