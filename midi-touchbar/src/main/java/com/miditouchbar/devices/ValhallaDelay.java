package com.miditouchbar.devices;

import com.bitwig.extension.controller.api.*;

public class ValhallaDelay extends MappableDevice {

    public enum MappableParameters {
        MIX(0, 0, 0),
        DELAY_STYLE(0, 1, 1);

        public final String id;
        public final int midiChannel;
        public final int cc;
        public final int bitwigParameterID;

        MappableParameters(int midiChannel, int cc, int bitwigParameterID) {
            this.id = id(midiChannel, cc);
            this.midiChannel = midiChannel;
            this.cc = cc;
            this.bitwigParameterID = bitwigParameterID;
        }
    }

    static String bitwigDeviceId = "565354644C617976616C68616C6C6164";
    static int openWindowMidiChannel = 0;
    static int openWindowCC = 36;

    public ValhallaDelay(ControllerHost host, CursorTrack cursorTrack) {
        super(bitwigDeviceId, openWindowMidiChannel, openWindowCC, host, cursorTrack);
    }
}
