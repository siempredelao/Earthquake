package com.futurice.earthquake.data.model;

public class FeatureEntity {

    private String           type;
    private PropertiesEntity properties;
    private GeometryEntity   geometry;
    private String           id;

    private FeatureEntity(Builder builder) {
        type = builder.type;
        properties = builder.properties;
        geometry = builder.geometry;
        id = builder.id;
    }

    public String getId() {
        return id;
    }

    public GeometryEntity getGeometry() {
        return geometry;
    }

    public PropertiesEntity getProperties() {
        return properties;
    }


    public static final class Builder {
        private String           type;
        private PropertiesEntity properties;
        private GeometryEntity   geometry;
        private String           id;

        public Builder() {
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withProperties(PropertiesEntity properties) {
            this.properties = properties;
            return this;
        }

        public Builder withGeometry(GeometryEntity geometry) {
            this.geometry = geometry;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public FeatureEntity build() {
            return new FeatureEntity(this);
        }
    }
}
