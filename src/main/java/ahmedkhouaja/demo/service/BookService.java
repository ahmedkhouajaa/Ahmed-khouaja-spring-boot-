package com.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restapi.model.Book;


@Service
public class BookService {

  static List<Book> tutorials = new ArrayList<Book>();
  static long id = 0;

  public List<Book> findAll() {
    return tutorials;
  }

  public List<Book> findByTitleContaining(String title) {
    return tutorials.stream().filter(tutorial -> tutorial.getTitle().contains(title)).toList();
  }

  public Book findById(long id) {
    return tutorials.stream().filter(tutorial -> id == tutorial.getId()).findAny().orElse(null);
  }

  public Book save(Book tutorial) {
    if (tutorial.getId() != 0) {
      long _id = tutorial.getId();

      for (int idx = 0; idx < tutorials.size(); idx++)
        if (_id == tutorials.get(idx).getId()) {
          tutorials.set(idx, tutorial);
          break;
        }

      return tutorial;
    }

    tutorial.setId(++id);
    tutorials.add(tutorial);
    return tutorial;
  }

  public void deleteById(long id) {
    tutorials.removeIf(tutorial -> id == tutorial.getId());
  }

  public void deleteAll() {
    tutorials.removeAll(tutorials);
  }

  public List<Book> findByPublished(boolean isPublished) {
    return tutorials.stream().filter(tutorial -> isPublished == tutorial.isPublished()).toList();
  }
}
