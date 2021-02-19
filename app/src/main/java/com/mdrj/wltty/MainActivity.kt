package com.mdrj.wltty

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import ceneax.lib.thirdsdk.function.login.ILoginCallback
import ceneax.lib.thirdsdk.function.login.Login
import ceneax.lib.thirdsdk.function.share.Share
import ceneax.lib.thirdsdk.function.share.ShareContentType
import ceneax.lib.thirdsdk.function.share.ShareToEnum

class MainActivity : AppCompatActivity() {

    private lateinit var mBtShare: Button
    private lateinit var mBtShareWx: Button
    private lateinit var mBtShareQz: Button
    private lateinit var mBtShareWechatTl: Button

    private lateinit var mBtLoginQQ: Button
    private lateinit var mBtLoginWechat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        bindEvent()
    }

    private fun initView() {
        mBtShare = findViewById(R.id.btShare)
        mBtShareWx = findViewById(R.id.btShareWx)
        mBtShareQz = findViewById(R.id.btShareQz)
        mBtShareWechatTl = findViewById(R.id.btShareWxTl)
        mBtLoginQQ = findViewById(R.id.btLoginQQ)
        mBtLoginWechat = findViewById(R.id.btLoginWechat)
    }

    private fun bindEvent() {
        // 分享QQ
        mBtShare.setOnClickListener {
            Share.Builder(this)
                .setTitle("测试分享")
                .setTextContent("测试分享内容")
                .setUrl("http://www.baidu.com")
                .setContentType(ShareContentType.URL)
                .build()
                .share(ShareToEnum.ShareByQQ)
        }

        mBtShareQz.setOnClickListener {
            Share.Builder(this)
                .setTitle("测试分享")
                .setTextContent("测试分享内容")
                .setUrl("http://www.baidu.com")
                .setShareFileUri(Uri.parse("http://www.baidu.com"))
                .build()
                .share(ShareToEnum.ShareByQZone)
        }

        // 分享微信
        mBtShareWx.setOnClickListener {
            Share.Builder(this)
                .setTitle("测试微信分享")
                .setTextContent("测试微信分享内容")
                .setUrl("http://www.baidu.com")
                .setContentType(ShareContentType.URL)
                .build()
                .share(ShareToEnum.ShareByWechat)
        }

        mBtShareWechatTl.setOnClickListener {
            Share.Builder(this)
                .setTitle("测试微信分享")
                .setTextContent("测试微信分享内容")
                .setUrl("http://www.baidu.com")
                .setContentType(ShareContentType.URL)
                .build()
                .share(ShareToEnum.ShareByWechatTimeline)
        }

        // QQ登录
        mBtLoginQQ.setOnClickListener {
            Login.QQ(this) {}
        }

        // 微信登录
        mBtLoginWechat.setOnClickListener {
            Login.wechat(this)
        }
    }

}