package com.franjmartin21.util;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import org.hamcrest.Matcher;
import org.junit.Assert;

import java.lang.reflect.Method;

import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

public class MyDailyRecyclerView {

    private int position;

    private int elementId;

    private MyDailyRecyclerView INSTANCE;

    private MyDailyRecyclerView(int position){
        this.position = position;
    }

    public static MyDailyRecyclerView atPosition(int position){
        MyDailyRecyclerView MyDailyRecyclerView = new MyDailyRecyclerView(position);
        return MyDailyRecyclerView;
    }

    public MyDailyRecyclerView onElementId(int elementId){
        this.elementId = elementId;
        return this;
    }

    private View getElementInRecyclerView(View view, NoMatchingViewException e){
        if (!(view instanceof RecyclerView)) {
            throw e;
        }
        RecyclerView rv = (RecyclerView) view;
        View viewElement= rv.findViewHolderForAdapterPosition(position).itemView.findViewById(elementId);
        return viewElement;
    }

    public ViewAssertion assertChecked(final boolean isChecked) {
        return new ViewAssertion() {
            @Override public void check(View view, NoMatchingViewException e) {
                View viewElement = getElementInRecyclerView(view, e);
                if (!(viewElement instanceof CheckBox)) {
                    throw e;
                }
                Assert.assertThat(viewElement, isChecked ? isChecked(): isNotChecked());
            }
        };
    }

    public ViewAssertion assertPropertyValue(final String property, final Object value){
        return new ViewAssertion() {
            @Override public void check(View view, NoMatchingViewException e) {
                View viewElement = getElementInRecyclerView(view, e);
                Object valueProp = getProperty(viewElement, property);
                Assert.assertEquals(value, valueProp);
            }
        };
    }

    public ViewAssertion assertNotPropertyValue(final String property, final Object value){
        return new ViewAssertion() {
            @Override public void check(View view, NoMatchingViewException e) {
                View viewElement = getElementInRecyclerView(view, e);
                Object valueProp = getProperty(viewElement, property);
                Assert.assertNotEquals(value, valueProp);
            }
        };
    }


    public ViewAction actionClick(){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE);
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                CheckBox checkBox = ((RecyclerView)view).findViewHolderForAdapterPosition(position).itemView.findViewById(elementId);
                checkBox.performClick();
            }
        };
    }

    public static Object getProperty(Object obj, String property) {
        Object returnValue = null;

        try {
            String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
            Class clazz = obj.getClass();
            Method method = clazz.getMethod(methodName, null);
            returnValue = method.invoke(obj, null);
        }
        catch (Exception e) {
            // Do nothing, we'll return the default value
        }
        return returnValue;
    }
}
