package ca.ulaval.ima.mp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.RequestService
import ca.ulaval.ima.mp.Token
import com.android.volley.Response
import com.google.gson.Gson
import org.json.JSONObject

class SigninFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private var connectButton: Button? = null;
    private var signupText: TextView? = null;

    private var emailField: EditText? = null;
    private var passwordField: EditText? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_signin, container, false)

        RequestService.getInstance(context as Context).whenConnected(Response.Listener{ response ->
            val ft = activity!!.supportFragmentManager!!.beginTransaction();

            ft.replace(R.id.fragment_containers, ProfileFragment()).addToBackStack(null).commit();
        })

        signupText = root.findViewById<TextView>(R.id.signupText);
        connectButton = root.findViewById<Button>(R.id.connectButton);

        emailField = root.findViewById<EditText>(R.id.emailField);
        passwordField = root.findViewById<EditText>(R.id.passwordField);

        signupText?.setOnClickListener {
            val ft = activity!!.supportFragmentManager!!.beginTransaction();

            ft.replace(R.id.fragment_containers, SignupFragment()).addToBackStack(null).commit();
        }

        connectButton?.setOnClickListener {

            var registerRequest = LoginRequest(resources.getString(R.string.clientID), resources.getString(R.string.clientSecret),
                 emailField!!.text.toString(), passwordField!!.text.toString())

            RequestService.getInstance(context as Context)
                .postRequest("account/login", JSONObject(Gson().toJson(registerRequest)) , Response.Listener { response ->
                    RequestService.getInstance(context!!).token = Gson().fromJson(response.getJSONObject("content").toString(), Token::class.java);

                    RequestService.getInstance(context as Context).whenConnected(Response.Listener{ response ->
                        val ft = activity!!.supportFragmentManager!!.beginTransaction();

                        ft.replace(R.id.fragment_containers, ProfileFragment()).addToBackStack(null).commit();
                    })
                }, Response.Listener { response ->
                    println("Fail");
                });
        }

        return root
    }
}