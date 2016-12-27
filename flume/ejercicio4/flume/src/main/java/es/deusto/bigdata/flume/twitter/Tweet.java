package es.deusto.bigdata.flume.twitter;

import org.codehaus.jackson.annotate.JsonProperty;

public class Tweet {

	private String id;
	private Integer userFriendsCount;
	private String userLocation;
	private String userDescription;
	private Integer userStatusesCount;
	private Integer userFollowersCount;
	private String userName;
	private String userScreenName;
	private String createdAt;
	private String text;
	private Integer retweetCount;
	private Boolean retweeted;
	private Integer inReplyToUserId;
	private String source;
	private Long inReplyToStatusId;
	private String mediaUrlHttps;
	private String expandedUrl;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("user_friends_count")
	public Integer getUserFriendsCount() {
		return userFriendsCount;
	}

	public void setUserFriendsCount(Integer userFriendsCount) {
		this.userFriendsCount = userFriendsCount;
	}

	@JsonProperty("user_location")
	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	@JsonProperty("user_description")
	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	@JsonProperty("user_statuses_count")
	public Integer getUserStatusesCount() {
		return userStatusesCount;
	}

	public void setUserStatusesCount(Integer userStatusesCount) {
		this.userStatusesCount = userStatusesCount;
	}

	@JsonProperty("user_followers_count")
	public Integer getUserFollowersCount() {
		return userFollowersCount;
	}

	public void setUserFollowersCount(Integer userFollowersCount) {
		this.userFollowersCount = userFollowersCount;
	}

	@JsonProperty("user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("user_screen_name")
	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	@JsonProperty("created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@JsonProperty("retweet_count")
	public Integer getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

	@JsonProperty("retweeted")
	public Boolean getRetweeted() {
		return retweeted;
	}

	public void setRetweeted(Boolean retweeted) {
		this.retweeted = retweeted;
	}

	@JsonProperty("in_reply_to_user_id")
	public Integer getInReplyToUserId() {
		return inReplyToUserId;
	}

	public void setInReplyToUserId(Integer inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	@JsonProperty("source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@JsonProperty("in_reply_to_status_id")
	public Long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	@JsonProperty("media_url_https")
	public String getMediaUrlHttps() {
		return mediaUrlHttps;
	}

	public void setMediaUrlHttps(String mediaUrlHttps) {
		this.mediaUrlHttps = mediaUrlHttps;
	}

	@JsonProperty("expanded_url")
	public String getExpandedUrl() {
		return expandedUrl;
	}

	public void setExpandedUrl(String expandedUrl) {
		this.expandedUrl = expandedUrl;
	}

	public static String getCsvHeader() {
		return "id;userFriendsCount;userLocation;userDescription;userStatusesCount;userFollowersCount;userName;userScreenName"
				+ ";createdAt;text;retweetCount;retweeted;inReplyToUserId;source;inReplyToStatusId;mediaUrlHttps;expandedUrl";
	}

	public String toCsvLine() {
		return id + ";" + userFriendsCount + ";" + userLocation + ";" + userDescription + ";" + userStatusesCount + ";"
				+ userFollowersCount + ";" + userName + ";" + userScreenName + ";" + createdAt + ";" + clean(text) + ";"
				+ retweetCount + ";" + retweeted + ";" + inReplyToUserId + ";" + source + ";" + inReplyToStatusId + ";"
				+ mediaUrlHttps + ";" + expandedUrl;
	}

	private String clean(String text) {
		String clean = text;
		clean = clean.replaceAll("\n", " ");
		return clean;
	}

}
