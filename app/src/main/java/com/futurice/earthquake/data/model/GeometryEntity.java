package com.futurice.earthquake.data.model;

import java.util.List;

public class GeometryEntity {

    private String      type;
    private List<Float> coordinates;

    private GeometryEntity(Builder builder) {
        type = builder.type;
        coordinates = builder.coordinates;
    }

    public Float getLatitude() {
        return coordinates.get(1);
    }

    public Float getLongitude() {
        return coordinates.get(0);
    }

    public Float getDepth() {
        return coordinates.get(2);
    }


    public static final class Builder {
        private String      type;
        private List<Float> coordinates;

        public Builder() {
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withCoordinates(List<Float> coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public GeometryEntity build() {
            return new GeometryEntity(this);
        }
    }
}
