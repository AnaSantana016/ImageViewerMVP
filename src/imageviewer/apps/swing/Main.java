package imageviewer.apps.swing;

import imageviewer.control.ImagePresenter;
import imageviewer.model.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import imageviewer.view.ImageDisplay;
import java.io.File;
import java.util.List;

public class Main extends JFrame{
    
    public static void main(String[] args){
        new Main().execute();
    }
    
    private ImageDisplay imageDisplay;
    private ImagePresenter imagePresenter;
    
    public Main(){
        this.setTitle("Image Viewer");        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(imagePanel());
        List<Image> images = new FileImageLoader(new File("fotos")).load();
        this.imagePresenter = new ImagePresenter(images, imageDisplay);
    }
    
    private void execute(){
        this.setVisible(true);
    }
    
    private JPanel imagePanel(){
        ImagePanel panel = new ImagePanel();
        this.imageDisplay = panel;
        return panel;
    }
}
