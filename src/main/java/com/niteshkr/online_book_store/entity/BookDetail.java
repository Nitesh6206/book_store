package com.niteshkr.online_book_store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="book_detail")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "detail")
    private String detail;

    @Column(name = "sold")
    private int sold;

    @Column(name="author")
    private String Author;

}

