package project_package;

public class Book
{
    private String bookName;
    private double bookPrice;
    
    public Book(String newBookName, double newBookPrice){
        this.bookName = newBookName;
        this.bookPrice = newBookPrice;
    }
   
    public String getName() {
        return this.bookName;
    }
    
    public double getPrice() {
        return this.bookPrice;
    }

    @Override
    public String toString() {
        return ("Book Name: " + this.bookName + ", Price: $" + this.bookPrice);
    }
}
