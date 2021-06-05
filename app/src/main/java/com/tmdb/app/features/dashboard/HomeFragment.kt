package com.tmdb.app.features.dashboard

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.tmdb.AppAlerts
import com.tmdb.app.AppCache
import com.tmdb.app.R
import com.tmdb.app.data.models.Contents
import com.tmdb.app.data.models.UIResultListener
import com.tmdb.app.di.Injectable
import com.tmdb.app.features.dashboard.adapter.MoviesListAdapter
import com.tmdb.app.features.dashboard.viewModel.HomeViewModel
import com.tmdb.app.helper.Helper
import com.tmdb.app.helper.addOnItemClickListener
import com.tmdb.app.interfaces.OnItemClickListener
import com.tmdb.app.widgets.TMDBEndlessScrollListener
import com.tmdb.app.widgets.TMDBTitleBar
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject
import kotlin.math.floor


class HomeFragment : Fragment(), Injectable {

    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var sColumnWidth: Int = 120
    private var spanCount: Int = 3

    private var page: Int = 1
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private var isLoading = false
    // If current page is the last page (Pagination will stop after this page load)
    private var isLastPage = false
    // indicates the current page which Pagination is fetching.
    private var currentPage = page

    private var totalContentItems: Int? = 0
    private var currentItems: Int? = 0

    companion object {
        private var contentList: ArrayList<Contents>? = arrayListOf()
    }
    private var moviesListAdapter: MoviesListAdapter? = null
    private var doubleBackToExitPressedOnce = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        setupView()
    }

    /**
     * Method to setup view
     */
    private fun setupView() {

        homeTitleBar?.setListener(object : TMDBTitleBar.Callback {
            override fun onBackButtonClick() {
                activity?.finish()
            }

            override fun onSearch(searchString: String) {
                isLoading = true
                moviesListAdapter?.getFilter()?.filter(searchString)
            }

            override fun onClearSearch() {
                isLoading = false
                contentList?.let {
                    moviesListAdapter?.setItems(it)
                    moviesListAdapter?.notifyDataSetChanged()
                }
            }
        })

        getGenreList()

        currentPage = 1
        getMovieList()
    }

    /**
     * Method to get the genre and save in the cache for later user
     */
    private fun getGenreList() {
        viewModel.getGenresDetails(this)
    }

    /**
     * Method to get the movie list
     */
    private fun getMovieList() {
        viewModel.getMovieDetails(this, object : UIResultListener<List<Contents>?> {

            override fun onLoading() {
                shimmerRecyclerView?.showShimmerAdapter()
            }

            override fun onReady(data: List<Contents>?) {
                setupData(data)
            }

            override fun onError() {
                shimmerRecyclerView?.hideShimmerAdapter()
                AppAlerts.showSnackBarException(view)
            }
        }, page = currentPage)
    }

    /**
     * Method to setup data with the parameter movie details
     */
    private fun setupData(data: List<Contents>?) {
        totalContentItems = AppCache.totalContents.value?.toInt()
        data?.let { listContents ->
            if (currentPage == 1) {
                currentItems = currentItems?.plus(listContents.size)
                contentList?.addAll(listContents)
                setupList()
            } else {
                contentList?.addAll(listContents)
                currentItems = currentItems?.plus(listContents.size)
                contentList?.let {
                    moviesListAdapter?.setItems(it)
                }
            }
        }
        val remainingCount = totalContentItems?.minus(currentItems!!)
        isLastPage = remainingCount!! <= 0
        isLoading = false

        shimmerRecyclerView?.hideShimmerAdapter()
        recyclerViewMovies?.visibility= View.VISIBLE
    }

    /**
     * Method to setup list
     */
    private fun setupList() {
        contentList?.let {
            moviesListAdapter = MoviesListAdapter(it)

            val layoutManager = GridLayoutManager(context, spanCount)
            recyclerViewMovies.layoutManager = layoutManager
            recyclerViewMovies.adapter = moviesListAdapter
            recyclerViewMovies.addOnScrollListener(object :
                TMDBEndlessScrollListener(layoutManager) {
                override fun loadMoreItems() {
                    isLoading = true
                    //Increment page index to load the next one
                    currentPage += 1
                    if (currentPage < totalContentItems!!) {
                        getMovieList()
                    }
                }

                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

            })

            recyclerViewMovies?.addOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(position: Int) {
                    val data = it[position]
                    activity?.let { act ->
                        Navigation.findNavController(act, R.id.my_nav_host_fragment).navigate(R.id.action_homeFragment_to_movieDetailsFragment, MovieDetailsFragment.buildBundle(data))
                    }
                }
            })
        }
    }

    /**
     * Method to calculate cell size for grid
     */
    private fun calculateCellSize() {
        recyclerViewMovies?.let {
            spanCount =
                floor((it.width / Helper.convertDPToPixels(activity, sColumnWidth)).toDouble()).toInt()
            if (spanCount > 3) {
                spanCount = 5
                shimmerRecyclerView.setDemoChildCount(5)
            } else {
                spanCount = 3
                shimmerRecyclerView.setDemoChildCount(3)
            }
            (it.layoutManager as GridLayoutManager).spanCount = spanCount
            view?.invalidate()
        }
    }

    /**
     * Method to handle back
     */
    private fun handleBack(){
        doubleBackToExitPressedOnce = true
        Toast.makeText(context, context?.getString(R.string.back_text), Toast.LENGTH_SHORT).show()

        Handler().postDelayed( { doubleBackToExitPressedOnce = false }, 1000)
    }

    override fun onResume() {
        super.onResume()

        val viewTreeObserver: ViewTreeObserver = recyclerViewMovies.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener { calculateCellSize() }

        // Method to handle back to get the action of back button
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN){
                    if (doubleBackToExitPressedOnce) {
                        return true
                    }
                    handleBack()
                }

                return false
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        contentList = null
    }
}