package com.faderfoxpc12.devices;

public class MappableParameter extends Mappable {
    public final String id;
    public final String name;
    public final int midiChannel;
    public final int cc;
    public final int bitwigParameterID;

    public MappableParameter(String name, int midiChannel, int cc, int bitwigParameterID) {
        this.id = id(midiChannel, cc);
        this.name = name;
        this.midiChannel = midiChannel;
        this.cc = cc;
        this.bitwigParameterID = bitwigParameterID;
    }
}
