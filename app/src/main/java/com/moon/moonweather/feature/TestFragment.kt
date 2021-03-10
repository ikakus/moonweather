package com.moon.moonweather.feature

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.moon.moonweather.R
import com.moon.moonweather.vmiflow.TestFeature
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TestFragment : Fragment(R.layout.fragment_test) {

    var feature = TestFeature()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test_button.setOnClickListener {
            lifecycleScope.launch {
                feature.emit(TestFeature.Wish.Load)
            }
        }

        lifecycleScope.launch {
            feature.collect {
                toast(it)
            }
        }
    }

    fun toast(state: TestFeature.State) {
        Toast.makeText(context, state.toString(), Toast.LENGTH_SHORT).show()
    }

}
