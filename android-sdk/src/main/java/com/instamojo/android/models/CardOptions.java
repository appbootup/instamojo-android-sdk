package com.instamojo.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * CardOptions object that holds the Card transaction information received from Instamojo server
 * for a particular order.
 */
public class CardOptions implements Parcelable {

    @SerializedName("submission_data")
    private SubmissionData submissionData;

    @SerializedName("submission_url")
    private String submissionURL;

    public SubmissionData getSubmissionData() {
        return submissionData;
    }

    public void setSubmissionData(SubmissionData submissionData) {
        this.submissionData = submissionData;
    }

    public String getSubmissionURL() {
        return submissionURL;
    }

    public void setSubmissionURL(String submissionURL) {
        this.submissionURL = submissionURL;
    }

    protected CardOptions(Parcel in) {
        submissionURL = in.readString();
    }

    public static final Creator<CardOptions> CREATOR = new Creator<CardOptions>() {
        @Override
        public CardOptions createFromParcel(Parcel in) {
            return new CardOptions(in);
        }

        @Override
        public CardOptions[] newArray(int size) {
            return new CardOptions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(submissionURL);
    }

    @Override
    public String toString() {
        return "CardOptions{" +
                "submissionData=" + submissionData +
                ", submissionURL='" + submissionURL + '\'' +
                '}';
    }
}
