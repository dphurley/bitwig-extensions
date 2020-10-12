package com.miditouchbar;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback;
import com.bitwig.extension.controller.api.*;
import com.bitwig.extension.controller.ControllerExtension;
import com.miditouchbar.devices.MappableDevice;
import com.miditouchbar.devices.MappableParameter;
import com.miditouchbar.devices.ValhallaDelay;

import java.util.ArrayList;
import java.util.List;

public class MIDITouchbarExtension extends ControllerExtension
{
   protected MIDITouchbarExtension(final MIDITouchbarExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }


   List<MappableDevice> mappedDevices;
   private void mapDevices(ControllerHost host, CursorTrack cursorTrack) {
      this.mappedDevices = new ArrayList<>();
      MappableDevice valhallaDelay = new ValhallaDelay(host, cursorTrack);
      mappedDevices.add(valhallaDelay);
   }

   @Override
   public void init()
   {
      final ControllerHost host = getHost();

      mTransport = host.createTransport();
      final MidiIn midiInPort = host.getMidiInPort(0);
      midiInPort.setMidiCallback((ShortMidiMessageReceivedCallback) this::onMidi0);
      midiInPort.setSysexCallback(this::onSysex0);

      TrackBank mainTrackBank = host.createMainTrackBank(1, 0, 0);

      CursorTrack cursorTrack = host.createCursorTrack("cursor track", "cursor track", 0, 0, true);
      mainTrackBank.followCursorTrack(cursorTrack);

      mapDevices(host, cursorTrack);

      // TODO this seems to have a performance issue with VSTs, will be nice to have in the future.
//      final HardwareSurface midiTouchbarHardwareSurface = host.createHardwareSurface();
//      final AbsoluteHardwareKnob firstSlider = midiTouchbarHardwareSurface.createAbsoluteHardwareKnob("SLIDER_1");
//
//      int midiChannel = 0; // MIDI channel 1
//      firstSlider.setAdjustValueMatcher(midiInPort.createAbsoluteCCValueMatcher(midiChannel, 0));
//      Parameter valhallaDelayMix = valhallaDelay.mappedParameters.get(ValhallaDelay.ParameterName.MIX);
//      firstSlider.setBinding(valhallaDelayMix);

      getHost().showPopupNotification("MIDI Touchbar Initialized");
   }

   @Override
   public void exit()
   {
      getHost().showPopupNotification("MIDI Touchbar Exited");
   }

   @Override
   public void flush()
   {
      // TODO Send any updates you need here.
   }

   /** Called when we receive short MIDI message on port 0. */
   private void onMidi0(ShortMidiMessage midiMessage)
   {
      int midiChannel = midiMessage.getChannel();
      int cc = midiMessage.getData1();
      int value = midiMessage.getData2();

      boolean acked;
      for(MappableDevice mappedDevice : mappedDevices) {
         acked = mappedDevice.setParameter(midiChannel, cc, value);

         if(acked) {
            return;
         }
      }
   }

   /** Called when we receive sysex MIDI message on port 0. */
   private void onSysex0(final String data) 
   {
      // MMC Transport Controls:
      if (data.equals("f07f7f0605f7"))
            mTransport.rewind();
      else if (data.equals("f07f7f0604f7"))
            mTransport.fastForward();
      else if (data.equals("f07f7f0601f7"))
            mTransport.stop();
      else if (data.equals("f07f7f0602f7"))
            mTransport.play();
      else if (data.equals("f07f7f0606f7"))
            mTransport.record();
   }

   private Transport mTransport;
}
