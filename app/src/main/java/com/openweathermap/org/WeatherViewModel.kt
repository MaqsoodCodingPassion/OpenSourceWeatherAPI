package com.openweathermap.org

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openweathermap.org.model.CurrentWeatherResponse
import com.openweathermap.org.model.Forecast5days3hoursResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val currentWeatherResponse: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()
    val forecast5days3hoursResponse: MutableLiveData<Forecast5days3hoursResponse> = MutableLiveData()
    val multipleCitiesWeatherResponse: MutableLiveData<List<CurrentWeatherResponse>> = MutableLiveData()

    /*
      Taking list with multiple cities and calling APIs in parallel
     */
    fun fetchMultipleCitiesWeatherData(
        citiesList: List<String?>,
        apiKey: String
    ) {
        compositeDisposable += Observable.fromIterable(citiesList).flatMap {
            repository.fetchCityWeatherData(it, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }.toList()
            .subscribe(
                { multipleCitiesWeatherResponse.value = it }, {
                    it.printStackTrace()
                    multipleCitiesWeatherResponse.value = emptyList()
                }
            )
    }

    /*
      fetching current city name using current lat long API
     */
    fun fetchCurrentLocationDetails(lat: String, long: String, apiKey: String) {
        compositeDisposable += repository.fetchCurrentWeatherDetails(lat, long, apiKey)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<CurrentWeatherResponse>() {
                override fun onSuccess(data: CurrentWeatherResponse) {
                    currentWeatherResponse.value = data
                }
                override fun onError(e: Throwable) {
                    currentWeatherResponse.value = null
                }
            })
    }

    /*
      fetching 5 days and 3 hours each w.t.r city name
     */
    fun fetchForeCast5Days3HoursData(cityName: String, apiKey: String) {
        compositeDisposable += repository.fetchForecast5Days3Hours(cityName, apiKey)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Forecast5days3hoursResponse>() {
                override fun onSuccess(data: Forecast5days3hoursResponse) {
                    forecast5days3hoursResponse.value = data
                }

                override fun onError(e: Throwable) {
                    forecast5days3hoursResponse.value = null
                }
            })
    }

    /*
       Adding disposables using extension function
     */
    operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
        add(disposable)
    }

    /*
       clearing all disposables
     */
    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}

