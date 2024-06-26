package com.example.andropeinn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Vegetariana : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_vegetariana, container, false)
        val recyclerView=view.findViewById<RecyclerView>(R.id.recycler_vegetariano)
        val layoutManager= LinearLayoutManager(requireContext())
        recyclerView.layoutManager=layoutManager

        val comidaList= listOf(
            ComidaData(R.drawable.cesar, "Ensalada César ", "3.50"),
            ComidaData(R.drawable.curry, "Curry de verduras", "3.00"),
            ComidaData(R.drawable.hummus, "Hummus con vegetales crudos", "3.25"),
            ComidaData(R.drawable.rissotto, "Risotto de champiñones", "3.00"),
            ComidaData(R.drawable.frijol, "Tacos de frijol", "3.00"),
            ComidaData(R.drawable.ensalada_griega, "Ensalada griega", "3.50"),
            ComidaData(R.drawable.lasanna_vegetariana, "Lasaña vegetariana", "4.00"),
            ComidaData(R.drawable.quiche_espinacas, "Quiche de espinaca", "3.75"),
            ComidaData(R.drawable.patatas_bravas, "Patatas bravas", "3.00"),
            ComidaData(R.drawable.falafel, "Falafel", "3.25"),
            ComidaData(R.drawable.burrito_vegetariano, "Burrito vegetariano", "3.50"),
            ComidaData(R.drawable.pizza_vegetariana, "Pizza vegetariana", "4.50"),
            ComidaData(R.drawable.berengenas_parmesana, "Berenjenas a la parmesana", "4.00")
        )

        recyclerView.adapter=ComidaAdapter(requireContext(), comidaList)

        recyclerView.setHasFixedSize(true)


        return  view
    }


}