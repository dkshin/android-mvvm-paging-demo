package kr.dkshin.android.pagingwithnetworkandroom.data.model.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Ahmed Abd-Elmeged on 2/13/2018.
 * <p>
 * Boring class use kotlin data class better :D
 */
@Entity(tableName = "users")
public class User {

    @Expose
    @SerializedName("id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("login")
    @ColumnInfo(name = "login")
    public String login;

    @Expose
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;

    public String getLogin() {
        return login;
    }

    public long getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(avatarUrl, user.avatarUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, login, avatarUrl);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}

