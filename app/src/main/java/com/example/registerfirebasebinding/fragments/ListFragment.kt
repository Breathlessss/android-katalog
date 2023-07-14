package com.example.registerfirebasebinding.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.registerfirebasebinding.Main
import com.example.registerfirebasebinding.R
import com.example.registerfirebasebinding.applistner
import com.example.registerfirebasebinding.apps
import com.example.registerfirebasebinding.databinding.FragmentListBinding
import com.example.registerfirebasebinding.databinding.FragmentProfileBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


@Suppress("DEPRECATION")
class ListFragment : Fragment(), View.OnClickListener {

    private var fragmentBinding: FragmentListBinding? = null
    private val mBinding get() = fragmentBinding!!

    private val one: TextView?
    get() = view?.findViewById(R.id.one)

    private val two: TextView?
        get() = view?.findViewById(R.id.two)

    private val three: TextView?
        get() = view?.findViewById(R.id.three)

    private val four: TextView?
        get() = view?.findViewById(R.id.four)

    private val five: TextView?
        get() = view?.findViewById(R.id.five)

    private lateinit var filmListener: applistner

    lateinit private var toolbar: MaterialToolbar

    override fun onAttach(context: Context) { // задйствуется интерфейс
        super.onAttach(context)
        if (context is applistner) {
            filmListener = context
        } else {
            throw RuntimeException("выберите фильм")
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.list_menu,menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // для взаимодействия с компонентами
        super.onViewCreated(view, savedInstanceState)
        var list = listOf<View>(
            view.findViewById(R.id.conteiner1),
            view.findViewById(R.id.conteiner2),
            view.findViewById(R.id.conteiner3),
            view.findViewById(R.id.conteiner4),
            view.findViewById(R.id.conteiner5),
        )
        list.forEach { //добавления обработчика событий для каждого вью
            it.setOnClickListener(this)
        }

        FirebaseFirestore.getInstance().collection("apps").document("document").get()
            .addOnSuccessListener { //обращение к колlекции
                val data = it.toObject(apps::class.java)
//            val txt = v.findViewById<TextView>(R.id.one)
                if (data != null) {
                    one!!.text = data.one
                    two!!.text = data.two
                    three!!.text = data.three
                    four!!.text = data.four
                    five!!.text = data.five
                }
            }

        glide("one", mBinding.img)
        glide("two", mBinding.img2)
        glide("three", mBinding.img3)
        glide("four", mBinding.img4)
        glide("five", mBinding.img5)

    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.profile_item -> {
//                Main.navController.navigate(R.id.list_to_profile)
//                return true
//
//            } R.id.adnmin_chat -> {
//            Main.navController.navigate(R.id.list_to_chat)
//            return true
//
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)

    }

    fun glide (id: String, imageView: ImageView ) {
        val storegeImg = Firebase.storage.reference
        storegeImg.child("img/${id}.jpg").downloadUrl.addOnSuccessListener { // скачка фото
            Glide.with(this).load(it) // загрузка изображения в imageview
                .fitCenter() // маштабирование
                .into(imageView!!) //загрузка фотки в imageview
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        inflater.inflate(R.layout.fragment_list, container, false)

        fragmentBinding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = mBinding.root
        return root
    }

    override fun onClick(v: View?) {
        v?.let {
            filmListener.appselected(it.id) //it заменяется на айди элемента из списка
        }
    }


}