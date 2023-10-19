package com.example.videosharing;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.videosharing", appContext.getPackageName());
    }

          //  @Test
         //   public void correctLinkReturnsTrue() {
           //     String result = YoutubePlayer.youTubeLinkWithoutProtocolAndDomain("https://www.youtube.com/watch?v=6nda3wakNn4");
         //       String expected = "https://www.youtube.com/watch?v=6nda3wakNn4";

           //     assertEquals(expected, result);
       //     }

         //   @Test
       //     public void incorrectLinkReturnsEmpty() {
        //        String result = YoutubePlayer.youTubeLinkWithoutProtocolAndDomain("https://www.youttube.com/watch?v=6nda3wakNn4");
        //        String expected = "";

          //      assertEquals(expected, result);
         //   }
}