package ceneax.lib.thirdsdk.function.share;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description: 分享内容 注解类
 * @Date: 2021/2/6 9:55
 * @Author: ceneax
 */
@StringDef({ShareContentType.TEXT, ShareContentType.IMAGE,
        ShareContentType.AUDIO, ShareContentType.VIDEO, ShareContentType.FILE, ShareContentType.URL})
@Retention(RetentionPolicy.SOURCE)
public @interface ShareContentType {

    /**
     * 分享文本
     */
    final String TEXT = "text/plain";

    /**
     * 分享图片
     */
    final String IMAGE = "image/*";

    /**
     * 分享音频
     */
    final String AUDIO = "audio/*";

    /**
     * 分享视频
     */
    final String VIDEO = "video/*";

    /**
     * 分享文件
     */
    final String FILE = "*/*";

    /**
     * 分享Url
     */
    final String URL = "url";

}