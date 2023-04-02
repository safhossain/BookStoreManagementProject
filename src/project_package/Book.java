package project_package;

import javafx.beans.property.SimpleBooleanProperty;

public class Book
{
    private String bookName;
    private double bookPrice;
    private SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
    
    public Book(String newBookName, double newBookPrice){
        this.bookName = newBookName;
        this.bookPrice = newBookPrice;
    }
   
    public String getBookName() {return this.bookName;}
    
    public double getBookPrice() {return this.bookPrice;}
    
    public boolean isSelected() {
        return selected.get();
    }
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }    
    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    @Override
    public String toString() {
        return ("Book Name: " + this.bookName + ", Price: $" + this.bookPrice);
    }
}
