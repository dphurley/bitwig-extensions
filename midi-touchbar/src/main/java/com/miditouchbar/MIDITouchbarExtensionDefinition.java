package com.miditouchbar;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class MIDITouchbarExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("65c22e4b-f68b-4bc0-9b6a-83d2bb3d3ec8");
   
   public MIDITouchbarExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "MIDI Touchbar";
   }
   
   @Override
   public String getAuthor()
   {
      return "dphurley";
   }

   @Override
   public String getVersion()
   {
      return "0.1";
   }

   @Override
   public UUID getId()
   {
      return DRIVER_ID;
   }
   
   @Override
   public String getHardwareVendor()
   {
      return "MIDI Touchbar";
   }
   
   @Override
   public String getHardwareModel()
   {
      return "MIDI Touchbar";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 12;
   }

   @Override
   public int getNumMidiInPorts()
   {
      return 1;
   }

   @Override
   public int getNumMidiOutPorts()
   {
      return 1;
   }

   @Override
   public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
   {
      if (platformType == PlatformType.WINDOWS)
      {
         list.add(new String[]{"MIDI Touchbar User"}, new String[]{"MIDI Touchbar User"});
      }
      else if (platformType == PlatformType.MAC)
      {
         list.add(new String[]{"MIDI Touchbar User"}, new String[]{"MIDI Touchbar User"});
      }
      else if (platformType == PlatformType.LINUX)
      {
          list.add(new String[]{"MIDI Touchbar User"}, new String[]{"MIDI Touchbar User"});
      }
   }

   @Override
   public MIDITouchbarExtension createInstance(final ControllerHost host)
   {
      return new MIDITouchbarExtension(this, host);
   }
}
