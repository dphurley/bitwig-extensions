package com.miditouchbar.devices;

import com.bitwig.extension.controller.api.*;

import java.util.HashMap;
import java.util.Map;

enum MappedParameterType {
    BITWIG_DEVICE_PARAMETER,
    PLUGIN_PARAMETER
}

class MappedParameter {
    private MappedParameterType type;
    private Parameter pluginParameter;
    private Device bitwigDevice;

    public MappedParameter(MappedParameterType type) {
        this.type = type;
    }

    public void setValue(int value) {
        if(this.type == MappedParameterType.BITWIG_DEVICE_PARAMETER) {

        } else {

        }
    }
}

public abstract class MappableDevice {
    public String bitwigDeviceId;
    public Device matchedDeviceFromBank;
    public SpecificPluginDevice specificDevice;
    public Map<String, Parameter> mappedParameters;
    private int openWindowMidiChannel;
    private int openWindowCC;

    static String id(int midiChannel, int cc) {
        return midiChannel + "-" + cc;
    }

    public MappableDevice(String bitwigDeviceId, int openWindowMidiChannel, int openWindowCC, ControllerHost host, CursorTrack cursorTrack) {
        this.bitwigDeviceId = bitwigDeviceId;
        this.openWindowMidiChannel = openWindowMidiChannel;
        this.openWindowCC = openWindowCC;

        // make a matcher for each mappable device
        DeviceMatcher deviceIdMatcher = host.createVST3DeviceMatcher(this.bitwigDeviceId);

        // create a device bank with a single entry and filter out all other devices
        // on the selected track using the matcher
        DeviceBank singleMatchedDeviceBank = cursorTrack.createDeviceBank(1);
        singleMatchedDeviceBank.setDeviceMatcher(deviceIdMatcher);
        this.matchedDeviceFromBank = singleMatchedDeviceBank.getDevice(0);
        this.specificDevice = matchedDeviceFromBank.createSpecificVst3Device(this.bitwigDeviceId);

        this.mappedParameters = new HashMap<>();
        for(ValhallaDelay.MappableParameters mappableParameter : ValhallaDelay.MappableParameters.values()) {
            Parameter parameter = this.specificDevice.createParameter(mappableParameter.bitwigParameterID);
            parameter.markInterested();
            parameter.setIndication(true);

            this.mappedParameters.put(mappableParameter.id, parameter);
        }
    }

    private void toggleWindow() {
        this.matchedDeviceFromBank.isWindowOpen().toggle();
    }

    public boolean setParameter(int midiChannel, int cc, int value) {
        boolean shouldToggleWindow = (
            midiChannel == this.openWindowMidiChannel
            && cc == this.openWindowCC
            && value == 127
        );

        if(shouldToggleWindow) {
            toggleWindow();
            return true;
        }

        Parameter parameterToSet = this.mappedParameters.get(id(midiChannel, cc));
        if(parameterToSet != null) {
            parameterToSet.value().set(value, 128);
            return true;
        }

        return false;
    }
}
