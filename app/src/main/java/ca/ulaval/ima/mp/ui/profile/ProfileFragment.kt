package ca.ulaval.ima.mp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.RequestService
import ca.ulaval.ima.mp.Token
import com.android.volley.Response
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_signin.*
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private var disconnectButton: Button? = null;

    private var nameField: TextView? = null;
    private var emailField: TextView? = null;
    private var reviewsField: TextView? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        disconnectButton = root.findViewById<Button>(R.id.disconnectButton);

        nameField = root.findViewById<TextView>(R.id.nameText);
        emailField = root.findViewById<TextView>(R.id.emailText);
        reviewsField = root.findViewById<TextView>(R.id.reviewsText);

        RequestService.getInstance(context as Context).whenConnected(Response.Listener{ response ->
            nameField!!.setText(response.last_name + " " + response.first_name);
            emailField!!.setText(response.email);
            reviewsField!!.setText("" + response.total_review_count);
        })

        disconnectButton?.setOnClickListener {

            RequestService.getInstance(context as Context).flushAuth();

            val ft = activity!!.supportFragmentManager!!.beginTransaction();
            ft.replace(R.id.fragment_containers, SigninFragment()).addToBackStack(null).commit();
        }

        return root
    }
}