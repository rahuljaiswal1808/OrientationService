package com.jio.orientationservice;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class OrientationService extends Service implements SensorEventListener {

    private static final String TAG = "OrientationService";
    private SensorManager mSensorManager;
    private Sensor mRotationSensor;
    private OrientationImpl orientation;

    private static final int SENSOR_DELAY = 500 * 1000; // 500ms
    private static final int FROM_RADS_TO_DEGS = -57;

    private final List<IOrientationCallback> mOrientationCallbackContainer = new ArrayList<IOrientationCallback>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        orientation = new OrientationImpl();
    }

    public OrientationService() {
        try {
            mSensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
            mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mRotationSensor, SENSOR_DELAY);
        } catch (Exception e) {
            Log.e(TAG, e.getStackTrace().toString());
        }
    }

    class OrientationImpl extends  IOrientation.Stub {

        @Override
        public void registerOrientationCallback(IBinder binder) throws RemoteException {
            mOrientationCallbackContainer.add(IOrientationCallback.Stub.asInterface(binder));
        }

        @Override
        public void unregisterOrientationCallback(IBinder binder) throws RemoteException {
            mOrientationCallbackContainer.remove(IOrientationCallback.Stub.asInterface(binder));
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return orientation;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mRotationSensor) {
            if (event.values.length > 4) {
                float[] truncatedRotationVector = new float[4];
                System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4);
                update(truncatedRotationVector);
            } else {
                update(event.values);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void update(float[] vectors) {
        for(IOrientationCallback orientationCallback : mOrientationCallbackContainer) {
            try {
                orientationCallback.onUpdateOrientation(vectors);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
