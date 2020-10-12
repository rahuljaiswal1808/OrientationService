// IOrientationCallback.aidl
package com.jio.orientationservice;

// Declare any non-default types here with import statements

interface IOrientationCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onUpdateOrientation(in float[] vectors);
}
