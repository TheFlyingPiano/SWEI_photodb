package fh.swei;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class Photographer {

    public List<String> getPhotographer() {
        return Photographer;
    }

    public void setPhotographer(List<String> photographer) {
        Photographer = photographer;
    }

    private List<String> Photographer=new ArrayList<>();


     static void addPhotographer(String name) throws SQLException {
        PhotographerDataAccess.addPhotographer(name);
    }
}
