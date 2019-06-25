package fh.swei;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.iptc.IptcDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MetadataExtractor {



    static List<String> getlocal(){

        List<String> LocalPics=new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get("pictures"))) {

            LocalPics = walk.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return LocalPics;
    }


    //TRUE FOR EXIF, FALSE FOR IPTC
    static List Metadata(File imagePath, boolean s) throws ImageProcessingException, IOException {
        List<Tag> tagList = new ArrayList<>();
        List<String> tags=new ArrayList<>();
        Metadata metadata = ImageMetadataReader.readMetadata(imagePath);

        if (s) {
            if (metadata.containsDirectoryOfType(ExifSubIFDDirectory.class)) {
                ExifSubIFDDirectory directory2 = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                //     System.out.println(tag);

                tagList.addAll(directory2.getTags());
                for(Tag tag:tagList){
                    tags.add(tag.toString());

                }
            }
            return tags;

        } else if (metadata.containsDirectoryOfType(IptcDirectory.class)) {
            IptcDirectory directory1 = metadata.getFirstDirectoryOfType(IptcDirectory.class);
            //     System.out.println(tag);
            tagList.addAll(directory1.getTags());

            return tagList;
        }
        return tagList;
    }




}
