package br.com.vaichover;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.vaichover.ui.view.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OnChangeViewMode {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void goToMapsViewModeActivity() {
        // Navigate to gmaps view mode
        onView(withId(R.id.action_gmaps)).perform(click());

        // Navigate to list view mode
        onView(withId(R.id.action_list)).perform(click());

        // Navigate to preferences settings
        onView(withId(R.id.action_scale)).perform(click());
    }

}
