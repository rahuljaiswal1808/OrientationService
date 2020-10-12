// IOrientation.aidl
package com.jio.orientationservice;

// Declare any non-default types here with import statements

interface IOrientation {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void registerOrientationCallback(in IBinder binder);
    void unregisterOrientationCallback(in IBinder binder);
}
