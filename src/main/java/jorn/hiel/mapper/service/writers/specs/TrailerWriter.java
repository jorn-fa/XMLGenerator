package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Component
public class TrailerWriter implements DocWriter {

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite(VehicleSpec.TRAILER)) {

            Node rootElement = doc.getElementsByTagName("vehicle").item(0);
            {
                Element trailer = doc.createElement("trailer");
                rootElement.appendChild(trailer);
                Element trailerConfigurations = doc.createElement("trailerConfigurations");
                trailer.appendChild(trailerConfigurations);

                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfTrailerConfigurations").getValue());
                for (int x=0;x<needed;x++) {
                    Element trailerConfiguration = doc.createElement("trailerConfiguration");
                    Element trailerConfig = doc.createElement("trailer");
                    Element tipSide = doc.createElement("tipSide");
                    trailerConfig.appendChild(tipSide);
                    tipSide.setAttribute("dischargeNodeIndex",mapper.getMappedItem("disChargeNode"+x).getValue());
                    tipSide.setAttribute("name","l10n_info_tipSideBack");
                    Element animation = doc.createElement("animation");

                    String animName=configFileReader.getMappedItem("TipAnimation").getValue();
                    String unknown = configFileReader.getMappedItem("UnknownEntry").getValue();
                    if(animName.equals(unknown)){animName="tipAnimationBack";}
                    configFileReader.addAnimation(animName,"");

                    tipSide.appendChild(animation);
                    animation.setAttribute("name",animName);
                    animation.setAttribute("speedScale","1.0");
                    animation.setAttribute("startTipTime","0.0");



                    trailerConfigurations.appendChild(trailerConfiguration);
                    trailerConfiguration.appendChild(trailerConfig);

                }


            }
        }
    }
}
