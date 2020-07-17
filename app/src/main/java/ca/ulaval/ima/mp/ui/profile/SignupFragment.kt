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

class SignupFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private var registerButton: Button? = null;
    private var signinText: TextView? = null;

    private var firstNameField: EditText? = null;
    private var lastNameField: EditText? = null;
    private var emailField: EditText? = null;
    private var passwordField: EditText? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_signup, container, false)

        signinText = root.findViewById<TextView>(R.id.signinText);
        registerButton = root.findViewById<Button>(R.id.registerButton);

        firstNameField = root.findViewById<EditText>(R.id.firstNameField);
        lastNameField = root.findViewById<EditText>(R.id.lastNameField);
        emailField = root.findViewById<EditText>(R.id.emailField);
        passwordField = root.findViewById<EditText>(R.id.passwordField);

        RequestService.getInstance(context as Context).whenConnected(Response.Listener{ response ->
            val ft = activity!!.supportFragmentManager!!.beginTransaction();

            ft.replace(R.id.fragment_containers, ProfileFragment()).addToBackStack(null).commit();
        })

        signinText?.setOnClickListener {
            val ft = activity!!.supportFragmentManager!!.beginTransaction();

            ft.replace(R.id.fragment_containers, SigninFragment()).addToBackStack(null).commit();
        }

        registerButton?.setOnClickListener {

        var registerRequest = RegisterRequest(resources.getString(R.string.clientID), resources.getString(R.string.clientSecret),
            firstNameField!!.text.toString(), lastNameField!!.text.toString(), emailField!!.text.toString(), passwordField!!.text.toString())

            println(Gson().toJson(registerRequest));

            RequestService.getInstance(context as Context)
                .postRequest("account/", JSONObject(Gson().toJson(registerRequest)) , Response.Listener { response ->
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