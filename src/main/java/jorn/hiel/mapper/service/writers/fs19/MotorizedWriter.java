package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

@Component
public class MotorizedWriter implements DocWriter {

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite("motorized")) {

            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Element motorized = doc.createElement("motorized");
            rootElement.appendChild(motorized);

            //consumers

            Element consumerConfigurations = doc.createElement("consumerConfigurations");
            motorized.appendChild(consumerConfigurations);
            Element consumerConfiguration = doc.createElement("consumerConfiguration");
            consumerConfigurations.appendChild(consumerConfiguration);

            int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfConsumers").getValue());

            for (int x=0;x<needed;x++) {
                Element consumer = doc.createElement("consumer");
                List<String> sizeList = List.of("fillUnitIndex", "usage", "fillType");
                sizeList.forEach(a -> consumer.setAttribute(a, mapper.getMappedItem("unknownKey").getValue()));
                consumerConfiguration.appendChild(consumer);
            }

            //motorconfig
            Element motorConfigurations = doc.createElement("motorConfigurations");
            motorized.appendChild(motorConfigurations);
            Element motorConfiguration = doc.createElement("motorConfiguration");
            motorConfigurations.appendChild(motorConfiguration);
            motorConfiguration.setAttribute("name", mapper.getMappedItem("motorConfigName").getValue());
            motorConfiguration.setAttribute("hp", mapper.getMappedItem("motorConfigHp").getValue());
            motorConfiguration.setAttribute("price", mapper.getMappedItem("motorConfigPrice").getValue());
            motorConfiguration.setAttribute("speed", mapper.getMappedItem("motorConfigSpeed").getValue());

            Element motor = doc.createElement("motor");
            motorConfiguration.appendChild(motor);
            motor.setAttribute("name", mapper.getMappedItem("motorTorqueScale").getValue());
            motor.setAttribute("minRpm", mapper.getMappedItem("motorMinRpm").getValue());
            motor.setAttribute("maxRpm", mapper.getMappedItem("motorMaxRpm").getValue());
            motor.setAttribute("maxForwardSpeed", mapper.getMappedItem("motorForwardSpeed").getValue());
            motor.setAttribute("maxBackwardSpeed", mapper.getMappedItem("motorBackwardSpeed").getValue());
            motor.setAttribute("brakeForce", mapper.getMappedItem("motorBrakeForce").getValue());
            motor.setAttribute("lowBrakeForceScale", mapper.getMappedItem("motorBrakeForceScale").getValue());
            motor.setAttribute("rotInertia", mapper.getMappedItem("motorRotInertia").getValue());

            needed = Integer.parseInt(configFileReader.getMappedItem("numberOfTorq").getValue());

            for (int x=0;x<needed;x++) {
                Element torque = doc.createElement("torque");
                List<String> sizeList = List.of("normRpm", "torque");
                sizeList.forEach(a -> torque.setAttribute(a, mapper.getMappedItem("unknownKey").getValue()));
                motor.appendChild(torque);
            }


            Element transmission = doc.createElement("transmission");
            transmission.setAttribute("minForwardGearRatio", configFileReader.getMappedItem("motorMinForwardGearRatio").getValue());
            transmission.setAttribute("maxForwardGearRatio", configFileReader.getMappedItem("MotorMaxForwardGearRatio").getValue());
            transmission.setAttribute("minBackwardGearRatio", configFileReader.getMappedItem("motorMinBackwardGearRatio").getValue());
            transmission.setAttribute("maxBackwardGearRatio", configFileReader.getMappedItem("motorMaxBackwardGearRatio").getValue());
            motorConfiguration.appendChild(transmission);

            //differentials
            Element differentialConfigurations = doc.createElement("differentialConfigurations");
            motorized.appendChild(differentialConfigurations);
            Element differentialConfiguration = doc.createElement("differentialConfiguration");
            differentialConfigurations.appendChild(differentialConfiguration);
            Element differentials = doc.createElement("differentials");
            differentialConfiguration.appendChild(differentials);


            needed = Integer.parseInt(configFileReader.getMappedItem("numberOfDifferentials").getValue());
            for (int x=0;x<needed;x++) {
                Element differential = doc.createElement("differential");
                differential.setAttribute("torqueRatio", configFileReader.getMappedItem("DiffTorqueRatio").getValue());
                differential.setAttribute("maxSpeedRatio", configFileReader.getMappedItem("maxSpeedRatio").getValue());
                differential.setAttribute("wheelIndex1", configFileReader.getMappedItem("DiffTorqueRatio").getValue());
                differential.setAttribute("wheelIndex2", configFileReader.getMappedItem("DiffTorqueRatio").getValue());
                differentials.appendChild(differential);
            }




            Element motorStartDuration = doc.createElement("motorStartDuration");
            motorStartDuration.setTextContent(configFileReader.getMappedItem("motorStartDuration").getValue());
            motorized.appendChild(motorStartDuration);

            Element brakeCompressor = doc.createElement("brakeCompressor");
            brakeCompressor.setAttribute("capacity", configFileReader.getMappedItem("brakeCompressorCapacity").getValue());
            brakeCompressor.setAttribute("fillSpeed", configFileReader.getMappedItem("brakeCompressorFillSpeed").getValue());
            motorized.appendChild(brakeCompressor);

            Element exhaustEffects = doc.createElement("exhaustEffects");
            motorized.appendChild(exhaustEffects);

            needed = Integer.parseInt(configFileReader.getMappedItem("numberOfExhaust").getValue());

            for (int x=0;x<needed;x++) {
                Element exhaustEffect = doc.createElement("exhaustEffect");
                exhaustEffects.appendChild(exhaustEffect);
                exhaustEffect.setAttribute("node", configFileReader.getMappedItem("exhaust"+x).getValue());
                exhaustEffect.setAttribute("filename", configFileReader.getMappedItem("exhaustFile").getValue());
                exhaustEffect.setAttribute("minRpmColor", configFileReader.getMappedItem("exhaustMinRpmColor").getValue());
                exhaustEffect.setAttribute("maxRpmColor", configFileReader.getMappedItem("exhaustMaxRpmColor").getValue());
                exhaustEffect.setAttribute("minRpmScale", configFileReader.getMappedItem("exhaustMinRpmScale").getValue());
                exhaustEffect.setAttribute("maxRpmScale", configFileReader.getMappedItem("exhaustMaxRpmScale").getValue());
                exhaustEffect.setAttribute("xzRotationsOffset", configFileReader.getMappedItem("exhaustXzRotationsOffset").getValue());
                exhaustEffect.setAttribute("xzRotationsForward", configFileReader.getMappedItem("exhaustXzRotationsForward").getValue());
                exhaustEffect.setAttribute("xzRotationsBackward", configFileReader.getMappedItem("exhaustXzRotationsBackward").getValue());
                exhaustEffect.setAttribute("xzRotationsLeft", configFileReader.getMappedItem("exhaustXzRotationsLeft").getValue());
                exhaustEffect.setAttribute("xzRotationsRight", configFileReader.getMappedItem("exhaustXzRotationsRight").getValue());
            }

            Element sounds = doc.createElement("sounds");
            motorized.appendChild(sounds);
            sounds.setAttribute("externalSoundFile", mapper.getMappedItem("exhaustXzRotationsRight").getValue());




        }

    }



}
