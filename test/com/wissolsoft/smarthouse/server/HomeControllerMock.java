package com.wissolsoft.smarthouse.server;

import java.util.HashMap;
import java.util.Map;

public class HomeControllerMock implements HomeController {

    private final Map<LightsLocation, Short> lights = new HashMap<HomeController.LightsLocation, Short>();

    @Override
    public void setLights(LightsLocation lightsLocation, short value) {
        this.lights.put(lightsLocation, value);
    }

    @Override
    public short getLights(LightsLocation lights) {
        return this.lights.get(lights);
    }

    @Override
    public float getTemperature(ThermometerLocation thermometer) {
        // TODO Auto-generated method stub
        return 0;
    }

}
