package com.example.dictionary.ui

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.domain.words.WordsEntity

private const val TAG = "@@@"

class MainActivity : AppCompatActivity(), MainActivityContract.MainActivityView {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val myAdapter by lazy { MainActivityAdapter() }
    private val progressDialog by lazy { ProgressDialog(this) }
    private var presenter: MainActivityContract.MainActivityPresenter = MainActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)

        initRecyclerView()

        binding.searchButton.setOnClickListener {
            val text = binding.textInputTe.text.toString()
            presenter.requestWordTranslation(text)
        }
    }

    private fun initRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = myAdapter
    }

    override fun showListWordsTranslated(list: List<WordsEntity>) {
        myAdapter.refreshList(list)
    }

    override fun showError() {

    }

    override fun startShowProgressLoading() {
        progressDialog.setTitle("Load data")
        progressDialog.setMessage("... please wait")
        progressDialog.show()
    }

    override fun stopShowProgressLoading() {
        progressDialog.dismiss()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()

    }


}