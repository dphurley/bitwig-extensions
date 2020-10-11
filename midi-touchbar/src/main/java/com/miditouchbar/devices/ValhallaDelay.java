package com.miditouchbar.devices;

import com.bitwig.extension.controller.api.*;

import java.util.HashMap;
import java.util.Map;

public class ValhallaDelay {

    public enum ParameterName {
        MIX, DELAY_STYLE, DELAY_L_MS
    }

    private static final Map<ParameterName, Integer> mappableParameters = new HashMap<ParameterName, Integer>() {{
        put(ParameterName.MIX, 0);
        put(ParameterName.DELAY_STYLE, 1);
        put(ParameterName.DELAY_L_MS, 4);
    }};

    public final Map<ParameterName, Parameter> mappedParameters;

    public ValhallaDelay(ControllerHost host, CursorTrack cursorTrack) {
        // make a matcher for each mappable device
        String valhallaDelayId = "565354644C617976616C68616C6C6164";
        DeviceMatcher valhallaDelayMatcher = host.createVST3DeviceMatcher(valhallaDelayId);

        // create a device bank with a single entry and filter out all other devices
        // on the selected track using the matcher
        DeviceBank valhallaDelayDeviceBank = cursorTrack.createDeviceBank(1);
        valhallaDelayDeviceBank.setDeviceMatcher(valhallaDelayMatcher);
        Device matchedDevice = valhallaDelayDeviceBank.getDevice(0);
        SpecificPluginDevice valhallaDelay = matchedDevice.createSpecificVst3Device(valhallaDelayId);

        mappedParameters = new HashMap<>();
        for(ParameterName parameterName : mappableParameters.keySet()) {
            Parameter parameter = valhallaDelay.createParameter(mappableParameters.get(parameterName));
            parameter.markInterested();
            parameter.setIndication(true);

            mappedParameters.put(parameterName, parameter);
        }
    }

}
