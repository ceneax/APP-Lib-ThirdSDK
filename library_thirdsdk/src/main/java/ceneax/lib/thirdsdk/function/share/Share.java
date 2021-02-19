package ceneax.lib.thirdsdk.function.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import ceneax.lib.thirdsdk.ThirdSDK;
import ceneax.lib.thirdsdk.util.BitmapUtil;

/**
 * @Description: 分享 主类
 * @Date: 2021/2/6 10:01
 * @Author: ceneax
 */
public class Share {

    private static final String TAG = "Share";

    private Builder builder;

    private Share(Builder builder) {
        this.builder = builder;
    }

    /**
     * 调用分享
     */
    public void share(ShareToEnum shareToEnum) {
        switch (shareToEnum) {
            case ShareBySystem:
                shareBySystem();
                break;
            case ShareByQQ:
                shareByQQ();
                break;
            case ShareByQZone:
                shareByQzone();
                break;
            case ShareByWechat:
                shareByWechat(0);
                break;
            case ShareByWechatTimeline:
                shareByWechat(1);
                break;
        }
    }

    /**
     * 调用QQ分享
     */
    private void shareByQQ() {
        if (!checkShareParam()) {
            return;
        }

        final Bundle bundle = new Bundle();

        switch (builder.contentType) {
            case ShareContentType.URL:
                bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, builder.url);
                break;
            default:
                bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, builder.shareFileUri.toString());
                break;
        }

        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, builder.title);
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, builder.contentText);

        if (ThirdSDK.getTencent() == null) {
            return;
        }

        // 回调暂时不处理
        ThirdSDK.getTencent().shareToQQ(builder.activity, bundle, new IUiListener() {
            @Override
            public void onComplete(Object o) { }

            @Override
            public void onError(UiError uiError) { }

            @Override
            public void onCancel() { }

            @Override
            public void onWarning(int i) { }
        });
    }
    /**
     * 调用QQ空间分享
     */
    private void shareByQzone() {
        if (!checkShareParam()) {
            return;
        }

        final Bundle bundle = new Bundle();

        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, builder.title);
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, builder.contentText);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, builder.url);
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add(builder.shareFileUri == null ? "" : builder.shareFileUri.toString());
        bundle.putStringArrayList(QQShare.SHARE_TO_QQ_IMAGE_URL, imgList);

        if (ThirdSDK.getTencent() == null) {
            return;
        }

        // 回调暂时不处理
        ThirdSDK.getTencent().shareToQzone(builder.activity, bundle, new IUiListener() {
            @Override
            public void onComplete(Object o) { }

            @Override
            public void onError(UiError uiError) { }

            @Override
            public void onCancel() { }

            @Override
            public void onWarning(int i) { }
        });
    }

    /**
     * 调用微信分享，默认为分享到微信好友
     * @param type 0: 分享到好友 1: 分享到朋友圈
     */
    private void shareByWechat(int type) {
        if (!checkShareParam()) {
            return;
        }

        // 初始化 WXMediaMessage 对象
        WXMediaMessage wxMediaMessage = new WXMediaMessage();

        switch (builder.contentType) {
            case ShareContentType.URL:
                wxMediaMessage.mediaObject = new WXWebpageObject(builder.url);
                wxMediaMessage.title = builder.title;
                wxMediaMessage.description = builder.contentText;
                break;
            default:
                Bitmap bitmap = BitmapFactory.decodeFile(builder.shareFileUri.getPath());
                wxMediaMessage.mediaObject = new WXImageObject(bitmap);
                // 设置缩略图
                Bitmap thumBmp = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                bitmap.recycle();
                // 缩略图的二进制数据, 限制内容大小不超过 32KB
                wxMediaMessage.thumbData = BitmapUtil.bitmap2ByteArray(thumBmp);
                break;
        }

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // 对应该请求的事务 ID，通常由 Req 发起，回复 Resp 时应填入对应事务 ID
        req.transaction = "share";
        req.message = wxMediaMessage;
        // 分享到对话: SendMessageToWX.Req.WXSceneSession
        // 分享到朋友圈: SendMessageToWX.Req.WXSceneTimeline
        // 分享到收藏: SendMessageToWX.Req.WXSceneFavorite
        req.scene = type == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        if (ThirdSDK.getIWxAPI() == null) {
            return;
        }

        // 分享
        ThirdSDK.getIWxAPI().sendReq(req);
    }

    /**
     * 调用系统分享
     */
    private void shareBySystem() {
        if (!checkShareParam()) {
            return;
        }

        Intent shareIntent = createShareIntent();

        if (shareIntent == null) {
            return;
        }

        if (builder.title == null) {
            builder.title = "";
        }

        if (builder.forcedUseSystemChooser) {
            shareIntent = Intent.createChooser(shareIntent, builder.title);
        }

        if (shareIntent.resolveActivity(builder.activity.getPackageManager()) != null) {
            try {
                if (builder.requestCode != -1) {
                    builder.activity.startActivityForResult(shareIntent, builder.requestCode);
                } else {
                    builder.activity.startActivity(shareIntent);
                }
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.addCategory("android.intent.category.DEFAULT");

        if (!TextUtils.isEmpty(builder.componentPackageName) && !TextUtils.isEmpty(builder.componentClassName)){
            ComponentName comp = new ComponentName(builder.componentPackageName, builder.componentClassName);
            shareIntent.setComponent(comp);
        }

        switch (builder.contentType) {
            case ShareContentType.TEXT :
                shareIntent.putExtra(Intent.EXTRA_TEXT, builder.contentText);
                shareIntent.setType("text/plain");
                break;
            case ShareContentType.IMAGE :
            case ShareContentType.AUDIO :
            case ShareContentType.VIDEO :
            case ShareContentType.FILE:
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addCategory("android.intent.category.DEFAULT");
                shareIntent.setType(builder.contentType);
                shareIntent.putExtra(Intent.EXTRA_STREAM, builder.shareFileUri);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    List<ResolveInfo> resInfoList = builder.activity.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        builder.activity.grantUriPermission(packageName, builder.shareFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }
                break;
            default:
                shareIntent = null;
                break;
        }

        return shareIntent;
    }


    private boolean checkShareParam() {
        if (builder.activity == null) {
            return false;
        }

//        if (TextUtils.isEmpty(builder.contentType)) {
//            return false;
//        }

//        if (ShareContentType.TEXT.equals(builder.contentType)) {
//            if (TextUtils.isEmpty(builder.contentText)) {
//                return false;
//            }
//        } else {
//            if (builder.shareFileUri == null) {
//                return false;
//            }
//        }

        return true;
    }

    public static class Builder {
        private final Activity activity;
        private @ShareContentType String contentType = ShareContentType.TEXT;
        private String title;
        private String componentPackageName;
        private String componentClassName;
        private Uri shareFileUri;
        private String contentText;
        private int requestCode = -1;
        private boolean forcedUseSystemChooser = true;
        private String url;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        /**
         * 设置分享内容类型
         */
        public Builder setContentType(@ShareContentType String contentType) {
            this.contentType = contentType;
            return this;
        }

        /**
         * 设置分享标题
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置分享文件路径
         */
        public Builder setShareFileUri(Uri shareFileUri) {
            this.shareFileUri = shareFileUri;
            return this;
        }

        /**
         * 设置分享文本内容
         */
        public Builder setTextContent(String contentText) {
            this.contentText = contentText;
            return this;
        }

        /**
         * Set Share To Component
         */
        public Builder setShareToComponent(String componentPackageName, String componentClassName) {
            this.componentPackageName = componentPackageName;
            this.componentClassName = componentClassName;
            return this;
        }

        /**
         * 设置 onActivityResult requestCode, 默认为 -1
         */
        public Builder setOnActivityResult (int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * 强制使用系统选择器去分享
         */
        public Builder forcedUseSystemChooser (boolean enable) {
            this.forcedUseSystemChooser = enable;
            return this;
        }

        /**
         * 分享Url
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * 构建
         */
        public Share build() {
            return new Share(this);
        }
    }

}