package com.test_spring.demo;


import com.test_spring.demo.entities.Author;
import com.test_spring.demo.entities.Book;
import com.test_spring.demo.repositories.AuthorRepository;
import com.test_spring.demo.repositories.BookRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public String listAllUrls(Model model) {
        return "home";
    }

    @GetMapping("/authors")
    public String listAllAuthors(Model model) {
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "author-list";
    }

    @GetMapping("/books")
    public String listAllBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/authors/{id}")
    public String booksByAuthor(@PathVariable("id") int id, Model model) {
        Author author = authorRepository.findById(id).get();
        List<Book> books = bookRepository.findAllByAuthor(author);
        model.addAttribute("books", books);
        model.addAttribute("author_id", id);
        return "books-by-author";
    }

    @PostMapping("/authors/{id}")
    public String addBookByAuthor(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        Author author = authorRepository.findById(id).get();
        Book b = new Book(book.getTitle(), book.getPublished_year(), author);
        bookRepository.save(b);
        return "redirect:/authors/" + id;
    }

    @PostMapping("/delete-author/{id}")
    public String deleteAuthor(@PathVariable("id") int id) {
        System.out.println("author wull be deleted.." + id);
        Author author = authorRepository.findById(id).get();
        authorRepository.delete(author);
        return "redirect:/authors";
    }

    @PostMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        System.out.println("book wull be deleted.." + id);
        Book book = bookRepository.findById(id).get();
        bookRepository.delete(book);
        return "redirect:/authors/"+book.getAuthor().getId();
    }
}
