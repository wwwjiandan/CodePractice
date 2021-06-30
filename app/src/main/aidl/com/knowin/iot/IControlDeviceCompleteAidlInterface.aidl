// IControlDeviceCompleteAidlInterface.aidl
package com.knowin.iot;

// Declare any non-default types here with import statements

interface IControlDeviceCompleteAidlInterface {
    void onControlDeviceComplete(String category, boolean open, int caseCode);
}