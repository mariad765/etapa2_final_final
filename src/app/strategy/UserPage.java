package app.strategy;

public interface UserPage {

    /**
     * Display page
     *
     * @return page content
     */
    String displayPage();

    /**
     * Update page
     *
     * @param userName user name
     */
    void updatePage(String userName);

    /**
     * Get username
     */
    String userName();
}
