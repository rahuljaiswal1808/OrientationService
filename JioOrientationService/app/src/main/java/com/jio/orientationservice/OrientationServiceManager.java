package com.jio.orientationservice;

import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.NonNull;

public class OrientationServiceManager {
    private final IOrientation mService;

    public OrientationServiceManager(IBinder service) {
        mService = IOrientation.Stub.asInterface(service);
    }

    public void registerOrientationCallback(@NonNull IBinder binder) throws RemoteException {
        mService.registerOrientationCallback(binder);
    }

    public void unregisterOrientationCallback(@NonNull IBinder binder) throws RemoteException {
        mService.unregisterOrientationCallback(binder);
    }

}
