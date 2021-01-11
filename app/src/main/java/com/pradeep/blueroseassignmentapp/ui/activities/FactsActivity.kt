package com.pradeep.blueroseassignmentapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.pradeep.blueroseassignmentapp.databinding.ActivityFactsBinding
import com.pradeep.blueroseassignmentapp.viewmodels.FactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FactsActivity : AppCompatActivity() {
    private lateinit var activityFactsBinding: ActivityFactsBinding

    private val factsViewModel: FactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            activityFactsBinding = ActivityFactsBinding.inflate(layoutInflater)
            setContentView(activityFactsBinding.root)
            setSupportActionBar(activityFactsBinding.factsToolbar)

            factsViewModel.observableFactTitle.observe(this) {
                Log.d("log", "In FactsActivity = $it")
                activityFactsBinding.factsToolbar.title = it?.title
            }

            // make the network request when the app is opened
            factsViewModel.getFactsFromNetwork()

        } catch (ex: Exception) {
            Log.e("log", "Exception in FactsActivity::onCreate() = ", ex)
        }
    }
}