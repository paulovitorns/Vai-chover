package br.com.vaichover;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.vaichover.ui.view.activity.PreferencesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkArgument;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

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

        // focus in location Editable
        onView(withId(R.id.edtLocation)).perform(click());

        // type some text into edittext
        onView(withId(R.id.edtLocation)).perform(typeText("santana "), closeSoftKeyboard());

        // select some item
        onView(withItemText("Santana, São Paulo")).perform(click());
    }

    public Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText),"cannot be null");
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return allOf(isDescendantOfA(isAssignableFrom(RecyclerView.class)),withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is descendant of a RecyclerView with text" + itemText);
            }
        };
    }
}
