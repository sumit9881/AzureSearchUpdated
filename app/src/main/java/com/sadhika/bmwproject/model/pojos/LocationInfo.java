package com.sadhika.bmwproject.model.pojos;

/**
 * Created by Sadhika on 7/2/17.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationInfo implements Parcelable {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("ArrivalTime")
    @Expose
    private String arrivalTime;
    public final static Parcelable.Creator<LocationInfo> CREATOR = new Creator<LocationInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LocationInfo createFromParcel(Parcel in) {
            LocationInfo instance = new LocationInfo();
            instance.iD = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.latitude = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.longitude = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.address = ((String) in.readValue((String.class.getClassLoader())));
            instance.arrivalTime = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public LocationInfo[] newArray(int size) {
            return (new LocationInfo[size]);
        }

    };

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(iD);
        dest.writeValue(name);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(address);
        dest.writeValue(arrivalTime);
    }

    public int describeContents() {
        return 0;
    }

}

