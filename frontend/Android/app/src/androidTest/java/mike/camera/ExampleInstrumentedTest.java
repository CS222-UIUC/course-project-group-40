package mike.camera;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Before
    public void startActivity() {
        // Start the MainActivity before each test
        startActivityHelper();
    }

//    @Rule
//    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("mike.camera", appContext.getPackageName());
    }

    @Test
    public void testCameraOpened() throws Throwable {
        onView(withId(R.id.button_camera)).perform(click());
        assertTrue(isCameraUsebyApp());
    }

    @Test
    public void testGalleryLaunch() {
        onView(withId(R.id.button_gallery)).perform(click());

        ActivityScenario<MainActivity> scenario = startActivityHelper();
        scenario.onActivity(
                activity -> {
                    onView(withId(R.id.button_gallery)).perform(click());
                    // Check to make sure that the right Intent is launched
                    Intents.init();
                    intended(hasComponent(GalleryActivity.class.getName()));
                });
    }

    public boolean isCameraUsebyApp() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            return true;
        } finally {
            if (camera != null) camera.release();
        }
        return false;
    }

    public static ActivityScenario<MainActivity> startActivityHelper() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        return scenario;
    }
}