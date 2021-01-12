package es.sublimestudio.kotlinquiz.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.sublimestudio.kotlinquiz.DataHolder
import es.sublimestudio.kotlinquiz.R
import kotlinx.android.synthetic.main.fragment_name.*

class NameFragment : Fragment() {

    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = activity!!.getSharedPreferences(DataHolder.DBName, Context.MODE_PRIVATE)
        getName()

        btnStart.setOnClickListener {
            if (saveName()) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, QuestionsFragment())?.commit()
            }
        }

        btnNewUser.setOnClickListener {
            showViews(false)
        }
    }


    fun saveName(): Boolean {
        val name = username.text.toString()
        if (name.length < 3) {
            username.setError("Nombre de usuario no válido")
            return false
        }

        val edit = sharedPref.edit()
        edit.putString(DataHolder.USERNAME, name)
        return edit.commit()
    }

    fun getName() {
        val currName = sharedPref.getString(DataHolder.USERNAME, null)

        if (currName != null) {
            showViews(true, currName)
        } else {
            showViews(false)
        }
    }

    fun showViews(existUser: Boolean, currName: String = "") {
        if (existUser) {
            welcome.text = "Hola $currName!"
            btnContinue.text = "Continuar como $currName"
            viewNoUser.visibility = View.GONE
            viewUser.visibility = View.VISIBLE
        } else {
            viewUser.visibility = View.GONE
            viewNoUser.visibility = View.VISIBLE
        }
    }

}