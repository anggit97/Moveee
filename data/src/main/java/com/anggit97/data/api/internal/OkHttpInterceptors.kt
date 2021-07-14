/*
 * Copyright 2021 SOUP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anggit97.data.api.internal

import com.anggit97.core.util.LangUtil
import com.anggit97.data.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor


object OkHttpInterceptors {

    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val HEADER_CACHE_MAX_AGE = "public, max-age=${5 * 60}" // 5 minutes

    private const val HEADER_USE_CACHE_PREFIX = "Use-Cache"
    const val HEADER_USE_CACHE = "$HEADER_USE_CACHE_PREFIX: "

    private const val QUERY_PARAM_API_KEY = "api_key"

    private const val QUERY_PARAM_LANG_KEY = "language"
    private const val LANG_ID = "id"
    private const val LANG_EN = "en"

    private fun Request.useCache(): Boolean {
        return header(HEADER_USE_CACHE_PREFIX) != null
    }

    fun createOkHttpInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    fun createOkHttpNetworkInterceptor() = Interceptor {
        var request = it.request()
        val useCache = request.useCache()

        request = appendApiKeyToQueryParam(request)

        request = appendLangToQueryParam(request)

        it.proceed(request).apply {
            if (useCache) {
                newBuilder()
                    .header(HEADER_CACHE_CONTROL, HEADER_CACHE_MAX_AGE)
                    .removeHeader(HEADER_USE_CACHE_PREFIX)
                    .build()
            }
        }
    }

    private fun appendLangToQueryParam(request: Request): Request {
        val selectedSystemLang = LangUtil.getLanguageCountryCode().lowercase()
        val url: HttpUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_PARAM_LANG_KEY, selectedSystemLang).build()
        return request.newBuilder().url(url).build()
    }

    private fun appendApiKeyToQueryParam(request: Request): Request {
        val url: HttpUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_PARAM_API_KEY, BuildConfig.API_KEY_MOVIE).build()
        return request.newBuilder().url(url).build()
    }
}
