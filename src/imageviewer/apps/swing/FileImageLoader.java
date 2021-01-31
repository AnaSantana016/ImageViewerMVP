package imageviewer.apps.swing;

import imageviewer.model.Image;
import imageviewer.view.ImageLoader;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileImageLoader implements ImageLoader{
    private final File folder;

    public FileImageLoader(File folder) {
        this.folder = folder;
    }
    
    @Override
    public List<Image> load(){
        List<Image> result = new ArrayList<>();
        File[] files = folder.listFiles(filesWith(".jpg",".png",".jpeg"));
        for (File file: files) result.add(new Image(file.getAbsolutePath()));
        return result;
    }
    
    private FilenameFilter filesWith(String... extensions){
        return new FilenameFilter(){
            @Override
            public boolean accept(File file, String name){
                for (String extensions : extensions)
                    if (name.endsWith(extensions)) return true;
                return false;
            }
        };
    }
}
