package fh.swei;

import fh.swei.Picture;

public class PicViewModel {
    private Picture picture;

    public PicViewModel(Picture picture){
        this.picture=picture;
    }

    public String getFilename(){
        return picture.getFilename();
    }

    public void setFilename(String File){
        picture.setFilename(File);
    }

    public String getPhotographer(){
        return picture.getPhotographer();
    }
    public void setPhotographer(String Name){
        picture.setPhotographer(Name);
    }


}
