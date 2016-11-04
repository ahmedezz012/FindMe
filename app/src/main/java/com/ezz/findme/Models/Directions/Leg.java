
package com.ezz.findme.Models.Directions;

import java.util.ArrayList;
import java.util.List;

public class Leg {

    private Distance distance;
    private Duration duration;
    private String end_address;
    private End_location end_location;
    private String start_address;
    private Start_location start_location;
    private List<Step> steps = new ArrayList<Step>();
    private List<Object> traffic_speed_entry = new ArrayList<Object>();
    private List<Object> via_waypoint = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The distance
     */
    public Distance getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    /**
     * 
     * @return
     *     The duration
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return
     *     The end_address
     */
    public String getEnd_address() {
        return end_address;
    }

    /**
     * 
     * @param end_address
     *     The end_address
     */
    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    /**
     * 
     * @return
     *     The end_location
     */
    public End_location getEnd_location() {
        return end_location;
    }

    /**
     * 
     * @param end_location
     *     The end_location
     */
    public void setEnd_location(End_location end_location) {
        this.end_location = end_location;
    }

    /**
     * 
     * @return
     *     The start_address
     */
    public String getStart_address() {
        return start_address;
    }

    /**
     * 
     * @param start_address
     *     The start_address
     */
    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    /**
     * 
     * @return
     *     The start_location
     */
    public Start_location getStart_location() {
        return start_location;
    }

    /**
     * 
     * @param start_location
     *     The start_location
     */
    public void setStart_location(Start_location start_location) {
        this.start_location = start_location;
    }

    /**
     * 
     * @return
     *     The steps
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * 
     * @param steps
     *     The steps
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    /**
     * 
     * @return
     *     The traffic_speed_entry
     */
    public List<Object> getTraffic_speed_entry() {
        return traffic_speed_entry;
    }

    /**
     * 
     * @param traffic_speed_entry
     *     The traffic_speed_entry
     */
    public void setTraffic_speed_entry(List<Object> traffic_speed_entry) {
        this.traffic_speed_entry = traffic_speed_entry;
    }

    /**
     * 
     * @return
     *     The via_waypoint
     */
    public List<Object> getVia_waypoint() {
        return via_waypoint;
    }

    /**
     * 
     * @param via_waypoint
     *     The via_waypoint
     */
    public void setVia_waypoint(List<Object> via_waypoint) {
        this.via_waypoint = via_waypoint;
    }

}
