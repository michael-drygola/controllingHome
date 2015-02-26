package com.wissolsoft.smarthouse.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HomeControllerImpl implements HomeController {

    private static final Log LOGGER = LogFactory.getLog(HomeControllerImpl.class);

    @Override
    public void setLights(LightsLocation lights, short value) {
        // TODO Auto-generated method stub
        LOGGER.debug("set lights " + lights + " to " + value);
    }

    @Override
    public short getLights(LightsLocation lights) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getTemperature(ThermometerLocation thermometer) {
        // TODO Auto-generated method stub
        return 0;
    }

}
