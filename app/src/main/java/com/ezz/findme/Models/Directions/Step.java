
package com.ezz.findme.Models.Directions;


public class Step {

    private Distance distance;
    private Duration duration;
    private End_location end_location;
    private String html_instructions;
    private Polyline polyline;
    private Start_location start_location;
    private String travel_mode;

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
     *     The html_instructions
     */
    public String getHtml_instructions() {
        return html_instructions;
    }

    /**
     * 
     * @param html_instructions
     *     The html_instructions
     */
    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    /**
     * 
     * @return
     *     The polyline
     */
    public Polyline getPolyline() {
        return polyline;
    }

    /**
     * 
     * @param polyline
     *     The polyline
     */
    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
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
     *     The travel_mode
     */
    public String getTravel_mode() {
        return travel_mode;
    }

    /**
     * 
     * @param travel_mode
     *     The travel_mode
     */
    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

}
