package project_package;

public class Book
{
    private String bookName;
    private double bookPrice;
    
    public Book(String newBookName, double newBookPrice){
        this.bookName = newBookName;
        this.bookPrice = newBookPrice;
    }
   
    public String getBookName() {
        return this.bookName;
    }
    
    public double getBookPrice() {
        return this.bookPrice;
    }

    @Override
    public String toString() {
        return ("Book Name: " + this.bookName + ", Price: $" + this.bookPrice);
    }
}
