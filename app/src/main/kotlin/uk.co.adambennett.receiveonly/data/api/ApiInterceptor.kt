/*
 *  Copyright 2017 Adam Bennett.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.adambennett.receiveonly.data.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException
import java.util.*

class ApiInterceptor : Interceptor {

    private val TAG = ApiInterceptor::class.java.simpleName

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.nanoTime()

        var requestLog = String.format(
                "Sending request %s with headers %s%n%s",
                request.url(),
                chain.connection(),
                request.headers())

        if (request.method().compareTo("post", ignoreCase = true) == 0) {
            requestLog = "\n" + requestLog + "\n" + requestBodyToString(request.body())
        }

        Log.d(TAG, "Request:" + "\n" + requestLog)

        val response = chain.proceed(request)
        val endTime = System.nanoTime()

        val responseLog = String.format(
                Locale.ENGLISH,
                "Received response from %s in %.1fms%n%s",
                response.request().url(),
                (endTime - startTime) / 1e6,
                response.headers())

        val bodyString = response.body().string()
        if (response.code() == 200) {
            Log.d(TAG, "Response:" + "\n" + responseLog + "\n" + bodyString)
        } else {
            Log.e(TAG, "Response:" + "\n" + responseLog + "\n" + bodyString)
        }

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build()
    }

    private fun requestBodyToString(request: RequestBody?): String {
        val buffer = Buffer()
        try {
            if (request != null) {
                request.writeTo(buffer)
                return buffer.readUtf8()
            } else {
                return ""
            }
        } catch (e: IOException) {
            return "IOException reading request body"
        } finally {
            buffer.close()
        }
    }

}