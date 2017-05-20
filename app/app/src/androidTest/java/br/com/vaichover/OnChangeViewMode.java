package br.com.vaichover;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.vaichover.ui.view.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
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

        // Scroll to bottom
        onView(withId(R.id.recyclerView)).perform(goToPosition());

        // Scroll back to top
        onView(withId(R.id.recyclerView)).perform(scrollToPosition(0));

        // Navigate to gmaps view mode
        onView(withId(R.id.action_gmaps)).perform(click());

        // peform click into some marker
        onView(withContentDescription("Google Maps")).perform(click());

        // peform click on request location
        onView(withId(R.id.fabGoToLocation)).perform(click());

        // peform click into some marker
        onView(withContentDescription("Google Maps")).perform(click());

        // Navigate to list view mode
        onView(withId(R.id.action_list)).perform(click());

        // Scroll to bottom
        onView(withId(R.id.recyclerView)).perform(goToPosition());

        // Scroll back to top
        onView(withId(R.id.recyclerView)).perform(scrollToPosition(0));

        // Navigate to preferences settings
        onView(withId(R.id.action_scale)).perform(click());
    }

    public ViewAction goToPosition(){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "set scroll to";
            }

            @Override
            public void perform(UiController uiController, View view) {
                int itens = ((RecyclerView) view).getChildCount();
                RecyclerViewActions.scrollToPosition(itens - 1);
            }
        };
    }

}
