package com.tcp.zomatosearch.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tcp.zomatosearch.data.model.RootDataClass
import com.tcp.zomatosearch.data.repository.MainRepository
import com.tcp.zomatosearch.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val rootData = MutableLiveData<Resource<RootDataClass>>()
    private val compositeDisposable = CompositeDisposable()

    fun fetchRootData(q : String) {
        rootData.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getRootData(q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    rootData.postValue(Resource.success(userList))
                }, { throwable ->
                    Log.e("errormsg",throwable.message!!)
                    rootData.postValue(Resource.error(throwable.message!!, null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getRootData(): LiveData<Resource<RootDataClass>> {
        return rootData
    }

}