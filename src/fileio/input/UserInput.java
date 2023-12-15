package fileio.input;

import lombok.Getter;

@Getter
public final class UserInput {
    private String username;
    private int age;
    private String city;

    /**
     * Constructor used for setting the fields of an UserInput object.
     */
    public UserInput() {
    }

    /**
     * Getter for username
     *
     * @param username the username of the user
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Getter for age
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     * Getter for city
     *
     * @param city the city of the user
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * Turn the object into a string
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "UserInput{"
                +
                "username='"
                + username
                + '\''
                +
                ", age="
                + age
                +
                ", city='"
                + city
                + '\''
                +
                '}';
    }
}
