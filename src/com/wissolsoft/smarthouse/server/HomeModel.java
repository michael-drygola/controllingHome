package com.wissolsoft.smarthouse.server;

public class HomeModel {

    public static class Room {
        private int people;
        private boolean movement;
    }

    public static class Door {
        private final Room from;
        private final Room to;
        private boolean rayCrossed;
        private boolean open;
        public Door(final Room from, final Room to) {
            this.from = from;
            this.to = to;
        }
    }

}
