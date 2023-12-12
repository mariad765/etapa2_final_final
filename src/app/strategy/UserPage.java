package app.strategy;

public interface UserPage {
    String displayPage();
    void updatePage(String userName);

    String userName();
}