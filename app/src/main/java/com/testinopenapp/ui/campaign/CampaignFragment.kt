package com.testinopenapp.ui.campaign

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testinopenapp.R

class CampaignFragment : Fragment() {

    companion object {
        fun newInstance() = CampaignFragment()
    }

    private lateinit var viewModel: CampaignViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_campaign, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CampaignViewModel::class.java)
        // TODO: Use the ViewModel
    }

}