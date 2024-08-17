package com.Floating_Cloud.Event_Point;
public class SiteData {
    private int id;
    private String name;
    private String tag;
    private String description;
    private double latitude;
    private double longitude;

    // 생성자
    public SiteData(String name, String tag, String description, double latitude, double longitude) {
        this.name = name;
        this.tag = tag;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter와 Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}