package com.ampos.restaurant.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuItemDTO implements Serializable {

    private Long Id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private String imageUrl;

    @NotNull
    private Double price;

    private String details;

    public MenuItemDTO(Long id, @NotNull String name, @NotNull String description, String imageUrl,
            @NotNull Double price, String details) {
        super();
        Id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.details = details;
    }

}
