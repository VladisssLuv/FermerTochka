package my.project.testretrofit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import my.project.testretrofit.R
import my.project.testretrofit.databinding.FragmentListBinding
import my.project.testretrofit.recycler.ActionListener
import my.project.testretrofit.recycler.ItemRecycler
import my.project.testretrofit.recycler.RecycleAdapter
import my.project.testretrofit.retrofit.RetrofitSource
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.SurfaceHolder
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.project.testretrofit.MainActivity
import okhttp3.Dispatcher

import java.io.IOException

class FragmentList: FragmentBase() {
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: RecycleAdapter
    private val retrofitSource: RetrofitSource = RetrofitSource()

    companion object {
        fun newInstance(): FragmentList {
            return FragmentList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecycleAdapter(object : ActionListener{
            override fun onClick(v: View) {
                val animation = AnimationUtils.loadAnimation(context, R.anim.anim_click)
                v.startAnimation(animation)
                openChat()
            }
        })

        val linearLayoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = linearLayoutManager
        binding.recycler.adapter = adapter
        println("OTKRIILS")
        binding.progressBar.visibility = View.INVISIBLE
    }

}
