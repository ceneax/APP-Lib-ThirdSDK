package com.mdrj.wltty

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ceneax.lib.thirdsdk.function.share.Share
import ceneax.lib.thirdsdk.function.share.ShareContentType

class MainActivity : AppCompatActivity() {

    private lateinit var mBtShare: Button
    private lateinit var mBtShareWx: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        bindEvent()
    }

    private fun initView() {
        mBtShare = findViewById(R.id.btShare)
        mBtShareWx = findViewById(R.id.btShareWx)
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
                .shareByQQ()
        }

        // 分享微信
        mBtShareWx.setOnClickListener {
            Share.Builder(this)
                .setTitle("测试微信分享")
                .setTextContent("测试微信分享内容")
                .setUrl("http://www.baidu.com")
                .setContentType(ShareContentType.URL)
                .build()
                .shareByWechat()
        }
    }

}