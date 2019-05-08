package com.tvt11.timemanagingapp;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.tvt11.timemanagingapp.model.Timer;
import com.tvt11.timemanagingapp.repo.TimerRepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Context context;
    TimerRepository timerRepository;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        timerRepository = new TimerRepository(context);
    }

    @Test
    public void insertTimers() {
        //code
    }

    @Ignore
    public void testDB1() {
        List<Timer> timers = timerRepository.getScheduledTimers();

        assertEquals(10, timers.size());
    }

    @Ignore
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.tvt11.timemanagingapp", appContext.getPackageName());
    }

    @Ignore
    public void createTimer() {
        //
    }
}
