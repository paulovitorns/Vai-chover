package br.com.vaichover;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.vaichover.ui.view.activity.PreferencesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OnInteractWithPreferenceOptions {

    @Rule
    public ActivityTestRule<PreferencesActivity> mActivityRule = new ActivityTestRule<>(
            PreferencesActivity.class);

    @Test
    public void interactWithOption() {

        // Check celsius metric
        onView(withId(R.id.containerCheckCelsius)).perform(click());

        // Check celsius fahrenheit
        onView(withId(R.id.containerCheckFahrenheit)).perform(click());

        // Perfome click on clerar button
        onView(withId(R.id.iconClear)).perform(click());

        // type some text into edittext
        onView(withId(R.id.edtLocation)).perform(typeText("santana "));

        // select first item from recyclerview
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // drag seekbar to right
        onView(withId(R.id.rangeSeekbar)).perform(setPregress(45));

        // drag seekbar back to left
        onView(withId(R.id.rangeSeekbar)).perform(setPregress(25));

        // peform click on save button
        onView(withId(R.id.btnSave)).perform(click());

        // peform click on dialog button
        onView(withId(R.id.btnOk)).perform(click());
    }

    public static ViewAction setPregress(final int position){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(CrystalSeekbar.class);
            }

            @Override
            public String getDescription() {
                return "Set a progress";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((CrystalSeekbar) view).setMinStartValue(position).apply();
            }
        };
    }
}
