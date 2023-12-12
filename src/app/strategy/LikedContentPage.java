package app.strategy;

import lombok.Getter;

import java.util.List;

class LikedContentPage implements UserPage {
    private List<String> likedSongsInfo;
    private List<String> followedPlaylistsInfo;
    @Getter
    private String userN;

    LikedContentPage(List<String> likedSongsInfo, List<String> followedPlaylistsInfo,String userN) {
        this.likedSongsInfo = likedSongsInfo;
        this.followedPlaylistsInfo = followedPlaylistsInfo;
        this.userN=userN;
    }

    @Override
    public String displayPage() {
        StringBuilder page = new StringBuilder();
        page.append("LikedContentPage:\n");
        page.append("Liked Songs:\n\t").append(likedSongsInfo).append("\n");
        page.append("Followed Playlists:\n\t").append(followedPlaylistsInfo);
        return page.toString();
    }

    @Override
    public void updatePage(String userName) {

    }

    @Override
    public String userName() {
        return getUserN() ;
    }
}