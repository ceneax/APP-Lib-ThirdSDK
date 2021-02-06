package ceneax.app.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import ceneax.lib.thirdsdk.ThirdSDK
import ceneax.lib.thirdsdk.function.share.Share
import ceneax.lib.thirdsdk.function.share.ShareContentType
import ceneax.lib.thirdsdk.module.QQShareModule
import ceneax.lib.thirdsdk.module.WechatShareModule

class MainActivity : AppCompatActivity() {

    private lateinit var mBtShare: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        bindEvent()
    }

    private fun initView() {
        mBtShare = findViewById(R.id.btShare)
    }

    private fun bindEvent() {
        // 分享
        mBtShare.setOnClickListener {
            Share.Builder(this)
                .setTitle("测试分享")
                .setTextContent("测试分享内容")
                .setUrl("http://www.baidu.com")
                .setContentType(ShareContentType.URL)
                .build()
                .shareByQQ()
        }
    }

}