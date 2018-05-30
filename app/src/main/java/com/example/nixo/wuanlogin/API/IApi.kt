package com.example.nixo.wuanlogin.API


import com.example.nixo.wuanlogin.Bean.LoginResultBean

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by Nixo on 25/05/2018.
 */

interface IApi {


    //登陆
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/login")
    fun login(@Body LoginBody: RequestBody): Observable<LoginResultBean>


}
