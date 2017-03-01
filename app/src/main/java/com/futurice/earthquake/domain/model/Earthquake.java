package com.futurice.earthquake.domain.model;

public class Earthquake {

    private String id;
    private Float  latitude;
    private Float  longitude;
    private Float  depth;
    private Long   timestamp;
    private Float  magnitude;
    private String magnitudeType;
    private String place;
    private String url;
    private String alert;

    private Earthquake(Builder builder) {
        setId(builder.id);
        setLatitude(builder.latitude);
        setLongitude(builder.longitude);
        setDepth(builder.depth);
        setTimestamp(builder.timestamp);
        setMagnitude(builder.magnitude);
        setMagnitudeType(builder.magnitudeType);
        setPlace(builder.place);
        setUrl(builder.url);
        setAlert(builder.alert);
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public void setDepth(Float depth) {
        this.depth = depth;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMagnitude(Float magnitude) {
        this.magnitude = magnitude;
    }

    public void setMagnitudeType(String magnitudeType) {
        this.magnitudeType = magnitudeType;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getPlace() {
        return place;
    }

    public Float getMagnitude() {
        return magnitude;
    }

    public String getMagnitudeType() {
        return magnitudeType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getAlert() {
        return alert;
    }

    public String getId() {
        return id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Float getDepth() {
        return depth;
    }

    public String getUrl() {
        return url;
    }


    public static final class Builder {

        private String id;
        private Float  latitude;
        private Float  longitude;
        private Float  depth;
        private Long   timestamp;
        private Float  magnitude;
        private String magnitudeType;
        private String place;
        private String url;
        private String alert;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withLatitude(Float latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Float longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withDepth(Float depth) {
            this.depth = depth;
            return this;
        }

        public Builder withTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withMagnitude(Float magnitude) {
            this.magnitude = magnitude;
            return this;
        }

        public Builder withMagnitudeType(String magnitudeType) {
            this.magnitudeType = magnitudeType;
            return this;
        }

        public Builder withPlace(String place) {
            this.place = place;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public Earthquake build() {
            return new Earthquake(this);
        }
    }
}
