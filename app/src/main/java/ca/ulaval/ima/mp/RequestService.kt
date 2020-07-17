package ca.ulaval.ima.mp

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class RequestService constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: RequestService? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RequestService(context).also {
                    INSTANCE = it
                }
            }
    }

    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }


    val url : String = "https://kungry.ca/api/v1/"

    var token: Token? = null;
    var profile: Profile? = null;

    fun flushAuth()
    {
        token = null;
        profile = null;
    }

    fun whenConnected(successListener: Response.Listener<Profile>)
    {
        if (profile == null) {
            getRequest("account/me/", Response.Listener { response ->
                profile = Gson().fromJson(response.getJSONObject("content").toString(), Profile::class.java);
                successListener.onResponse(profile);
            }, Response.Listener { response ->
                println("Fail");
            });
        }
        else
            successListener.onResponse(profile);
    }

    fun postRequest(url: String, obj: JSONObject?, successListener: Response.Listener<JSONObject>, failureListener: Response.Listener<KungryResponse>? = null) {
        val req = object: JsonObjectRequest(Method.POST, this.url + url, obj,
            Response.Listener { response ->
                successListener.onResponse(JSONObject(response.toString()));
            },
            Response.ErrorListener { error ->
                if (error.networkResponse != null && error.networkResponse.data != null && failureListener != null) {
                    failureListener.onResponse(
                        Gson().fromJson(
                            String(error.networkResponse.data),
                            KungryResponse::class.java
                        ) as KungryResponse
                    )
                }
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                var params: MutableMap<String, String>? = HashMap<String, String>();
                if (params == null) params = HashMap()
                if (token != null)
                    params["Authorization"] = token!!.access_token;
                return params
            }
        }

        addToRequestQueue(req);
    }

    fun getRequest(url: String, successListener: Response.Listener<JSONObject>, failureListener: Response.Listener<KungryResponse>? = null) {
        val req = object: JsonObjectRequest(Method.GET, this.url + url, null,
            Response.Listener { response ->
                successListener.onResponse(JSONObject(response.toString()));
            },
            Response.ErrorListener { error ->
                if (error.networkResponse != null && error.networkResponse.data != null && failureListener != null) {
                    failureListener.onResponse(
                        Gson().fromJson(
                            String(error.networkResponse.data),
                            KungryResponse::class.java
                        ) as KungryResponse
                    )
                }
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                var params: MutableMap<String, String>? = HashMap<String, String>();
                if (params == null) params = HashMap()
                if (token != null)
                    params["Authorization"] ="Bearer " + token!!.access_token;
                return params
            }
        }

        addToRequestQueue(req);
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    fun getArrayRequest(url : String, listener: Response.Listener<JSONArray>) {
        val req = JsonObjectRequest(Request.Method.GET, this.url + url, null, Response.Listener { response ->
            listener.onResponse(response.getJSONObject("content").getJSONArray("results"))
        },
            Response.ErrorListener { error ->
                // TODO: Handle error
                println(error)
            })

        addToRequestQueue(req);
    }

    fun getObjectRequest(url : String, listener: Response.Listener<JSONObject>) {
        val req = JsonObjectRequest(Request.Method.GET, this.url + url, null, Response.Listener { response ->
            println(url);
            listener.onResponse(response.getJSONObject("content"))
        },
            Response.ErrorListener { error ->
                // TODO: Handle error
                println(error)
            })

        addToRequestQueue(req)
    }
}