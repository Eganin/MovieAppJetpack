package com.eganin.jetpack.thebest.movieapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.ActorAdapter
import com.eganin.jetpack.thebest.movieapp.data.models.Actor

class FragmentMoviesDetails : Fragment() {

    private val actorsAdapter = ActorAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view = view)
    }

    private fun setupRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.actors_recycler_view).apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorsAdapter
            actorsAdapter.bindActors(
                actors = listOf(
                    Actor(
                        id = 1,
                        name = "Robert",
                        image = "https://i.pinimg.com/736x/1c/45/9d/1c459d38763b376d9f55f63289de22cb.jpg"
                    ),
                    Actor(
                        id = 2,
                        name = "Robert",
                        image = "https://i.pinimg.com/736x/1c/45/9d/1c459d38763b376d9f55f63289de22cb.jpg"
                    ),
                    Actor(
                        id = 3,
                        name = "Robert",
                        image = "https://i.pinimg.com/736x/1c/45/9d/1c459d38763b376d9f55f63289de22cb.jpg"
                    ),
                    Actor(id = 4, name = "Robert", image = ""),
                )
            )
        }
    }

    companion object {
        fun newInstance(name: String = ""): FragmentMoviesDetails {
            val args = Bundle()
            args.putString("name", name)
            return FragmentMoviesDetails().also { it.arguments = args }
        }
    }

}