package imageviewer.apps.swing;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import imageviewer.model.Image;
import imageviewer.view.ImageDisplay;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class ImagePanel extends JPanel implements ImageDisplay{
    
    private String current;
    private String future;
    private BufferedImage image;
    private BufferedImage futureImage;
    private int offset = 0;
    private Shift shift = new Shift.Null();

    public ImagePanel() {
        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        if (futureImage == null) return;
        //g.drawImage(image, offset, 0, null);
        Bounds b = new Bounds();
        g.drawImage(image, b.x(), b.y(), b.width(), b.height(), null);
        
        if (offset == 0) return;
        g.drawImage(futureImage, offset > 0 ? -(futureImage.getWidth()+offset) : image.getWidth(), 0, null);
    }
    
    
    private void toggle() {
        current = future;
        image = futureImage;
        offset = 0;
        repaint();
    }
    
    private void reset(){
        offset = 0;
        repaint();
    }
    
    private void setOffset(int offset){
        this.offset = offset;
        if (offset < 0) setFuture(shift.rigth());
        if (offset > 0) setFuture(shift.left());
        repaint();
    }
    
    @Override
    public void display(String name){
        this.current = name; 
        this.image = load(name);
        repaint();
    }
    
    private void setFuture(String name){
        if (name.equals(future)) return;
        this.future = name;
        this.futureImage = load(name);
    }
   
    private static BufferedImage load(String name) {
        try{
            return ImageIO.read(new File(name));
        }
        catch(IOException ex){
            return null;
        }
    }
    
    @Override
    public void on(Shift shift){
        this.shift = shift;
    }
    
    @Override
    public String current(){
        return this.current;
    }
    
    private class MouseHandler implements MouseListener, MouseMotionListener {

        private int initial;

        @Override
        public void mouseClicked(MouseEvent event) {
        }

        @Override
        public void mousePressed(MouseEvent event) {
            initial = event.getX();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            if (Math.abs(offset) > getWidth() / 2) 
                toggle();
            else
                reset();
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            setOffset(event.getX() - initial);
        }

        @Override
        public void mouseMoved(MouseEvent event) {
        }
    }
    
    private class Bounds{
        
        private final int iw;
        private final int ih;
        private final double ir;
        private final int pw;
        private final int ph;
        private final double pr;

        public Bounds() {
            iw = image.getWidth();
            ih = image.getHeight();
            ir = (double) iw / ih;
            
            pw = getWidth();
            ph = getHeight();
            pr = (double) pw / ph;
        }
        
        int x() {
            return (pw - width()) / 2;
        }
       
        int y() {
            return (ph - height()) / 2;
        }
        
        int width() {
            return ir < pr ? (int) ((double) iw * ph / ih) : pw;
        }
        
        int height() {
            return ir < pr ? ph : (int) ((double) ih * pw / iw);
        }
    }
}
