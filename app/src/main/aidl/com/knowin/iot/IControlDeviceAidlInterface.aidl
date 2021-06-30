// IControlDeviceAidlInterface.aidl
package com.knowin.iot;

import com.knowin.iot.IControlDeviceCompleteAidlInterface;

// Declare any non-default types here with import statements

interface IControlDeviceAidlInterface {

    int controlRoomDevice(String category, boolean open);

    void registerControlDeviceListener(IControlDeviceCompleteAidlInterface listener);
    void unregisterControlDeviceListener(IControlDeviceCompleteAidlInterface listener);
}