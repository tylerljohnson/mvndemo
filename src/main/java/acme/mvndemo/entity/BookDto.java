package acme.mvndemo.entity;

import lombok.*;

@Data
public class BookDto {
    private String title;
    private String author;
    private String isbn;
}
