package com.alekseiivhsin.taskmanager.shadows;

import com.octo.android.robospice.SpiceManager;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;

/**
 * Created by Aleksei Ivshin
 * on 24.01.2016.
 */
public class MyRobolectricRunner extends RobolectricGradleTestRunner {
    public MyRobolectricRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public InstrumentationConfiguration createClassLoaderConfig() {
        InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
        builder.addInstrumentedClass(SpiceManager.class.getName());
        return builder.build();
    }
}
