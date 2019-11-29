package com.sample.home24.room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Article implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String articleImageUrl;
    @ColumnInfo
    public boolean isLiked;
    @ColumnInfo
    public boolean isDisliked;
    @ColumnInfo
    public String sku;
    @ColumnInfo
    public String tittle;

    public Article() {
    }


    protected Article(Parcel in) {
        id = in.readInt();
        articleImageUrl = in.readString();
        isLiked = in.readByte() != 0;
        isDisliked = in.readByte() != 0;
        sku = in.readString();
        tittle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(articleImageUrl);
        dest.writeByte((byte) (isLiked ? 1 : 0));
        dest.writeByte((byte) (isDisliked ? 1 : 0));
        dest.writeString(sku);
        dest.writeString(tittle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
