package jorn.hiel.mapper.service.managers;

import jorn.hiel.mapper.service.interfaces.PropertyChangeWithEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeSupport;

@Slf4j
public class BasicManager implements PropertyChangeWithEnum {

    @Getter
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    protected String oldValue = "";


    @Override
    public <E, V, V1> void fireEnumPropertyChange(E insertEnum, V oldValue, V1 newValue) {
        if (newValue != null) {
            log.debug("firing : " + insertEnum + " " + oldValue + " " + newValue);

            pcs.firePropertyChange(insertEnum.toString(), oldValue, newValue);
        }

    }


    protected void requestUpdate(Object o) {
        //o.getClass();
        // lazy, method signature to single line + getClass does nothing in this case,
        // removes code smell from sonarcube
        requestUpdate();

    }

    public void requestUpdate() {
        //cannot abstract due to implemented code above
    }


}