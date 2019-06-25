package fh.swei;

import java.util.List;


public class Picture {
    private int ID;
    private String Filename;
    private String Photographer;
    private String Date;
    private List<String> ExifMetadata;
    private List<String> IptcMetadata;

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getPhotographer() {
        return Photographer;
    }

    public void setPhotographer(String photographer) {
        Photographer = photographer;
    }

    public List<String> getExifMetadata() {
        return ExifMetadata;
    }

    public void setExifMetadata(List<String> exifMetadata) {
        ExifMetadata = exifMetadata;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public List<String> getIptcMetadata() {
        return IptcMetadata;
    }

    public void setIptcMetadata(List<String> iptcMetadata) {
        IptcMetadata = iptcMetadata;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
