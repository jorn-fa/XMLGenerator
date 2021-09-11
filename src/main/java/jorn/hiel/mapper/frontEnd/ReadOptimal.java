package jorn.hiel.mapper.frontEnd;

import javax.swing.*;

/**
 * @author Hiel Jorn
 *
 */
public interface ReadOptimal {



    /**
     * @param component
     * @returns width of component based on contents
     */
    public default int getOptimalWidth(JComponent component) {

        return (int) component.getPreferredSize().getWidth();
    }

}