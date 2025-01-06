
package com.egroc.model;
import lombok.Data;
import jakarta.persistence.*;


@Entity
@Data
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="category_id")
    private int id;

    private String name;

}
