package com.picmap.models;

/**
 * Created by Yur on 28/08/16.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import com.picmap.activities.DetailActivity;
/**
 * Created by Yur on 28/08/16.
 */

public class ImageModel implements Parcelable {

    String name, url;
    Context mContext;

    public Context getContext() {
        return mContext;
    }

    public ImageModel() {

    }

    public ImageModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    protected ImageModel(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }


}