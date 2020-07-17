package ca.ulaval.ima.mp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ca.ulaval.ima.mp.R

class ContainerFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_container, container, false)

        val ft = activity!!.supportFragmentManager!!.beginTransaction();

        ft.replace(R.id.fragment_containers, SigninFragment()).addToBackStack(null).commit();

        return root
    }
}