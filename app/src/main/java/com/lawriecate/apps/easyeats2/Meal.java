package com.lawriecate.apps.easyeats2;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;

import java.util.ArrayList;

/**
 * Created by lawrie on 15/11/2016.
 */

public class Meal implements Parcelable {
    private Integer id;
    private String title;
    private String description;
    private String image;
    private String video;
    private ArrayList<String>  instructions;
    private ArrayList<String>  ingredients;

    public Meal(Integer id, String title, String description, String image, String video, ArrayList<String> instructions, ArrayList<String>  ingredients) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.video = video;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public ArrayList<String>  getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String>  instructions) {
        this.instructions = instructions;
    }

    public ArrayList<String>  getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.id);
        out.writeString(this.title);
        out.writeString(this.description);
        out.writeString(this.image);
        out.writeString(this.video);
        out.writeSerializable(this.instructions);
        out.writeSerializable(this.ingredients);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Meal> CREATOR = new Parcelable.Creator<Meal>() {
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Meal(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.video = in.readString();
        this.instructions = (ArrayList<String>) in.readSerializable();
        this.ingredients = (ArrayList<String>) in.readSerializable();

    }
}
