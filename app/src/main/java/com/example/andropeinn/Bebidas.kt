package com.example.andropeinn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView




class Bebidas : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_bebidas, container, false)
        val recyclerView=view.findViewById<RecyclerView>(R.id.recycler_bebidas)
        val layoutManager= LinearLayoutManager(requireContext())
        recyclerView.layoutManager=layoutManager

        val comidaList= listOf(
            ComidaData(R.drawable.coca, "Coca cola en lata", "1.00"),
            ComidaData(R.drawable.fanta, "Fanta vidrio", "0.75"),
            ComidaData(R.drawable.crma, "Crema soda vidrio", "0.75")
        )

        recyclerView.adapter=ComidaAdapter(requireContext(), comidaList)

        recyclerView.setHasFixedSize(true)


        return view
    }


}