package com.wissolsoft.smarthouse.server;

import com.wissolsoft.smarthouse.shared.LightsLocation;

public interface HomeController {

    enum ThermometerLocation {
        OUTER(1),
        BATHROOM(2);

        int id;

        private ThermometerLocation(int id) {
			this.id = id;
		}

        public int getId() {
			return id;
		}
    }

    public void setLights(final LightsLocation lights, short value) throws Exception;

    public short getLights(final LightsLocation lights);

    public float getTemperature(final ThermometerLocation thermometer);

    //TODO doors
    //TODO other sensors
}
