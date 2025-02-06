package com.app.githubu

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.githubu.ui.main.MainActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var mockWebServer: MockWebServer

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080) // Ensure your Retrofit points to this port
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testUserListDisplay() {
        // Mock API Response
        val mockResponse = """
            [
                {
                    "id": 1,
                    "login": "octocat",
                    "avatar_url": "https://example.com/avatar"
                },
                {
                    "id": 2,
                    "login": "hubber",
                    "avatar_url": "https://example.com/avatar2"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockResponse)
        )

        // Check if RecyclerView is displayed
        onView(withId(R.id.rv_users))
            .check(matches(isDisplayed()))

        // Scroll to the first item and verify content
        onView(withId(R.id.rv_users))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(hasDescendant(withText("octocat"))))
    }
}