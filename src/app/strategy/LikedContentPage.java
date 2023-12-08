package app.strategy;

import java.util.List;

class LikedContentPage implements UserPage {
    private List<String> likedSongsInfo;
    private List<String> followedPlaylistsInfo;

    LikedContentPage(List<String> likedSongsInfo, List<String> followedPlaylistsInfo) {
        this.likedSongsInfo = likedSongsInfo;
        this.followedPlaylistsInfo = followedPlaylistsInfo;
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
}