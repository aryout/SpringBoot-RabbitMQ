package com.faceyee.service;

import com.faceyee.domain.entity.Author;

import java.util.List;

/**
 * Created by 97390 on 8/26/2018.
 */
public interface AuthorService_jdbc {
    int add(Author author);

    int update(Author author);

    int delete(Long id);

    Author findAuthor(Long id);

    List<Author> findAuthorList();
}