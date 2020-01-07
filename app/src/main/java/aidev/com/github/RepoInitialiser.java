package aidev.com.github;

public class RepoInitialiser {


    private String link;
    private String htmlUrl;
    private String description;
    private String pushedOn, upadatedOn, createdOn;


    public RepoInitialiser(String link, String htmlUrl, String description, String pushedOn, String upadatedOn, String createdOn) {
        this.link = link;
        this.htmlUrl = htmlUrl;
        this.description = description;
        this.pushedOn = pushedOn;
        this.upadatedOn = upadatedOn;
        this.createdOn = createdOn;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPushedOn() {
        return pushedOn;
    }

    public void setPushedOn(String pushedOn) {
        this.pushedOn = pushedOn;
    }

    public String getUpadatedOn() {
        return upadatedOn;
    }

    public void setUpadatedOn(String upadatedOn) {
        this.upadatedOn = upadatedOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}