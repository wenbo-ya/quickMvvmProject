package com.rk.quickmvvmproject.http



import com.rk.quickmvvmproject.utils.Constants
import com.rk.quickmvvmproject.utils.ShareUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitManager private constructor() {

    lateinit var retrofit: Retrofit
    lateinit var retrofitMusic: Retrofit

    companion object {
        val instance: RetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitManager()
        }

    }

    fun init(stringUrl: String) {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder().apply {
                        addHeader(
                            "Authorization", Constants.mSharedPreferences.getString(ShareUtil.TOKEN, "")
                        )
                        //  addHeader("X-ADVAI-KEY", "bc01ecec6758d217")
                        addHeader("AuthorizationType", "1")
                    }
                        .build()
                )
            }

            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(stringUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    }



    /**
     * 动态代理模式，创建请求接口类
     * @param tClass
     * @param <T>
     * @return
    </T> */
    fun <T> createService(tClass: Class<T>): T {

        return retrofit.create(tClass)
    }

    inline fun <reified T> create(): T = createService(T::class.java)




}
