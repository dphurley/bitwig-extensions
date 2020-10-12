package com.faderfoxpc12;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class FaderfoxPC12ExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("479f82c4-1e97-4943-ad4a-d59a293a296c");
   
   public FaderfoxPC12ExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "Faderfox PC12";
   }
   
   @Override
   public String getAuthor()
   {
      return "Danny Hurley";
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
      return "Faderfox";
   }
   
   @Override
   public String getHardwareModel()
   {
      return "Faderfox PC12";
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
         list.add(new String[]{"Faderfox PC12"}, new String[]{"Faderfox PC12"});
      }
      else if (platformType == PlatformType.MAC)
      {
         list.add(new String[]{"Faderfox PC12"}, new String[]{"Faderfox PC12"});
      }
      else if (platformType == PlatformType.LINUX)
      {
          list.add(new String[]{"Faderfox PC12"}, new String[]{"Faderfox PC12"});
      }
   }

   @Override
   public FaderfoxPC12Extension createInstance(final ControllerHost host)
   {
      return new FaderfoxPC12Extension(this, host);
   }
}
