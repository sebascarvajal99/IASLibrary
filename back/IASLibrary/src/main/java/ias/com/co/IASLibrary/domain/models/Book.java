package ias.com.co.IASLibrary.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("books")
public class Book {
    private String name;
    @Id
    private Integer id;
    private boolean borrowed;
    private String ISBN;
}
