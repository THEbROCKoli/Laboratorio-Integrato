package com.example.laboratorio_integrato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel() {
    var navBarShouldHide = MutableLiveData<Boolean>()

}