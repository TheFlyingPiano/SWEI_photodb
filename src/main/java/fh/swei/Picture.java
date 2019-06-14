package fh.swei;

import java.util.List;


public class Picture {

    private String Filename;
    private String Photographer;
    private List<String> Metdatata;


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

    public List<String> getMetdatata() {
        return Metdatata;
    }

    public void setMetdatata(List<String> metdatata) {
        Metdatata = metdatata;
    }


}
