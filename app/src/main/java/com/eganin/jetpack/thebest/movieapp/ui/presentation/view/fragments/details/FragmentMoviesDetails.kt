package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import coil.compose.AsyncImage
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentMovieDetailBinding
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi.Companion.BASE_IMAGE_URL_BACKDROP
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.CastItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.downloadImage
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.BaseFragment
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.Greeting
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.MovieDetailsActivity.Companion.SAVE_MOVIE_DATA_KEY
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.MovieAppTheme
import com.google.android.material.snackbar.Snackbar
import java.util.*


class FragmentMoviesDetails : BaseFragment() {

    private val idMovie: Int? by lazy { arguments?.getInt(SAVE_MOVIE_DATA_KEY) }
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private var viewModel: MovieDetailsViewModel? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var isRationalShown = false
    private lateinit var responseMovie: MovieDetailsResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restorePreferencesData()
        setupUI(view = view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel =
            (requireActivity().application as MovieApp).myComponent.getMoviesDetailsRepository(
                fragment = this
            )
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (!isGranted) {
                onCalendarPermissionNotGranted()
            }
        }

    }

    override fun onDetach() {
        super.onDetach()
        viewModel = null
        requestPermissionLauncher.unregister()
    }

    override fun onDestroyView() {
        savePreferencesData()
        _binding = null
        super.onDestroyView()
    }


    private fun setupUI(view: View) {
        binding.composeActors.setContent {
            ComposeListActors()
        }
        setupListeners(view)
        observeData()
        idMovie?.let { viewModel?.downloadDetailsData(id = it) }
    }

    @Composable
    private fun ComposeListActors(listActors: List<CastItem> = emptyList()) {
        LazyRow {
            listActors.map { actorInfo ->
                item {
                    ActorCell(info = actorInfo)
                }
            }
        }
    }

    @Composable
    private fun ActorCell(info: CastItem) {
        Card(
            shape = RoundedCornerShape(4.dp),
            backgroundColor = Color(0xFF191926),
            modifier = Modifier.padding(8.dp)
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = (MoviesApi.BASE_IMAGE_URL + info.profilePath),
                    contentDescription = stringResource(id = R.string.image_actor_description),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_baseline_cloud_download_24),
                    fallback = painterResource(R.drawable.ic_baseline_sms_failed_24)
                )
                Text(text = info.name ?: "", color = Color.White, modifier = Modifier.width(80.dp))
            }
        }
    }

    private fun observeData() {
        viewModel?.detailsData?.observe(this.viewLifecycleOwner, this::updateInfoMovie)
        viewModel?.castData?.observe(this.viewLifecycleOwner) {
            binding.composeActors.setContent {
                ComposeListActors(listActors = it)
            }
        }
        viewModel?.stateData?.observe(this.viewLifecycleOwner) {
            setState(state = it, progressBar = binding.progressBarDetails)
        }
        viewModel?.dataCalendar?.observe(this.viewLifecycleOwner, this::startActivityCalendar)
    }

    private fun startActivityCalendar(intent: Intent) = startActivity(intent)

    private fun updateInfoMovie(response: MovieDetailsResponse) {
        responseMovie = response
        with(binding) {
            downloadImage(
                link = BASE_IMAGE_URL_BACKDROP + response.backdropPath,
                imageView = backgroundImage,
                context = requireContext()
            )
            adultTv.text = if (response.adult == true) "18+" else "12+"
            titleMovie.text = response.title
            tagLine.text = response.genres?.joinToString(separator = ",") { it.name ?: "" }
            countReviews.text = "${response.voteCount} REVIEWS"
            storylineDescription.text = response.overview
            response.voteAverage?.let { bindStars(rating = (it / 2).toInt()) }
        }
    }


    private fun setupListeners(view: View) {
        binding.backBtb?.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.backBtbArrow?.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.calendar.setOnClickListener {
            useCalendar(view = view)
        }
    }

    private fun useCalendar(view: View) {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_CALENDAR
                ) == PackageManager.PERMISSION_GRANTED -> onCalendarPermissionGranted(view = view)
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR) -> showDialog(
                    message = getString(R.string.message_permission_dialog),
                    action = {
                        isRationalShown = true
                        requestCalendarPermission()
                    })
                isRationalShown -> showDialog(
                    message = getString(R.string.message_denied_permission),
                    action = {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + it.packageName)
                            )
                        )
                    })
                else -> requestCalendarPermission()
            }
        }
    }

    private fun bindStars(rating: Int) {
        val listOfRating = listOf(
            binding.oneStar,
            binding.twoStar,
            binding.threeStar,
            binding.fourStar,
            binding.fiveStar,
        )
        for (i in 0 until rating) {
            listOfRating[i].setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_star_icon
                )
            )
        }
    }

    private fun onCalendarPermissionGranted(view: View) {
        context?.let {
            Toast.makeText(
                it,
                getString(R.string.calendar_permission_granted_label),
                Toast.LENGTH_SHORT
            )
        }
        showDatePicker(view = view)
    }

    private fun onCalendarPermissionNotGranted() =
        context?.let {
            Toast.makeText(
                it,
                getString(R.string.calendar_permission_not_granted_label),
                Toast.LENGTH_SHORT
            )
        }

    private fun showDialog(message: String, action: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(getString(R.string.positive_button_label)) { dialog, _ ->
                action()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.negative_button_label)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun requestCalendarPermission() {
        context?.let {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
        }
    }

    private fun showTimePicker(view: View, year: Int, month: Int, date: Int) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { p0, hourOfDay, minute ->

                Snackbar.make(
                    view,
                    "you choosed $hourOfDay:$minute",
                    Snackbar.LENGTH_LONG
                )
                    .show()

                viewModel?.writeDataCalendar(
                    year = year,
                    month = month,
                    date = date,
                    hourOfDay = hourOfDay,
                    minute = minute,
                    movie = responseMovie
                )
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun showDatePicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { p0, year, month, date ->
                Snackbar.make(
                    view,
                    "you choosed $year/$month/$date",
                    Snackbar.LENGTH_LONG
                )
                    .show()
                showTimePicker(view = view, year = year, month = month, date = date)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()

    }

    private fun savePreferencesData() {
        activity?.let {
            val sharedPreferences =
                (it.applicationContext as MovieApp).myComponent.getSharedPreferencesRationalShown()

            sharedPreferences.edit {
                putBoolean(KEY_LOCATION_PERMISSION_RATIONAL_SHOWN, isRationalShown)
            }
        }
    }

    private fun restorePreferencesData() {
        activity?.let {
            val sharedPreferences =
                (it.applicationContext as MovieApp).myComponent.getSharedPreferencesRationalShown()

            isRationalShown =
                sharedPreferences.getBoolean(KEY_LOCATION_PERMISSION_RATIONAL_SHOWN, false)
        }
    }

    companion object {
        private const val KEY_LOCATION_PERMISSION_RATIONAL_SHOWN =
            "KEY_LOCATION_PERMISSION_RATIONAL_SHOWN"
    }

}