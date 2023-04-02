package project_package;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class BookList
{
    private ArrayList<Book> bookComponents;
    
    public BookList(){
        bookComponents = new ArrayList<Book>();
    }
    
    public ArrayList<Book> getBookComponents(){
        return bookComponents;
    }
    
    public void addBook(Book newBookComponent) {
        bookComponents.add(newBookComponent);
    }
    public void addBook(String newBookName, int newBookPrice){
        Book newBook = new Book(newBookName, newBookPrice);
        bookComponents.add(newBook);
    }
    
    public void removeBook(int i) {
        bookComponents.remove(i);        
    }
    public void removeBook(Book oldBookComponent){
        bookComponents.remove(oldBookComponent);        
    }
    public void removeBook(String bookName){
        for (int i = 0; i < bookComponents.size(); i++) {
            if (bookComponents.get(i).getBookName().equals(bookName)) {
                bookComponents.remove(i);
                return;
            }
        }
    }
    
    public String getBookInfo(int j){
        return bookComponents.get(j).toString();
    }
    public String getBookName(int j){
        return bookComponents.get(j).getBookName();
    }
    public double getBookPrice(int j){
        return bookComponents.get(j).getBookPrice();
    }    
    public double getBookPrice(String bookName){
        for (int i = 0; i < bookComponents.size(); i++) {
            if (bookComponents.get(i).getBookName().equals(bookName)) {                
                return bookComponents.get(i).getBookPrice();
            }
        }
        return 0;
    }
    
    public int getCollectionSize(){
        return bookComponents.size();
    }
    @Override
    public String toString(){
        String str = "";
        for (int i = 0; i < bookComponents.size(); i++) {
            str += bookComponents.get(i).toString()+"\n";
        }
        return str;
    }
    
    public void LoadBooksFromFile(String fileName) throws FileNotFoundException, IOException{
        File bookFile = new File(fileName);
        FileReader fr = new FileReader(bookFile);        
        
        String linePattern = "^(.+)\t([0-9]+(?:\\.[0-9]+)?)$";
        
        try(BufferedReader br = new BufferedReader(fr)){
            String line;
            while ((line = br.readLine()) != null) {
                if(line.matches(linePattern)){
                    String[] parts = line.split("\t", 2); // Split using the tab delimiter
                    String bookName = parts[0];
                    double bookPrice = Double.parseDouble(parts[1]);
                    Book newBook = new Book(bookName, bookPrice);
                    addBook(newBook);
                }
                else{
                    System.err.println("Warning: Invalid line format in books.txt: " + line);
                }
            } 
        } 
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void UpdateBookFile(String fileName) throws FileNotFoundException, IOException{
        File bookFile = new File(fileName);
        FileWriter fw = new FileWriter(bookFile);
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            for (Book book : bookComponents) {                
                String line = book.getBookName() + "\t" + book.getBookPrice();
                bw.write(line);
                bw.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    //Method method to test if all the methods work.
    public static void main(String[] args) throws IOException {
        BookList store = new BookList();
        store.LoadBooksFromFile("books.txt");
        System.out.println(store.toString());        
        store.removeBook("Anna Karenina"); //already removed
        store.UpdateBookFile("books.txt");
        System.out.println(store.toString());
    }
}