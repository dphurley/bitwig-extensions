package com.miditouchbar;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback;
import com.bitwig.extension.controller.api.*;
import com.bitwig.extension.controller.ControllerExtension;
import com.miditouchbar.devices.ValhallaDelay;

import java.util.List;

public class MIDITouchbarExtension extends ControllerExtension
{
   protected MIDITouchbarExtension(final MIDITouchbarExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   CursorTrack cursorTrack;

   ValhallaDelay valhallaDelay;

   @Override
   public void init()
   {
      final ControllerHost host = getHost();

      mTransport = host.createTransport();
      host.getMidiInPort(0).setMidiCallback((ShortMidiMessageReceivedCallback)msg -> onMidi0(msg));
      host.getMidiInPort(0).setSysexCallback((String data) -> onSysex0(data));

      TrackBank mainTrackBank = host.createMainTrackBank(4, 0, 0);

      cursorTrack = host.createCursorTrack("cursor track", "cursor track", 0, 0, true);
//      cursorTrack.pan().markInterested();
//      cursorTrack.pan().setIndication(true);

      valhallaDelay = new ValhallaDelay(host, cursorTrack);

      mainTrackBank.followCursorTrack(cursorTrack);

      // TODO: Perform your driver initialization here.
      // For now just show a popup notification for verification that it is running.

   }

   @Override
   public void exit()
   {
      // TODO: Perform any cleanup once the driver exits
      // For now just show a popup notification for verification that it is no longer running.
      getHost().showPopupNotification("MIDI Touchbar Exited");
   }

   @Override
   public void flush()
   {
      // TODO Send any updates you need here.
   }

   /** Called when we receive short MIDI message on port 0. */
   private void onMidi0(ShortMidiMessage msg)
   {
      int cc = msg.getData1();
      int value = msg.getData2();

      // set the mix value
//      Parameter valhallaMix = valhallaDelay.mappedParameters.get(ValhallaDelay.ParameterName.MIX);
//      valhallaMix.value().set(value, 128);

      Parameter valhallaDelayStyle = valhallaDelay.mappedParameters.get(ValhallaDelay.ParameterName.DELAY_STYLE);
      valhallaDelayStyle.value().set(value, 128);

      Parameter valhallaDelayLMilliseconds = valhallaDelay.mappedParameters.get(ValhallaDelay.ParameterName.DELAY_L_MS);
      valhallaDelayLMilliseconds.value().set(value, 128);
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
