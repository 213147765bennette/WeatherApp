package com.example.weatherapp.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.weatherapp.R
import com.example.weatherapp.entity.FiveforecastEntity
import com.example.weatherapp.ui.adapter.FavouritresAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class GalleryFragment : Fragment() {

    companion object{
        val TAG = "GalleryFragment"
    }

    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var fouvaratesAdapter: FavouritresAdapter
    private lateinit var recyclerView: RecyclerView
    private  lateinit var mProgressBar: ProgressBar

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this.context)
    }

    private val deleteClickListner: (data: FiveforecastEntity) ->Unit ={
        showDeleteDialog(it)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {



        galleryViewModel =
                ViewModelProvider(this).get(GalleryViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        mProgressBar = root.findViewById(R.id.pbFavourates)
        recyclerView = root.findViewById(R.id.rvFavourates)
        fouvaratesAdapter = FavouritresAdapter(deleteClickListner)


        //observing the LIVE Data
        observers()

        recyclerView.apply {

            layoutManager = linearLayoutManager
            adapter = fouvaratesAdapter
        }

        val textView: TextView = root.findViewById(R.id.text_gallery)

        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }


    //in here observe live data from my model
    private fun observers(){

        galleryViewModel.isError.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->{
                showOnErrorDialog(msg)
            } }
        })


        galleryViewModel.isDeleted.observe(viewLifecycleOwner, Observer {
            if(it){
                showDeleteDialog()
            }
        })

        galleryViewModel.favouratesList.observe(viewLifecycleOwner, Observer {

            fouvaratesAdapter.setList(it)

        })

    }


    private fun showOnErrorDialog(msg:String){

        val dialog = MaterialDialog(requireContext())
            .cornerRadius(8f)
            .cancelable(false)
            .title(R.string.dialog_error_title)
            .message(null,msg)

        dialog.positiveButton {
            dialog.dismiss()
        }

        dialog.show()

    }

    //show the deleted dialog
    private fun showDeleteDialog(){

        val dialog = MaterialDialog(requireContext())
            .cornerRadius(8f)
            .cancelable(false)
            .title(R.string.dialog_deleted_title)
            .message(R.string.dialog_deleted_msg)

        dialog.positiveButton {
            dialog.dismiss()
        }

        dialog.show()

    }

    //show the delete dialog
    private fun showDeleteDialog(data: FiveforecastEntity){

        val dialog = MaterialDialog(requireContext())
            .cornerRadius(8f)
            .cancelable(false)
            .title(R.string.dialog_delete_title)
            .message(R.string.dialog_delete_msg)

        galleryViewModel.forecastId.value = data.id

        dialog.positiveButton(R.string.dialog_delete) {

            galleryViewModel.temperature.value = data.temperature
            galleryViewModel.temp_min.value = data.temp_min
            galleryViewModel.temp_max.value = data.temp_max
            galleryViewModel.main.value = data.main
            galleryViewModel.description.value = data.description
            galleryViewModel.icon.value = data.icon


            //Delete to favourated record from RoomDB
            galleryViewModel.delete()

        }

        dialog.negativeButton {
            dialog.dismiss()
        }

        dialog.show()

    }

}