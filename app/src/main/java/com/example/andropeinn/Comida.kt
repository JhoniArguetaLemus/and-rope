package com.example.andropeinn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class Comida : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


            val view= inflater.inflate(R.layout.fragment_comida, container, false)
            val recyclerView=view.findViewById<RecyclerView>(R.id.recycler_comida)
            val layoutManager=LinearLayoutManager(requireContext())
            recyclerView.layoutManager=layoutManager

            val comidaList= listOf(
                ComidaData(R.drawable.arroz_pollo, "Arroz con pollo", "2.50"),
                ComidaData(R.drawable.pechuga_arroz, "Pechuga con arroz", "3.25"),
                ComidaData(R.drawable.carne_arroz, "Carne azada con arroz y vegetales", "3.25")
            )

            recyclerView.adapter=ComidaAdapter(requireContext(), comidaList)

            recyclerView.setHasFixedSize(true)




           return  view

    }


}