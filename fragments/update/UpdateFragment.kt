package com.harman.drivinglessons.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.harman.drivinglessons.R
import com.harman.drivinglessons.model.Note
import com.harman.drivinglessons.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.updateTitle_et.setText(args.currentNote.title)
        view.updateDescription_et.setText(args.currentNote.description)

        view.update_btn.setOnClickListener {
            updateItem()
        }

        //Add menu
        setHasOptionsMenu(true)


        val tvDelete = view.findViewById<TextView>(R.id.tvDelete)
        tvDelete.setOnClickListener {
            deleteNote()
        }

        return view
    }

    private fun updateItem(){
        val title = updateTitle_et.text.toString()
        val description = updateDescription_et.text.toString()

        if(inputCheck(title, description)) {
            //Create Note object
            val updatedNote = Note(args.currentNote.id, title, description)
            //Update current note
            mNoteViewModel.updateNote(updatedNote)
            Toast.makeText(requireContext(), "Updated successfully!", Toast.LENGTH_SHORT).show()
            //Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(title: String, description: String):Boolean {    //for int: value:Editable
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty((description)))
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
        mNoteViewModel.deleteNote(args.currentNote)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentNote.title}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") {_,_ ->}
        builder.setTitle("Delete ${args.currentNote.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentNote.title}?")
        builder.create().show()
    }
}