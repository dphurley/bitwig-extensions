package com.faderfoxpc12.devices;

import com.bitwig.extension.controller.api.*;

import java.util.HashMap;
import java.util.Map;

public class ValhallaDelay extends MappableDevice {

    public static Map<String, MappableParameter> mappableParameters = new HashMap<String, MappableParameter>(){{
        put(id(0, 0), new MappableParameter("mix", 0, 0, 0));
    }};

    static String bitwigDeviceId = "565354644C617976616C68616C6C6164";
    static int openWindowMidiChannel = 0;
    static int openWindowCC = 77;

    public ValhallaDelay(ControllerHost host, CursorTrack cursorTrack) {
        super(bitwigDeviceId, openWindowMidiChannel, openWindowCC, host, cursorTrack, mappableParameters);
    }
}
