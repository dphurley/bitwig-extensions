package com.faderfoxpc12.devices;

import com.bitwig.extension.controller.api.*;

import java.util.HashMap;
import java.util.Map;

public abstract class MappableDevice extends Mappable {
    public String bitwigDeviceId;
    public Device matchedDeviceFromBank;
    public SpecificPluginDevice specificDevice;
    public Map<String, Parameter> mappedParameters;
    private final int openWindowMidiChannel;
    private final int openWindowCC;

    public MappableDevice(String bitwigDeviceId, int openWindowMidiChannel, int openWindowCC, ControllerHost host, CursorTrack cursorTrack, Map<String, MappableParameter> mappableParameters) {
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
        for(String mappableParameterId : mappableParameters.keySet()) {
            MappableParameter mappableParameter = mappableParameters.get(mappableParameterId);
            Parameter parameter = this.specificDevice.createParameter(mappableParameter.bitwigParameterID);
            parameter.markInterested();
            parameter.setIndication(true);

            this.mappedParameters.put(mappableParameter.id, parameter);
        }

          // HELPER that will print all parameter names + bitwig values
        for(int i = 0; i <= 200; i++) {
            Parameter parameter = this.specificDevice.createParameter(i);
            parameter.name().markInterested();
            int finalI = i;
            parameter.name().addValueObserver((name) -> host.println(name + "x" + finalI));
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