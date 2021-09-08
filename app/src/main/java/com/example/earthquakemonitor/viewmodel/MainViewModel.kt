package com.example.earthquakemonitor.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.earthquakemonitor.model.data.Earthquake
import com.example.earthquakemonitor.model.data.EqDataBase
import com.example.earthquakemonitor.model.data.getDatabase
import com.example.earthquakemonitor.model.network.ApiResponseStatus
//import com.example.earthquakemonitor.model.network.ApiResponseStatus
import com.example.earthquakemonitor.model.network.Repository
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import java.net.UnknownHostException

//a partir del application, podemos obtener un Contexto, ya que los viewModel no tiene contexto
private val TAG = MainViewModel::class.java.simpleName

class MainViewModel(application: Application) : AndroidViewModel(application) {

/*    //private val job = Job()
    // Es el alcance del hilo en que queremos que corra, se ejecuta en el hilo principal junto con job
    // y va a estar vivo mientras este vivo el job; el Mai no permitr actualizando el eqList dentro de esa coroutine
    //private val coroutineScope = CoroutineScope(Dispatchers.Main + job)*/

    //private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    //val eqList : LiveData<MutableList<Earthquake>>
    //  get() = _eqList


    // Jamás se debe actualizar la UI o los liveData's, desde dentro de una coroutine que no sea Dispatchers.Main

    private val dataBase = getDatabase(application)
    private val repository = Repository(dataBase)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status

    //volvemos a utilizar el mutablelivedata
    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
        get() = _eqList


    init {
        reloadEarthquakes(false)
    }


    private fun reloadEarthquakes(sortByMagnitude: Boolean) {
        viewModelScope.launch {

            //_eqlist.value = repository.fetchEarthquakes()
            try {
                _status.value = ApiResponseStatus.LOADING
                _eqList.value = repository.fetchEarthquakes(sortByMagnitude)
                _status.value = ApiResponseStatus.DONE

            } catch (e: UnknownHostException) {
                _status.value = ApiResponseStatus.NOT_INTERNET_CONNECTION
                Log.d(TAG, "No internet connection", e)
            }

        }
    }

    fun reloadEarthquakesFromDatabase(sortByMagnitude: Boolean) {
        viewModelScope.launch {
            //_eqlist.value = repository.fetchEarthquakes()
            _eqList.value = repository.fetchEarthquakesDatabase(sortByMagnitude)
        }
    }

/*    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }*/
}