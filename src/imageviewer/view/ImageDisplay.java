package imageviewer.view;

public interface ImageDisplay {
    void display(String name);
    void on (Shift shift);
    public String current();
    
    interface Shift {
        String left();
        String rigth();
        
        public static class Null implements Shift{
            @Override
            public String left(){
                return "";
            }
            
            @Override
            public String rigth(){
                return "";
            }
        }
    }
}
