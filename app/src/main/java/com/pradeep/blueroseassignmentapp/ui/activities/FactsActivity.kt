package com.pradeep.blueroseassignmentapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.pradeep.blueroseassignmentapp.FactsApplication
import com.pradeep.blueroseassignmentapp.databinding.ActivityFactsBinding
import com.pradeep.blueroseassignmentapp.repositories.FactRepository
import com.pradeep.blueroseassignmentapp.viewmodels.FactsViewModel
import com.pradeep.blueroseassignmentapp.viewmodels.FactsViewModelFactory

class FactsActivity : AppCompatActivity() {
    lateinit var activityFactsBinding: ActivityFactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFactsBinding = ActivityFactsBinding.inflate(layoutInflater)
        setContentView(activityFactsBinding.root)
        setSupportActionBar(activityFactsBinding.factsToolbar)

        val factsRepository = FactRepository((application as FactsApplication).factsDatabase)
        val factsViewModelFactory = FactsViewModelFactory(factsRepository)
        val factsViewModel: FactsViewModel =
            ViewModelProvider(this, factsViewModelFactory).get(FactsViewModel::class.java)

        // make the network request when the app is opened
        factsViewModel.getFacts()

        factsViewModel.factsFromDB.observe(this) {
            Log.d("log", "In FactsActivity = $it")
           activityFactsBinding.factsToolbar.title = it?.title
        }
    }
}