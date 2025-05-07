package com.restapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.Book;
import com.restapi.service.BookService;

@CrossOrigin(origins = "http://localhost:4000")
@RestController
@RequestMapping("/api")
public class BookController {
  
  @Autowired
  BookService bookService;

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String title) {
    try {
      List<Book> books = new ArrayList<>();

      if (title == null)
        bookService.findAll().forEach(books::add);
      else
        bookService.findByTitleContaining(title).forEach(books::add);

      if (books.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(books, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
    Book book = bookService.findById(id);

    if (book != null) {
      return new ResponseEntity<>(book, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/books")
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    try {
      Book _book = bookService.save(new Book(book.getTitle(), book.getDescription(), false));
      return new ResponseEntity<>(_book, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
    Book _book = bookService.findById(id);

    if (_book != null) {
      _book.setTitle(book.getTitle());
      _book.setDescription(book.getDescription());
      _book.setPublished(book.isPublished());
      return new ResponseEntity<>(bookService.save(_book), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
    try {
      bookService.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/books")
  public ResponseEntity<HttpStatus> deleteAllBooks() {
    try {
      bookService.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/books/published")
  public ResponseEntity<List<Book>> findByPublished() {
    try {
      List<Book> books = bookService.findByPublished(true);

      if (books.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(books, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
