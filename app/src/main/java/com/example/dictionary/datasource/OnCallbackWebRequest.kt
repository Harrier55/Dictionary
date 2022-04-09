package com.example.dictionary.datasource

import com.example.dictionary.domain.entities.skyeng.SkyengBase
import com.example.dictionary.ui.MainActivityPresenter

interface OnCallbackWebRequest {
    fun onResponse(body: List<SkyengBase>?)
    fun onFailure()
}