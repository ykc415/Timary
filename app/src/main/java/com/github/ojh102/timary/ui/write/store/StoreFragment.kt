package com.github.ojh102.timary.ui.write.store

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ojh102.timary.EventObserver
import com.github.ojh102.timary.R
import com.github.ojh102.timary.base.BaseFragment
import com.github.ojh102.timary.databinding.FragmentStoreBinding
import java.util.Calendar
import org.threeten.bp.LocalDate

internal class StoreFragment : BaseFragment<FragmentStoreBinding>() {
    override val layoutRes = R.layout.fragment_store

    private val viewModel by viewModels<StoreViewModel> { viewModelFactory }

    private val args by navArgs<StoreFragmentArgs>()

    private val storeAdapter by lazy { StoreAdapter(viewModel) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        initToolbar()
        initRecyclerView()

        viewModel.storeItems.observe(this) {
            storeAdapter.submitList(it)
            storeAdapter.notifyDataSetChanged()
        }
        viewModel.showDatePicker.observe(this, EventObserver { showDatePickerDialog() })
        viewModel.navigateToComplete.observe(this, EventObserver { navController.navigate(it) })

        viewModel.argument(args)
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { navController.popBackStack() }
    }

    private fun initRecyclerView() {
        binding.rvStore.run {
            layoutManager = LinearLayoutManager(context)
            adapter = storeAdapter
        }
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1)
        }

        context?.let {
            DatePickerDialog(it, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                viewModel.onSelectDatePicker(LocalDate.of(year, month + 1, day))
            }, cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH]).apply {
                setCancelable(false)
                setButton(DatePickerDialog.BUTTON_NEGATIVE, null) { _, _ -> }
                datePicker.minDate = cal.timeInMillis
            }.show()
        }
    }
}
