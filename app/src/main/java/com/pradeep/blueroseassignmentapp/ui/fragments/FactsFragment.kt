package com.pradeep.blueroseassignmentapp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.pradeep.blueroseassignmentapp.R
import com.pradeep.blueroseassignmentapp.adapters.FactsListAdapter
import com.pradeep.blueroseassignmentapp.databinding.FragmentFactsBinding
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import com.pradeep.blueroseassignmentapp.viewmodels.FactsViewModel
import dagger.hilt.android.AndroidEntryPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FactsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val factsViewModel: FactsViewModel by activityViewModels()

    lateinit var fragmentFactsBinding: FragmentFactsBinding
    private val factsListAdapter = FactsListAdapter(listOf())

    enum class ErrorCodes {
        NO_INTERNET
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFactsBinding = FragmentFactsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return fragmentFactsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentFactsBinding.rvFacts.setHasFixedSize(true)
        fragmentFactsBinding.rvFacts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val itemDecoration: ItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        fragmentFactsBinding.rvFacts.addItemDecoration(itemDecoration)

        fragmentFactsBinding.rvFacts.adapter = factsListAdapter

        factsViewModel.allFactItems.observe(viewLifecycleOwner) {
            Log.d("log", "In FactsFragment, allFactItems = $it")
            // set the recyclerview adapter
            it?.let {
                if (it.isEmpty()) {
                    // show no data to display text
                    fragmentFactsBinding.tvNoData.text = resources.getString(R.string.fetching_data)
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (it.isEmpty()) {
                            fragmentFactsBinding.tvNoData.text =
                                resources.getString(R.string.no_data)
                        }
                    }, 5000)
                } else {
                    fragmentFactsBinding.tvNoData.visibility = View.GONE
                    factsListAdapter.factItems = it
                    factsListAdapter.notifyDataSetChanged()
                }
            }
        }

        factsViewModel.errorStatus.observe(viewLifecycleOwner) {
            Log.d("log", "In FactsFragment, errorStatus = $it")
            // show toast
            if (it == ErrorCodes.NO_INTERNET.ordinal) {
                Log.d("log", "no internet error code, show toast")
                Toast.makeText(
                    this.context,
                    resources.getString(R.string.no_internet),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.d("log", "unrecognized error code")
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FactsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FactsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                try {
                    item.isEnabled = false
                    var tempList: ArrayList<FactItem>? = null
                    // refresh the data. Get the latest data and update the view
                    if (factsListAdapter.factItems.isNotEmpty()) {
                        tempList = ArrayList()
                        tempList.addAll(factsListAdapter.factItems)
                        (factsListAdapter.factItems as ArrayList).clear()
                        factsListAdapter.notifyDataSetChanged()
                    }

                    // notify user that data is being refreshed
                    fragmentFactsBinding.tvNoData.visibility = View.VISIBLE
                    fragmentFactsBinding.tvNoData.text =
                        resources.getString(R.string.refreshing_data)
                    Toast.makeText(
                        this.context,
                        resources.getString(R.string.refreshing_data),
                        Toast.LENGTH_SHORT
                    ).show()

                    // make the network request to refresh data
                    factsViewModel.getFacts()

                    Handler(Looper.getMainLooper()).postDelayed({
                        if (tempList == null) {
                            fragmentFactsBinding.tvNoData.text =
                                resources.getString(R.string.no_data)
                        } else {
                            fragmentFactsBinding.tvNoData.visibility = View.GONE
                            factsListAdapter.factItems = tempList
                            factsListAdapter.notifyDataSetChanged()
                        }
                        item.isEnabled = true
                    }, 1500)
                } catch (ex: Exception) {
                    Log.e("log", "Exception in onOptionsItemSelected() = ", ex)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}