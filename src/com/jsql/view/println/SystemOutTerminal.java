package com.jsql.view.println;
/*******************************************************************************
 * Copyhacked (H) 2012-2014.
 * This program and the accompanying materials
 * are made available under no term at all, use it like
 * you want, but share and discuss about it
 * every time possible with every body.
 * 
 * Contributors:
 *      ron190 at ymail dot com - initial implementation
 ******************************************************************************/

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import com.jsql.model.bean.Request;
import com.jsql.model.injection.MediatorModel;
import com.jsql.view.swing.interaction.IInteractionCommand;

/**
 * View in the MVC pattern, defines all the components
 * and process actions sent by the model.<br>
 * Main groups of components:<br>
 * - at the top: textfields input,<br>
 * - at the center: tree on the left, table on the right,<br>
 * - at the bottom: information labels.
 */
@SuppressWarnings("serial")
public class SystemOutTerminal implements Observer {
    /**
     * Log4j logger sent to view.
     */
    private static final Logger LOGGER = Logger.getLogger(SystemOutTerminal.class);

    /**
     * Build the GUI: add app icon, tree icons, the 3 main panels.
     */
    public SystemOutTerminal() {
        // Register the view to the model
        MediatorModel.model().addObserver(this);
    }

    /**
     * Observer pattern.<br>
     * Receive an update order from the model:<br>
     * - Use the Request message to get the Interaction class,<br>
     * - Pass the parameters to that class.
     */
    @Override
    public void update(Observable model, Object newInteraction) {
        Request interaction = (Request) newInteraction;

        try {
            Class<?> cl = Class.forName("com.jsql.view.println.interaction." + interaction.getMessage());
            Class<?>[] types = new Class[]{Object[].class};
            Constructor<?> ct = cl.getConstructor(types);

            IInteractionCommand o2 = (IInteractionCommand) ct.newInstance(new Object[]{interaction.getParameters()});
            o2.execute();
        } catch (ClassNotFoundException e) {
//            LOGGER.error(e, e);
        } catch (InstantiationException e) {
            LOGGER.error(e, e);
        } catch (IllegalAccessException e) {
            LOGGER.error(e, e);
        } catch (NoSuchMethodException e) {
            LOGGER.error(e, e);
        } catch (SecurityException e) {
            LOGGER.error(e, e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e, e);
        } catch (InvocationTargetException e) {
            LOGGER.error(e, e);
        }
    }
}
