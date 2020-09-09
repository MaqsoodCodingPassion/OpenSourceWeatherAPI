package com.openweathermap.org

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.openweathermap.org.adapter.CitiesAdapter
import com.openweathermap.org.adapter.Forecast5Days3HoursAdapter
import com.openweathermap.org.gps.GpsUtils
import com.openweathermap.org.gps.LocationViewModel
import com.openweathermap.org.model.CurrentWeatherResponse
import com.openweathermap.org.model.ListItem
import com.openweathermap.org.util.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var locationViewModel: LocationViewModel
    private var isGPSEnabled = false
    private var latitude: String? = null
    private var longitude: String? = null
    private var citiesWeatherList: ArrayList<CurrentWeatherResponse>? = null
    private var citiesAdapter: CitiesAdapter? = null
    private var foreCast5DaysDataList: ArrayList<ListItem>? = null
    private var foreCast5DaysAdapter: Forecast5Days3HoursAdapter? = null
    private var citiesNameList = ArrayList<String>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)

        GpsUtils(this).turnGPSOn(object : GpsUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                this@MainActivity.isGPSEnabled = isGPSEnable
            }
        })
        initAdapter()
        searchBtn.setOnClickListener {
            performSearchCitiesWeather()
        }

        searchViewSetUp()
        citiesField.requestFocusFromTouch()
        callCurrentCityNameAPI()
    }

    private fun initAdapter() {
        citiesWeatherList = ArrayList()
        citiesAdapter = CitiesAdapter(citiesWeatherList!!)
        foreCast5DaysDataList = ArrayList()
        foreCast5DaysAdapter = Forecast5Days3HoursAdapter(foreCast5DaysDataList!!)

        citiesRecyclerView.layoutManager = LinearLayoutManager(this)
        citiesRecyclerView.adapter = citiesAdapter
        fiveDaysDataRecyclerView.layoutManager = LinearLayoutManager(this)
        fiveDaysDataRecyclerView.adapter = foreCast5DaysAdapter
    }

    private fun performSearchCitiesWeather() {
        if (isMinimum3Cities()) {
            weatherViewModel.fetchMultipleCitiesWeatherData(citiesNameList, API_KEY)
            multipleCitiesObservableLiveData()
        } else {
            showErrorDialog(this, getString(R.string.multipleCityNamesHint))
        }
    }

    private fun isMinimum3Cities(): Boolean {
        val values = citiesField.query.toString().split(",")
        citiesNameList = ArrayList(values)
        return ArrayList<String?>(values).size in 3..7
    }

    private fun searchViewSetUp() {
        citiesField.setIconifiedByDefault(true)
        citiesField.isFocusable = true
        citiesField.isIconified = false
        citiesField.clearFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                callCurrentCityNameAPI()
            }
        }
    }

    private fun multipleCitiesObservableLiveData() {
        searchBtn.isEnabled = false
        citiesWeatherList?.clear()
        weatherViewModel.multipleCitiesWeatherResponse.observe(this, Observer { response ->
            searchBtn.isEnabled = true
            hideKeyboard(this)
            if (response.isNotEmpty()) {
                this.citiesAdapter!!.setDataList(response)
                this.citiesAdapter!!.notifyDataSetChanged()
            } else if(response.isEmpty()){
                showErrorDialog(this, getString(R.string.multipleCityNamesHint))
            }
        })
    }

    private fun currentLocationObservableLiveData() {
        foreCast5DaysDataList?.clear()
        weatherViewModel.currentWeatherResponse.observe(this, Observer {
            if (it != null) {
                cityName.text = it.name.toString()
                weatherViewModel.fetchForeCast5Days3HoursData(it.name.toString(), API_KEY)
                forecast5Days3HourObservableLiveData()
            } else {
                //Need to handle proper error
                showErrorDialog(this, "oops something went wrong, please try after sometime")
            }
        })
    }

    private fun forecast5Days3HourObservableLiveData() {
        weatherViewModel.forecast5days3hoursResponse.observe(this, Observer {
            if (it != null) {
                progressBar.showView(false)
                it.list?.let { it1 -> foreCast5DaysAdapter?.setDataList(it1) }
                foreCast5DaysAdapter!!.notifyDataSetChanged()
            } else {
                //Need to handle proper error
                showErrorDialog(this, "oops something went wrong, please try after sometime")
            }
        })
    }

    private fun callCurrentCityNameAPI() {
        when {
            isPermissionsGranted(this) -> startLocationUpdate()
            else -> ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_REQUEST
            )
        }
    }

    private fun startLocationUpdate() {
        var count: Int = 0
        locationViewModel.getLocationData().observe(this, Observer {
            this.latitude = it.latitude.toString()
            this.longitude = it.longitude.toString()
            if (count == 0) {
                weatherViewModel.fetchCurrentLocationDetails(latitude!!, longitude!!, API_KEY)
                currentLocationObservableLiveData()
                count++
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) { super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> { callCurrentCityNameAPI() }
        }
    }
}

const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101


