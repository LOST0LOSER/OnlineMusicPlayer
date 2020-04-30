package entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class Music {
	private int id;
	@Nullable
    private String title;
	private String singer;
	private Date time;
	@Nullable
    private String url;
	private int userid;
	private boolean favTag;
	public int getId() {
		return id;
}
	public void setId(int id) {
		this.id = id;
	}
	@Nullable
    public String getTitle() {
		return title;
	}
	public void setTitle(@Nullable String title) {
		this.title = title!=null?title:"";
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Nullable
    public String getUrl() {
		return url;
	}
	public void setUrl(@Nullable String url) {
		if(url != null){
			this.url = url;
		}
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public boolean getFavTag() {
		return favTag;
	}
	public void setFavTag(boolean favTag) {
		this.favTag = favTag;
	}
	@NotNull
    @Override
	public String toString() {
		return "Music [id=" + id + ", title=" + title + ", singer=" + singer + ", time=" + time + ", url=" + url
				+ ", favTag=" + favTag + "]";
	}
	
	public String toJSON() {
		return "{\"name\":\"" + title + "\",\"artist\":\"" + singer + "\",\"url\":\"musicFile/" + url + "\"}";
	}
}

