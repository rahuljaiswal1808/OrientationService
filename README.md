# OrientationService

Client needs to get an instance of OrientationServiceManager and register IOrientationCallback instance.

  private final IOrientationCallback mOrientationCallback = new IOrientationCallback.Stub() {

        @Override
        public void onUpdateOrientation(float[] vectors) throws RemoteException {
            //Handle vectors according to client requirment
        }
    };
    
   and register IOrientationCallback instance.
  
    orientationServiceManager.registerOrientationCallback(mOrientationCallback.asBinder());
    
   That's it.
