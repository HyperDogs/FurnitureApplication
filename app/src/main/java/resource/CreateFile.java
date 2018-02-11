package resource;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by OptimizeSystem on 14/7/2558.
 */
public class CreateFile {
    private static String path="";
    private static String name="";
    public static String headerFilePath="";
    public static String setPath="";

    public static File createUnique(){
        ///Create File Name
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmssSS");
        String filename = df.format(c.getTime());
        String fileNameExtension = ".jpg";

        ///Path sdCard
        File sdCard = Environment.getExternalStorageDirectory();

        //Folder Path
        String imageStorageFolder = File.separator + "DCIM" + File.separator + "Camera" + File.separator;
        setPath = imageStorageFolder;

        //File Path
        path = sdCard + imageStorageFolder + filename + fileNameExtension;
        name = filename+fileNameExtension;

        File file = new File(path);
        return file;
    }

    public static String getFilePath(){
        return  path;
    }
    public static String getFileName(){ return name; }
    public static String getPath(){
        String getPath = setPath;
        return getPath;}
}
