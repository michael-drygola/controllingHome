package com.wissolsoft.smarthouse.server;

public interface HomeController {

    enum LightsLocation {
        BATHROOM,
        KITCHEN,
        LIVING_ROOM_BACK,
        LIVING_ROOM_MAIN
    }

    enum ThermometerLocation {
        OUTER,
        BATHROOM
    }

    public void setLights(final LightsLocation lights, short value);

    public short getLights(final LightsLocation lights);

    public float getTemperature(final ThermometerLocation thermometer);

    //TODO doors
    //TODO other sensors
}
