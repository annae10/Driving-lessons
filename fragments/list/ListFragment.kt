package com.harman.drivinglessons.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.harman.drivinglessons.R
import com.harman.drivinglessons.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //Recyclerview
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //NoteViewModel
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.readAllData.observe(viewLifecycleOwner, Observer { note ->
            adapter.setData(note)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        val newsIb = view.findViewById<ImageButton>(R.id.newsIb)
        newsIb.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_newsFragment)
        }

        val tasksIb = view.findViewById<ImageButton>(R.id.tasksIb)
        tasksIb.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_tasksFragment)
        }


        //Add menu
        setHasOptionsMenu(true)

        val tvDeleteAll = view.findViewById<TextView>(R.id.tvDeleteAll)
        tvDeleteAll.setOnClickListener {
            deleteAllNotes()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        floatingActionButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            mNoteViewModel.deleteAllNotes()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_,_ ->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

}