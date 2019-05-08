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

import java.util.ArrayList;
import java.util.List;

import static org.awaitility.Awaitility.await;
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
    List<Timer> timers;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        timerRepository = new TimerRepository(context);

        timers = new ArrayList<>();
        timers.add(new Timer("Sleep", "05:00:00", "2019-05-03", true, false, ""));
        timers.add(new Timer("Eat", "01:00:00", "2019-05-03", true, false, ""));
        timers.add(new Timer("Exercise", "01:00:00", "2019-05-03", true, false, ""));
        timers.add(new Timer("Study", "04:00:00", "2019-05-03", true, false, ""));
        timers.add(new Timer("Gaming", "06:00:00", "2019-05-03", true, false, ""));
        timers.add(new Timer("Rest", "04:00:00", "2019-05-03", true, false, ""));
        timers.add(new Timer("Other", "04:00:00", "2019-05-03", true, false, ""));

        timers.add(new Timer("Sleep", "08:00:00", "2019-05-04", true, false, ""));
        timers.add(new Timer("Eat", "00:30:00", "2019-05-04", true, false, ""));
        timers.add(new Timer("Exercise", "02:00:00", "2019-05-04", true, false, ""));
        timers.add(new Timer("Study", "05:00:00", "2019-05-04", true, false, ""));
        timers.add(new Timer("Gaming", "02:00:00", "2019-05-04", true, false, ""));
        timers.add(new Timer("Rest", "04:00:00", "2019-05-04", true, false, ""));
        timers.add(new Timer("Other", "02:30:00", "2019-05-04", true, false, ""));

        timers.add(new Timer("Sleep", "06:00:00", "2019-05-05", true, false, ""));
        timers.add(new Timer("Eat", "01:20:00", "2019-05-05", true, false, ""));
        timers.add(new Timer("Exercise", "01:00:00", "2019-05-05", true, false, ""));
        timers.add(new Timer("Study", "06:00:00", "2019-05-05", true, false, ""));
        timers.add(new Timer("Gaming", "02:00:00", "2019-05-05", true, false, ""));
        timers.add(new Timer("Rest", "04:00:00", "2019-05-05", true, false, ""));
        timers.add(new Timer("Other", "03:40:00", "2019-05-05", true, false, ""));

        timers.add(new Timer("Sleep", "07:00:00", "2019-05-06", true, false, ""));
        timers.add(new Timer("Eat", "01:30:00", "2019-05-06", true, false, ""));
        timers.add(new Timer("Exercise", "01:00:00", "2019-05-06", true, false, ""));
        timers.add(new Timer("Study", "07:00:00", "2019-05-06", true, false, ""));
        timers.add(new Timer("Gaming", "02:00:00", "2019-05-06", true, false, ""));
        timers.add(new Timer("Rest", "04:00:00", "2019-05-06", true, false, ""));
        timers.add(new Timer("Other", "01:30:00", "2019-05-06", true, false, ""));

        timers.add(new Timer("Sleep", "06:00:00", "2019-05-07", true, false, ""));
        timers.add(new Timer("Eat", "01:00:00", "2019-05-07", true, false, ""));
        timers.add(new Timer("Exercise", "02:00:00", "2019-05-07", true, false, ""));
        timers.add(new Timer("Study", "06:00:00", "2019-05-07", true, false, ""));
        timers.add(new Timer("Gaming", "02:00:00", "2019-05-07", true, false, ""));
        timers.add(new Timer("Rest", "04:00:00", "2019-05-07", true, false, ""));
        timers.add(new Timer("Other", "03:00:00", "2019-05-07", true, false, ""));


    }

    @Test
    public void insertTimersToDB() {
        timerRepository.nukeTable();
        await().until(() -> timerRepository.getAllTimers().size() == 0);

        for(Timer timer: timers)
            timerRepository.insertTask(timer);

        await().until(() ->  timerRepository.getAllTimers().size() >= timers.size());

        List<Timer> timersInDB = timerRepository.getFinishedTimers();

        assertEquals(timers.size(), timersInDB.size());
    }

    @Test
    public void nukeDBTable() {
        timerRepository.nukeTable();
        await().until(() -> timerRepository.getAllTimers().size() == 0);

        assertEquals(0, timerRepository.getAllTimers().size());
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
