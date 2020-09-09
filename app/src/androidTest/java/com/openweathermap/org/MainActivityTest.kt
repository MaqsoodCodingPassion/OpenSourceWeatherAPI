package com.synchronoss.currentweather

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.openweathermap.org.MainActivity
import com.openweathermap.org.R
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsNot.not
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class MainActivityTest {

    private var device: UiDevice? = null // for testing location

    var citiesRecyclerView: RecyclerView? = null
    var fiveDaysDataRecyclerView: RecyclerView? = null

    @get:Rule
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        citiesRecyclerView =
            mainActivityTestRule.activity.findViewById(R.id.citiesRecyclerView)
        fiveDaysDataRecyclerView =
            mainActivityTestRule.activity.findViewById(R.id.fiveDaysDataRecyclerView)
    }

    @Test
    fun testAllowLocationPermission() {
        val allowButton = this.device?.findObject(UiSelector().text("ALLOW"))
        var permissionAllowedMessage =
            this.device?.findObject(UiSelector().text("Permission allowed"))
        allowButton!!.click()
        assert(permissionAllowedMessage!!.exists())
    }

    @Test
    fun testDenyLocationPermission() {
        val denyButton = this.device?.findObject(UiSelector().text("DENY"))
        val permissionDeniedMessage =
            this.device?.findObject(UiSelector().text("Permission denied"))
        denyButton!!.click()
        assert(permissionDeniedMessage!!.exists())
    }

    @Test
    fun testCitiesFieldVisibility() {
        onView(allOf(withId(R.id.citiesField), not(withText("Unwanted"))))
    }

    @Test
    fun testStep1Visibility() {
        onView(allOf(withId(R.id.step1), not(withText("Unwanted"))))
    }

    @Test
    fun testStep2Visibility() {
        onView(allOf(withId(R.id.step2), not(withText("Unwanted"))))
    }

    @Test
    fun testIsMainActivityInView() {
        onView(withId(R.id.parenLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchBtnVisibility() {
        onView(withId(R.id.searchBtn)).check(matches(isDisplayed()))
    }

    @Test
    fun testCityNameLabelVisibility() {
        onView(withId(R.id.cityLabel)).check(matches(isDisplayed()))
    }

    @Test
    fun testCityNameVisibility() {
        onView(withId(R.id.cityName)).check(matches(isDisplayed()))
    }

    @Test
    fun testCitiesRecyclerViewVisibility() {
        onView(withId(R.id.citiesRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testFiveDaysDataRecyclerViewVisibility() {
        onView(withId(R.id.fiveDaysDataRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testCitiesRecyclerViewScrollTestRowItems() {
        var ItemCount = citiesRecyclerView?.adapter?.itemCount!! - 1
        onView(withId(R.id.citiesRecyclerView)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                ItemCount
            )
        )
    }

    @Test
    fun testFiveDaysDataRecyclerViewScrollTestRowItems() {
        var ItemCount = fiveDaysDataRecyclerView?.adapter?.itemCount!! - 1
        onView(withId(R.id.fiveDaysDataRecyclerView)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                ItemCount
            )
        )
    }

    @Test
    fun testCitiesRecyclerViewItemCount() {
        var itemCount = citiesRecyclerView?.adapter?.itemCount
        Assert.assertTrue(itemCount!! > 0)
    }

    @Test
    fun testFiveDaysDataRecyclerViewItemCount() {
        var itemCount = fiveDaysDataRecyclerView?.adapter?.itemCount
        Assert.assertTrue(itemCount!! > 0)
    }
}


@After
fun tearDown() {
    /*InstrumentationRegistry.getInstrumentation().uiAutomation.revokeRuntimePermission(
        InstrumentationRegistry.getInstrumentation().targetContext.packageName,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    InstrumentationRegistry.getInstrumentation().uiAutomation.revokeRuntimePermission(
        InstrumentationRegistry.getInstrumentation().targetContext.packageName,
        Manifest.permission.ACCESS_FINE_LOCATION)*/
}
