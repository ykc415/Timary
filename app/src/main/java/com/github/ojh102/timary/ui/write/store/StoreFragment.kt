package com.github.ojh102.timary.ui.write.store

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ojh102.timary.EventObserver
import com.github.ojh102.timary.R
import com.github.ojh102.timary.base.BaseFragment
import com.github.ojh102.timary.databinding.FragmentStoreBinding
import com.github.ojh102.timary.ui.legacy.write.store.StoreAdapter
import com.github.ojh102.timary.ui.legacy.write.store.StoreItem
import com.github.ojh102.timary.util.TimaryParser
import java.util.Calendar
import javax.inject.Inject

internal class StoreFragment : BaseFragment<FragmentStoreBinding>() {
    override val layoutRes = R.layout.fragment_store

    private val viewModel by viewModels<StoreViewModel> { viewModelFactory }

    private val args by navArgs<StoreFragmentArgs>()

    @Inject
    lateinit var timaryParser: TimaryParser

    @Inject
    lateinit var storeAdapter: StoreAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.timaryParser = timaryParser

        initRecyclerView()

        viewModel.storeItems.observe(this, Observer { storeAdapter.submitList(it) })
        viewModel.showDatePicker.observe(this, EventObserver { showDatePickerDialog() })
        viewModel.navigateToComplete.observe(this, EventObserver { navController.navigate(it) })

        viewModel.argument(args)
    }

    private fun initRecyclerView() {
        binding.rvStore.run {
            layoutManager = LinearLayoutManager(context)
            adapter = storeAdapter
        }

        storeAdapter.callbacks = object : StoreAdapter.Callbacks {
            override fun onItemSelect(item: StoreItem, position: Int) {
                viewModel.onClickStoreItem(item, position)
            }
        }
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1)
        }

        val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val selectedCal = Calendar.getInstance().apply {
                set(year, month, day)
            }

            viewModel.onSelectDatePicker(selectedCal)
        }, cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH]).apply {
            setCancelable(false)
            setButton(DatePickerDialog.BUTTON_NEGATIVE, null, { _, _ -> })
            datePicker.minDate = cal.timeInMillis
        }

        dialog.show()
    }
}
