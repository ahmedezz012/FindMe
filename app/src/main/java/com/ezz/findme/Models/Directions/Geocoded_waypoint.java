
package com.ezz.findme.Models.Directions;

import java.util.ArrayList;
import java.util.List;

public class Geocoded_waypoint {

    private String geocoder_status;
    private String place_id;
    private List<String> types = new ArrayList<String>();

    /**
     * 
     * @return
     *     The geocoder_status
     */
    public String getGeocoder_status() {
        return geocoder_status;
    }

    /**
     * 
     * @param geocoder_status
     *     The geocoder_status
     */
    public void setGeocoder_status(String geocoder_status) {
        this.geocoder_status = geocoder_status;
    }

    /**
     * 
     * @return
     *     The place_id
     */
    public String getPlace_id() {
        return place_id;
    }

    /**
     * 
     * @param place_id
     *     The place_id
     */
    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    /**
     * 
     * @return
     *     The types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * 
     * @param types
     *     The types
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

}
