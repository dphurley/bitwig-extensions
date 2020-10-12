package com.faderfoxpc12.devices;

import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorTrack;

import java.util.HashMap;
import java.util.Map;

public class Pigments extends MappableDevice {

    public static Map<String, MappableParameter> mappableParameters = new HashMap<String, MappableParameter>(){{
         put(id(0, 28), new MappableParameter("Env VCA Attack", 0, 28, 5));
         put(id(0, 40), new MappableParameter("Env VCA Decay", 0, 40, 6));
         put(id(0, 52), new MappableParameter("Env VCA Sustain", 0, 52, 7));
         put(id(0, 92), new MappableParameter("Env VCA Release", 0, 92, 8));
         put(id(1, 28), new MappableParameter("Env 2 Attack", 1, 28, 14));
         put(id(1, 40), new MappableParameter("Env 2 Decay", 1, 40, 15));
         put(id(1, 52), new MappableParameter("Env 2 Sustain", 1, 52, 16));
         put(id(1, 92), new MappableParameter("Env 2 Release", 1, 92, 17));
         put(id(0, 37), new MappableParameter("Wavetable 1 Main Vol", 0, 37, 81));
         put(id(0, 91), new MappableParameter("Wavetable 1 Coarse", 0, 91, 83));
         put(id(1, 91), new MappableParameter("Wavetable 1 Fine", 1, 91, 84));
         put(id(0, 13), new MappableParameter("Wavetable 1 Position", 0, 13, 85));
         put(id(1, 13), new MappableParameter("Wavetable 1 FM Amount", 1, 13, 89));
         put(id(0, 25), new MappableParameter("Wavetable 1 Phase Distortion", 0, 25, 94));
         put(id(1, 25), new MappableParameter("Wavetable 1 Fold", 1, 25, 96));
         put(id(0, 51), new MappableParameter("Wavetable 1 Unison Voices", 0, 51, 99));
    }};

    static String bitwigDeviceId = "41727475415649534B61743150726F63";
    static int openWindowMidiChannel = 0;
    static int openWindowCC = 76;

    public Pigments(ControllerHost host, CursorTrack cursorTrack) {
        super(bitwigDeviceId, openWindowMidiChannel, openWindowCC, host, cursorTrack, mappableParameters);
    }
}
