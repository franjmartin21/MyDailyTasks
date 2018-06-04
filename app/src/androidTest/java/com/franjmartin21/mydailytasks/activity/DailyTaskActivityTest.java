package com.franjmartin21.mydailytasks.activity;

import android.graphics.Paint;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.franjmartin21.mydailytasks.R;
import com.franjmartin21.util.MyDailyRecyclerView;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class DailyTaskActivityTest {

    private String mStringToBetyped;

    private static final String EMPTY_TEXT = "";

    private static final int ITEM_1_POSITION = 0;

    private static final String ITEM_1_TEXT = "This is the item 1";

    private static final int ITEM_2_POSITION = 1;

    private static final String ITEM_2_TEXT = "This is the item 2";

    @Rule
    public ActivityTestRule<DailyTasksActivity> mActivityRule = new ActivityTestRule<>(
            DailyTasksActivity.class);

    @BeforeClass
    public static void beforeClass() {
        InstrumentationRegistry.getTargetContext().deleteDatabase("mydailytask-db");
    }

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "Espresso";
    }

    public void testIsEmpty(){
        boolean isEmpty = false;
        try{
            onView(ViewMatchers.withId(R.id.et_new_task)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        } catch(Exception e){
            isEmpty = true;
        }
        if(!isEmpty) Assert.fail("The recycler view is not empty");
    }

    @Test
    public void testAddingQuickTaskToList_success(){
        testIsEmpty();

        //We add a first element and check that the field gets empty
        onView((withId(R.id.et_new_task))).check(matches(withText(EMPTY_TEXT)));
        onView((withId(R.id.et_new_task))).check(matches(withHint(R.string.daily_task_quick_tasks_hint)));
        onView((withId(R.id.et_new_task))).perform(typeText(ITEM_1_TEXT));
        onView((withId(R.id.btn_add_task_to_list))).perform(click());
        onView((withId(R.id.et_new_task))).check(matches(withText(EMPTY_TEXT)));

        onView(withId(R.id.rv_list_tasks)).check(matches(hasDescendant(withText(ITEM_1_TEXT))));

        //We add a first element and check that the field gets empty
        onView((withId(R.id.et_new_task))).perform(typeText(ITEM_2_TEXT));
        onView((withId(R.id.btn_add_task_to_list))).perform(click());
        onView((withId(R.id.et_new_task))).check(matches(withText(EMPTY_TEXT)));

        onView(withId(R.id.rv_list_tasks)).check(matches(hasDescendant(withText(ITEM_2_TEXT))));
    }
    @Test
    public void testClickingCheckboxItem_success(){
        testAddingQuickTaskToList_success();
        //onView(withId(R.id.rv_list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_1_POSITION, ClickViewAction.getInstanceForElement(R.id.cb_completed)));
        onView(withId(R.id.rv_list_tasks)).perform(MyDailyRecyclerView.atPosition(ITEM_1_POSITION).onElementId(R.id.cb_completed).actionClick());
        onView(withId(R.id.rv_list_tasks)).check(MyDailyRecyclerView.atPosition(ITEM_1_POSITION).onElementId(R.id.cb_completed).assertChecked(true));
        onView(withId(R.id.rv_list_tasks)).check(MyDailyRecyclerView.atPosition(ITEM_1_POSITION).onElementId(R.id.tv_tasktitle).assertPropertyValue("paintFlags", Paint.STRIKE_THRU_TEXT_FLAG));
        onView(withId(R.id.rv_list_tasks)).check(MyDailyRecyclerView.atPosition(ITEM_2_POSITION).onElementId(R.id.cb_completed).assertChecked(false));
        onView(withId(R.id.rv_list_tasks)).check(MyDailyRecyclerView.atPosition(ITEM_2_POSITION).onElementId(R.id.tv_tasktitle).assertNotPropertyValue("paintFlags", Paint.STRIKE_THRU_TEXT_FLAG));

    }

    public static class ClickViewAction implements ViewAction {

        private int id;

        private ClickViewAction(int id) {
            this.id = id;
        }

        private static ClickViewAction getInstanceForElement(int id){
            return new ClickViewAction(id);
        }

        @Override
        public Matcher<View> getConstraints() {
            return withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE);
        }

        @Override
        public String getDescription() {
            return "Click on a child view with specified id.";
        }

        @Override
        public void perform(UiController uiController, View view) {
            View v = view.findViewById(id);
            v.performClick();
        }
    }

    @Test
    public void testAddingQuickTaskToList_empty(){

    }
}
