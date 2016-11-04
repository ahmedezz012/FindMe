
package com.ezz.findme.Models.Directions;

import java.util.ArrayList;
import java.util.List;

public class TheDirections {

    private List<Geocoded_waypoint> geocoded_waypoints = new ArrayList<Geocoded_waypoint>();
    private List<Route> routes = new ArrayList<Route>();
    private String status;

    /**
     * 
     * @return
     *     The geocoded_waypoints
     */
    public List<Geocoded_waypoint> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    /**
     * 
     * @param geocoded_waypoints
     *     The geocoded_waypoints
     */
    public void setGeocoded_waypoints(List<Geocoded_waypoint> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    /**
     * 
     * @return
     *     The routes
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * 
     * @param routes
     *     The routes
     */
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
