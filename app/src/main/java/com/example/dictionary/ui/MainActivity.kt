package com.example.dictionary.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.App
import com.example.dictionary.data.WordsRepoImpl
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.domain.words.WordsEntity
import javax.inject.Inject

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
            val text = binding.textInputTe.text
            presenter.requestTranslated(binding.textInputTe.text.toString())
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }



    private fun initRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = myAdapter
    }

    override fun showListWordsTranslated(list: List<WordsEntity>) {
        myAdapter.refreshList(list)
        Log.d(TAG, "showListTranslated: ")
    }

     fun showProgressDialog() {
        progressDialog.setTitle("Load data")
        progressDialog.setMessage("... please wait")
        progressDialog.show()
    }

     fun dismissProgressDialog() {
        progressDialog.dismiss()
    }


    override fun showError() {

    }

    override fun startShowProgressLoading() {
        showProgressDialog()
    }

    override fun stopShowProgressLoading() {
        dismissProgressDialog()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()

    }


}