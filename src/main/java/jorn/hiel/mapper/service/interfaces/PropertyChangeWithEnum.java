package jorn.hiel.mapper.service.interfaces;
/**
 * @author Hiel Jorn
 *
 */
public interface PropertyChangeWithEnum {

    public <E, V, V1> void fireEnumPropertyChange(E insertEnum, V oldValue, V1 newValue);

}