package com.example.nixo.wuanlogin.ui

import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.nixo.wuanlogin.Bean.LoginRequestBean
import com.example.nixo.wuanlogin.MainActivity
import com.example.nixo.wuanlogin.R
import com.example.nixo.wuanlogin.Util.L
import com.example.nixo.wuanlogin.Util.RequestUtil
import com.example.nixo.wuanlogin.Util.SharedUtil
import com.example.nixo.wuanlogin.Util.StaticClass
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main2.*
import okhttp3.RequestBody

class Main2Activity : AppCompatActivity() ,View.OnClickListener{

    val l: L = L()
    val sharedUtil: SharedUtil = SharedUtil()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        login_login.setOnClickListener(this)
        login_register.setOnClickListener(this)
    }

    private fun toLogin(accountStr: String, passwordStr: String) {
        var login = LoginRequestBean()
        var route = StringBuilder()
        login.email = accountStr
        login.password = passwordStr
        var gson = Gson()
        route.append(gson.toJson(login))
        var body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route.toString())
        RequestUtil.observable.login(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { loginResultBean ->
                    when (loginResultBean.infoCode) {
                        "500" -> Toast.makeText(this@Main2Activity, "服务器出现错误", Toast.LENGTH_SHORT).show()
                        "200" -> {
                            startActivity(Intent(this@Main2Activity, MainActivity::class.java))
                            sharedUtil.putInt(this@Main2Activity, StaticClass.USER_ID, loginResultBean.user_id)
                            sharedUtil.putInt(this@Main2Activity, StaticClass.GROUP_ID, loginResultBean.group_id)
                        }
                        else -> Toast.makeText(this@Main2Activity, "出现未知错误", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_login -> {
                val accountStr = login_account.text.toString().trim()
                val passwordStr = login_password.text.toString().trim()
                if (!TextUtils.isEmpty(accountStr) and !TextUtils.isEmpty(passwordStr)) {
                    toLogin(accountStr, passwordStr)
                }
                if (TextUtils.isEmpty(accountStr) || TextUtils.isEmpty(passwordStr)) {
                    Toast.makeText(this@Main2Activity, "账号或密码不能为空", Toast.LENGTH_SHORT).show()
                }
            }
//            R.id.login_register -> startActivity(Intent(this@Main2Activity, RegisterActivity::class.java))
        }
    }

       

}
