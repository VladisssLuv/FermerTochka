package my.project.testretrofit.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
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

        binding.addPhoto.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)

            val file = File(context?.cacheDir, "image.jpg")
            file.createNewFile()

            val outputStream = FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            println(file)

            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            val base64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
            SafeRequest(lifecycleScope).request(object : SafeRequest.Protection {
                override suspend fun makeRequest(): BaseResponseInterface? {
                    return retrofitSource.addProduct(RequestBodyProduct(
                        19,
                        3,
                        "test",
                        "test",
                    12.3,
                        base64))
                }

                override fun ifSuccess(response: BaseResponseInterface?) {
                    println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK")
                    response as ResponseProduct
                    val decodedString = Base64.decode(response.photo, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    imageView.setImageBitmap(decodedByte)
                }
            })
        }
    }
}