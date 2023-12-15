package app.user;

import lombok.Getter;

@Getter
public class Merch {
    private String name;
    private String description;
    private int price;

    /**
     * Constructor for Merch
     *
     * @param name        name of the merch
     * @param description description of the merch
     * @param price       price of the merch
     */
    public Merch(final String name, final String description,
                 final int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
