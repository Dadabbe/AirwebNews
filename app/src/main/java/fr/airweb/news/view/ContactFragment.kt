package fr.airweb.news.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.airweb.news.R
import android.content.Intent
import android.net.Uri
import java.util.*


class ContactFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.contact_fragment, container, false)
        view.findViewById<View>(R.id.return_button).setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.mother_layout, MainFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        view.findViewById<View>(R.id.addressTextView).setOnClickListener {
            val uri: String =
                java.lang.String.format(Locale.FRENCH, "geo:0,0?q=1 Rue Royale, Bureaux de Colline, 92210 Saint Cloud")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            requireContext().startActivity(intent)
        }

        view.findViewById<View>(R.id.mailTextView).setOnClickListener {
            val i = Intent(Intent.ACTION_SENDTO)
            i.data = Uri.parse("mailto:contact@airweb.fr")
            i.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Support")
            startActivity(Intent.createChooser(i, "Send feedback"))
        }

        view.findViewById<View>(R.id.phoneTextView).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:0176616510")
            startActivity(intent)
        }

        return view
    }
}