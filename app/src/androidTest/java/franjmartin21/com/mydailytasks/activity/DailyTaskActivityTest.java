package franjmartin21.com.mydailytasks.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DailyTaskActivityTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<DailyTasksActivity> mActivityRule = new ActivityTestRule<>(
            DailyTasksActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "Espresso";
    }

    @Test
    public void testToPerform(){

    }
}
