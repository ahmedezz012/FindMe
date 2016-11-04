
package com.ezz.findme.Models.Directions;

import java.util.ArrayList;
import java.util.List;


public class Route {

    private Bounds bounds;
    private String copyrights;
    private List<Leg> legs = new ArrayList<Leg>();
    private Overview_polyline overview_polyline;
    private String summary;
    private List<Object> warnings = new ArrayList<Object>();
    private List<Object> waypoint_order = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The bounds
     */
    public Bounds getBounds() {
        return bounds;
    }

    /**
     * 
     * @param bounds
     *     The bounds
     */
    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    /**
     * 
     * @return
     *     The copyrights
     */
    public String getCopyrights() {
        return copyrights;
    }

    /**
     * 
     * @param copyrights
     *     The copyrights
     */
    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    /**
     * 
     * @return
     *     The legs
     */
    public List<Leg> getLegs() {
        return legs;
    }

    /**
     * 
     * @param legs
     *     The legs
     */
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    /**
     * 
     * @return
     *     The overview_polyline
     */
    public Overview_polyline getOverview_polyline() {
        return overview_polyline;
    }

    /**
     * 
     * @param overview_polyline
     *     The overview_polyline
     */
    public void setOverview_polyline(Overview_polyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    /**
     * 
     * @return
     *     The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The warnings
     */
    public List<Object> getWarnings() {
        return warnings;
    }

    /**
     * 
     * @param warnings
     *     The warnings
     */
    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    /**
     * 
     * @return
     *     The waypoint_order
     */
    public List<Object> getWaypoint_order() {
        return waypoint_order;
    }

    /**
     * 
     * @param waypoint_order
     *     The waypoint_order
     */
    public void setWaypoint_order(List<Object> waypoint_order) {
        this.waypoint_order = waypoint_order;
    }

}
