package com.futurice.earthquake.data.model;

import java.util.List;

public class GetEarthquakesResponseEntity {

    private String              type;
    private MetadataEntity      metadata;
    private List<FeatureEntity> features;
    private List<Float>         bbox;

    private GetEarthquakesResponseEntity(Builder builder) {
        type = builder.type;
        metadata = builder.metadata;
        features = builder.features;
        bbox = builder.bbox;
    }

    public MetadataEntity getMetadata() {
        return metadata;
    }

    public List<FeatureEntity> getFeatures() {
        return features;
    }


    public static final class Builder {
        private String              type;
        private MetadataEntity      metadata;
        private List<FeatureEntity> features;
        private List<Float>         bbox;

        public Builder() {
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withMetadata(MetadataEntity metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder withFeatures(List<FeatureEntity> features) {
            this.features = features;
            return this;
        }

        public Builder withBbox(List<Float> bbox) {
            this.bbox = bbox;
            return this;
        }

        public GetEarthquakesResponseEntity build() {
            return new GetEarthquakesResponseEntity(this);
        }
    }
}
