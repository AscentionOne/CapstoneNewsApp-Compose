package com.kenchen.capstonenewsappcompose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kenchen.capstonenewsappcompose.ui.news.CapstoneNewsApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NewsScreenNavigationTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupNewsScreenNavHost() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            CapstoneNewsApp(navController = navController)
        }
    }

    @Test
    fun newsScreenNavHost_verifyStartNavigation() {
        val newsScreenContentDescription =
            composeTestRule.activity.getString(R.string.news_screen_content_description)
        composeTestRule
            .onNodeWithContentDescription(newsScreenContentDescription)
            .assertIsDisplayed()

        navController.assertCurrentRouteName(CapstoneNewsDestinations.HOME_ROUTE)
    }

    @Test
    fun newsScreenNavHost_clickOnFirstNews_navigateToNewsDetailScreen() {
        val newsListContentDescription =
            composeTestRule.activity.getString(R.string.news_list_content_description)

        // wait until the news list is shown
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithContentDescription(newsListContentDescription)
                .fetchSemanticsNodes().size == 1
        }
        // perform click on the news
        composeTestRule
            .onNodeWithContentDescriptionStringId(R.string.news_list_content_description)
            .performClick()
        // assert navigates to news detail screen
        composeTestRule
            .onNodeWithContentDescriptionStringId(R.string.news_detail_screen_content_description)
            .assertIsDisplayed()

        navController.assertCurrentRouteName(CapstoneNewsDestinations.NEWS_DETAIL_ROUTE)
    }

}

/**
 * Assertion utility extensions
 * */

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithContentDescriptionStringId(
    @StringRes id: Int,
): SemanticsNodeInteraction = onNodeWithContentDescription(activity.getString(id))
