package com.pradeep.blueroseassignmentapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.pradeep.blueroseassignmentapp.FactsApplication
import com.pradeep.blueroseassignmentapp.R
import com.pradeep.blueroseassignmentapp.adapters.FactsListAdapter
import com.pradeep.blueroseassignmentapp.databinding.FragmentFactsBinding
import com.pradeep.blueroseassignmentapp.repositories.FactRepository
import com.pradeep.blueroseassignmentapp.viewmodels.FactsViewModel
import com.pradeep.blueroseassignmentapp.viewmodels.FactsViewModelFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FactsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var factsViewModel: FactsViewModel
    lateinit var fragmentFactsBinding: FragmentFactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
        val factsRepository =
            FactRepository((requireActivity().application as FactsApplication).factsDatabase)
        val factsViewModelFactory = FactsViewModelFactory(factsRepository)
        factsViewModel =
            ViewModelProvider(this, factsViewModelFactory).get(FactsViewModel::class.java)
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
        val factsListAdapter = FactsListAdapter(listOf())
        fragmentFactsBinding.rvFacts.adapter = factsListAdapter

        factsViewModel.allFactItems.observe(viewLifecycleOwner) {
            Log.d("log", "In FactsFragment = $it")
            // set the recyclerview adapter
            it?.apply {
                factsListAdapter.factItems = it
                factsListAdapter.notifyDataSetChanged()
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
                // refresh the data. Get the latest data and update the view
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}