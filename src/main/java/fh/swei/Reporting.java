package fh.swei;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.drew.metadata.Tag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reporting {

    static void singleReport(Picture pic) throws IOException {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);


        contentStream.beginText();
        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(25, 700);
        contentStream.showText("Picture Report for         ");

        contentStream.showText(pic.getFilename());
        contentStream.newLine();

        contentStream.showText("Photographer: "+pic.getPhotographer());


        PDImageXObject pdImage = PDImageXObject.createFromFile("pictures\\"+pic.getFilename(),document);
        PDPageContentStream contents = new PDPageContentStream(document, page,true,true);
        float scale = 0.05f;
        contents.drawImage(pdImage, 20, 450, pdImage.getWidth()*scale, pdImage.getHeight()*scale);

        contents.close();
        contentStream.newLineAtOffset(25, -300);
        contentStream.showText("IPTC Metadata: ");
        contentStream.newLine();
        List[] iptc=split(pic.getIptcMetadata());
        List[] iptc1=split(iptc[0]);
        contentStream.showText(String.valueOf(iptc1[0]));
        contentStream.newLine();
        contentStream.showText(String.valueOf(iptc1[1]));
        contentStream.newLine();
        contentStream.showText(String.valueOf(iptc[1]));
        contentStream.newLine();
        contentStream.newLine();


        contentStream.showText("EXIF Metadata: ");
        contentStream.newLine();
        List<String> exif = new ArrayList<>(pic.getExifMetadata());
        boolean x=true;
        for(int i=0;i<exif.size();i++){
            if(i>17){
                if(x){
                    contentStream.endText();
                    contentStream.close();
                    PDPage page2 = new PDPage();
                    document.addPage(page2);
                    contentStream= new PDPageContentStream(document, page2);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER, 12);
                    contentStream.setLeading(14.5f);
                    contentStream.newLineAtOffset(25, 700);
                    x=false;

                }


                contentStream.showText(exif.get(i));
                contentStream.newLine();

            }

            contentStream.showText(exif.get(i));
            contentStream.newLine();
        }

        contentStream.endText();
        contentStream.close();

        String Fname= pic.getFilename();
        Fname=Fname.split("\\.")[0];
        document.save("reports\\Report_"+Fname+".pdf");
        document.close();

    }


    // Generic function to split a list into two sublists in Java
    public static<T> List[] split(List<T> list)
    {
        // get size of the list
        int size = list.size();

        // construct new list from the returned view by list.subList() method
        List<T> first = new ArrayList<>(list.subList(0, (size + 1)/2));
        List<T> second = new ArrayList<>(list.subList((size + 1)/2, size));

        // return an List array to accommodate both lists
        return new List[] {first, second};
    }
}
