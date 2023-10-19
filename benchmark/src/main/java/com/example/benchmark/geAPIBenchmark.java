package com.example.benchmark;

import androidx.benchmark.macro.CompilationMode;
import androidx.benchmark.macro.StartupMode;
import androidx.benchmark.macro.StartupTimingMetric;
import androidx.benchmark.macro.junit4.MacrobenchmarkRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

@RunWith(AndroidJUnit4.class)
public class geAPIBenchmark {

    @Rule
    public MacrobenchmarkRule mBenchmarkRule = new MacrobenchmarkRule();

    @Test
    public void getAPI() {
        mBenchmarkRule.measureRepeated(
                "com.example.videosharing",
                Collections.singletonList(new StartupTimingMetric()),
                CompilationMode.DEFAULT,
                StartupMode.COLD,
                5,
                scope -> {
                    scope.pressHome();
                    scope.startActivityAndWait();
                    return null;
                });
    }
}
