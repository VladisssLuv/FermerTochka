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
    private lateinit var cameraSource:  CameraSource

    private val CAMERA_PERMISSION_REQUEST_CODE = 1001

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

        if(!safeCheckUserValid()) {
            openNotValidUser()
        }

        adapter = RecycleAdapter(object : ActionListener{
            override fun onClick(v: View) {
                val animation = AnimationUtils.loadAnimation(context, R.anim.anim_click)
                v.startAnimation(animation)
                openChat()
            }
        })

        adapter.products = ArrayList<ItemRecycler>(10)

        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = linearLayoutManager
        binding.recycler.adapter = adapter
        println("OTKRIILS")
        binding.progressBar.visibility = View.INVISIBLE

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }

        binding.cardProduct.setOnClickListener {
            openChat()
        }

        startCamera()
    }

    private fun startCamera() {
        val barcodeDetector = BarcodeDetector.Builder(requireContext())
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        cameraSource = CameraSource.Builder(requireContext(), barcodeDetector)
            .setAutoFocusEnabled(true)
            .build()

        binding.cameraPreview.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    cameraSource.start(binding.cameraPreview.holder)
                } catch (e: IOException) {
                    Log.e("CAMERA", e.message ?: "")
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() > 0) {
                    val barcode = barcodes.valueAt(0)
                    Log.d("QR_CODE", barcode.rawValue ?: "")
                    val test = barcode.rawValue
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.cardProduct.visibility = View.VISIBLE
                        binding.cardProduct.text = test
                    }
                    cameraSource.stop()
                }
            }
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Camera permission is required to scan QR code",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
