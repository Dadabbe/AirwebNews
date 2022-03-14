package fr.airweb.news.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.airweb.news.R


class TitleFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.title_fragment, container, false)

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        view.findViewById<View>(R.id.contact_button).setOnClickListener {
            transaction?.replace(R.id.mother_layout, ContactFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        return view
    }
}