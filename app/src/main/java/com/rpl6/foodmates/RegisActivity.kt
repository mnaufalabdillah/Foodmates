package com.rpl6.foodmates

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class RegisActivity : AppCompatActivity() {
    private var name: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var c_password: EditText? = null
    private var btn_regist: Button? = null
    private var loading: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loading = findViewById(R.id.loading)
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        c_password = findViewById(R.id.c_password)
        btn_regist = findViewById(R.id.btn_regis)
        btn_regist!!.setOnClickListener(View.OnClickListener { Regist() })
    }

    private fun Regist() {
        loading!!.visibility = View.VISIBLE
        btn_regist!!.visibility = View.GONE
        val name = name!!.text.toString().trim { it <= ' ' }
        val email = email!!.text.toString().trim { it <= ' ' }
        val password = password!!.text.toString().trim { it <= ' ' }
        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL_REGIST,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success == "1") {
                            Toast.makeText(this@RegisActivity, "Register Success!", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this@RegisActivity, "Register Error! $e", Toast.LENGTH_SHORT).show()
                        loading!!.visibility = View.GONE
                        btn_regist!!.visibility = View.VISIBLE
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@RegisActivity, "Register Error! $error", Toast.LENGTH_SHORT).show()
                    loading!!.visibility = View.GONE
                    btn_regist!!.visibility = View.VISIBLE
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["name"] = name
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    companion object {
        private const val URL_REGIST = "https://e2b7b2d8.ngrok.io/regis/register.php"
    }
}