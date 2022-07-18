package com.eganin.jetpack.thebest.movieapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eganin.jetpack.thebest.movieapp.R

class FragmentMoviesDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_detail, container, false)

    companion object {
        fun newInstance(name: String=""): FragmentMoviesDetails {
            val args = Bundle()
            args.putString("name", name)
            return FragmentMoviesDetails().also { it.arguments = args }
        }
    }

}