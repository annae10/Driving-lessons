package com.harman.drivinglessons.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.harman.drivinglessons.R
import com.harman.drivinglessons.model.Note
import com.harman.drivinglessons.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val title = addTitle_et.text.toString()
        val description = addDescription_et.text.toString()

        if(inputCheck(title, description)){
            //Create Note object
            val note = Note(0,title,description)
            //Add data to database
            mNoteViewModel.addNote(note)
            Toast.makeText(requireContext(),"Successfully added!", Toast.LENGTH_LONG).show()
            //Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, description: String):Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty((description)))
    }

}