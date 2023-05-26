package my.project.testretrofit.fragments

import android.R
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import android.Manifest
import my.project.testretrofit.TokenStorage
import my.project.testretrofit.databinding.FragmentAddProductBinding
import my.project.testretrofit.retrofit.RequestBody.RequestBodyProduct
import my.project.testretrofit.retrofit.ResponseBody.BaseResponseInterface
import my.project.testretrofit.retrofit.ResponseBody.ResponseProduct

import my.project.testretrofit.retrofit.RetrofitSource
import my.project.testretrofit.retrofit.SafeRequest
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class FragmentAddProduct: FragmentBase(){
    private lateinit var binding: FragmentAddProductBinding
    private val retrofitSource: RetrofitSource = RetrofitSource()
    private val cameraRequest = 123
    private lateinit var imageView: ImageView
    private var indexCategory: Int = 2
    private var base64 : String? = null

    companion object {
        fun newInstance(): FragmentAddProduct {
            return FragmentAddProduct()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(layoutInflater)
        imageView = binding.image
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = binding.spinner
        val items = arrayOf("Все", "Молочная продукция", "Мясная продукция", "Овощи", "Фрукты")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                indexCategory = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.addPhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, cameraRequest)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), cameraRequest)
            }
        }

        binding.addProduct.setOnClickListener {
            SafeRequest(lifecycleScope).request(object : SafeRequest.Protection {
                override suspend fun makeRequest(): BaseResponseInterface? {
                    if (!checkInput())
                        return null
                    return retrofitSource.addProduct(RequestBodyProduct(
                        TokenStorage.ID,
                        indexCategory,
                        binding.editTextName.text.toString(),
                        binding.editTextDisc.text.toString(),
                        binding.editTextPrice.text.toString().toDouble(),
                        base64))
                }

                override fun ifSuccess(response: BaseResponseInterface?) {
                }
            })
            binding.editTextName.setText(" ")
            binding.editTextDisc.setText(" ")
            binding.editTextPrice.setText(" ")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)

            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            base64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
    }

    private fun checkInput(): Boolean {
        return  binding.editTextName.text.toString().length != 0 &&
            binding.editTextDisc.text.toString().length != 0 &&
                binding.editTextPrice.text.toString().length != 0
    }
}