package com.futurice.earthquake.data.model;

public class PropertiesEntity {

    private Float   mag;
    private String  place;
    private Long    time;
    private Long    updated;
    private Integer tz;
    private String  url;
    private String  detail;
    private Integer felt;
    private Double  cdi;
    private Double  mmi;
    private String  alert;
    private String  status;
    private Integer tsunami;
    private Integer sig;
    private String  net;
    private String  code;
    private String  ids;
    private String  sources;
    private String  types;
    private Integer nst;
    private Float   dmin;
    private Float   rms;
    private Float   gap;
    private String  magType;
    private String  type;
    private String  title;

    private PropertiesEntity(Builder builder) {
        mag = builder.mag;
        place = builder.place;
        time = builder.time;
        updated = builder.updated;
        tz = builder.tz;
        url = builder.url;
        detail = builder.detail;
        felt = builder.felt;
        cdi = builder.cdi;
        mmi = builder.mmi;
        alert = builder.alert;
        status = builder.status;
        tsunami = builder.tsunami;
        sig = builder.sig;
        net = builder.net;
        code = builder.code;
        ids = builder.ids;
        sources = builder.sources;
        types = builder.types;
        nst = builder.nst;
        dmin = builder.dmin;
        rms = builder.rms;
        gap = builder.gap;
        magType = builder.magType;
        type = builder.type;
        title = builder.title;
    }

    public Long getTime() {
        return time;
    }

    public Float getMagnitude() {
        return mag;
    }

    public String getMagnitudeType() {
        return magType;
    }

    public String getPlace() {
        return place;
    }

    public String getUrl() {
        return url;
    }

    public String getAlert() {
        return alert;
    }

    public static final class Builder {
        private Float   mag;
        private String  place;
        private Long    time;
        private Long    updated;
        private Integer tz;
        private String  url;
        private String  detail;
        private Integer felt;
        private Double  cdi;
        private Double  mmi;
        private String  alert;
        private String  status;
        private Integer tsunami;
        private Integer sig;
        private String  net;
        private String  code;
        private String  ids;
        private String  sources;
        private String  types;
        private Integer nst;
        private Float   dmin;
        private Float   rms;
        private Float   gap;
        private String  magType;
        private String  type;
        private String  title;

        public Builder() {
        }

        public Builder withMag(Float mag) {
            this.mag = mag;
            return this;
        }

        public Builder withPlace(String place) {
            this.place = place;
            return this;
        }

        public Builder withTime(Long time) {
            this.time = time;
            return this;
        }

        public Builder withUpdated(Long updated) {
            this.updated = updated;
            return this;
        }

        public Builder withTz(Integer tz) {
            this.tz = tz;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withDetail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder withFelt(Integer felt) {
            this.felt = felt;
            return this;
        }

        public Builder withCdi(Double cdi) {
            this.cdi = cdi;
            return this;
        }

        public Builder withMmi(Double mmi) {
            this.mmi = mmi;
            return this;
        }

        public Builder withAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder withTsunami(Integer tsunami) {
            this.tsunami = tsunami;
            return this;
        }

        public Builder withSig(Integer sig) {
            this.sig = sig;
            return this;
        }

        public Builder withNet(String net) {
            this.net = net;
            return this;
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withIds(String ids) {
            this.ids = ids;
            return this;
        }

        public Builder withSources(String sources) {
            this.sources = sources;
            return this;
        }

        public Builder withTypes(String types) {
            this.types = types;
            return this;
        }

        public Builder withNst(Integer nst) {
            this.nst = nst;
            return this;
        }

        public Builder withDmin(Float dmin) {
            this.dmin = dmin;
            return this;
        }

        public Builder withRms(Float rms) {
            this.rms = rms;
            return this;
        }

        public Builder withGap(Float gap) {
            this.gap = gap;
            return this;
        }

        public Builder withMagType(String magType) {
            this.magType = magType;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public PropertiesEntity build() {
            return new PropertiesEntity(this);
        }
    }
}
