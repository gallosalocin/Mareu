package com.gallosalocin.mareu;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.gallosalocin.mareu.ui.MainActivity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 * Testing MainActivity screen
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) @RunWith(AndroidJUnit4.class) public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        mActivityRule.getActivity();
    }

    /**
     * When we click on add button, we create a meeting and it's displayed on the main activity.
     */
    @Test
    public void myMeetingsList_addMeeting_shouldAddMeeting() {
        onView(withId(R.id.fab_add_meeting)).perform(click());
        onView(withId(R.id.add_meeting_content)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner_room)).perform(click());
        onView(withText("Salle D")).perform(click());
        onView(withId(R.id.text_view_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.text_view_time)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.text_input_topic)).perform(typeText("Projet 4"), closeSoftKeyboard());
        onView(withId(R.id.text_input_email)).perform(typeText("gallosalocin@gmail.com")).perform(pressImeActionButton());
        onView(withId(R.id.button_save)).perform(click());
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we click on delete button, we cancel the deletion of a meeting from the main activity list by pressing CANCEL.
     */
    @Test
    public void myMeetingsList_deleteMeeting_shouldCancelDeletionMeeting() {
        onView(allOf(withId(R.id.recyclerView), isDisplayed()));
        onView(withId(R.id.iv_cardview_delete_btn)).perform(click());
        onView(withText("ANNULER")).perform(click());
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we click on add button, the add activity is displayed
     */
    @Test
    public void myMeetingsList_clickOnAddbutton_shouldDisplayAddMeetingActivity() {
        onView(withId(R.id.fab_add_meeting)).perform(click());
        onView(withId(R.id.add_meeting_content)).check(matches(isDisplayed()));
    }

    /**
     * When we click on delete button, we removing a meeting from the main activity list by pressing SUPPRIMER.
     */
    @Test
    public void myMeetingsList_deleteMeeting_shouldRemoveMeeting() {
        onView(allOf(withId(R.id.recyclerView), isDisplayed()));
        onView(withId(R.id.iv_cardview_delete_btn)).perform(click());
        onView(withText("SUPPRIMER")).perform(click());
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(matches(hasMinimumChildCount(0)));
    }

    /**
     * When we click create a meeting, if one field is empty or not valid we cannot validate.
     */
    @Test
    public void myMeetingsList_fieldMissing_shouldNotAddMeeting() {
        onView(withId(R.id.fab_add_meeting)).perform(click());
        onView(withId(R.id.add_meeting_content)).check(matches(isDisplayed()));
        onView(withId(R.id.button_save)).perform(click());
        onView(withId(R.id.add_meeting_content)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner_room)).perform(click());
        onView(withText("Salle D")).perform(click());
        onView(withId(R.id.button_save)).perform(click());
        onView(withId(R.id.add_meeting_content)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.button_save)).perform(click());
        onView(withId(R.id.add_meeting_content)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_time)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.button_save)).perform(click());
        onView(withId(R.id.add_meeting_content)).check(matches(isDisplayed()));
        onView(withId(R.id.text_input_topic)).perform(typeText("Projet 4"), closeSoftKeyboard());
        onView(withId(R.id.button_save)).perform(click());
        onView(withId(R.id.add_meeting_content)).check(matches(isDisplayed()));
        onView(withId(R.id.text_input_email)).perform(typeText("gallosgmail.com")).perform(pressImeActionButton());
        onView(withId(R.id.button_save)).perform(click());
        onView(withId(R.id.add_meeting_content)).check(matches(isDisplayed()));
    }

    /**
     * We ensure that our recyclerview is empty
     */
    @Test
    public void myMeetingsList_shouldBeEmpty() {
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(matches(hasMinimumChildCount(0)));
    }
}
