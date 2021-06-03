package com.android1.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Fields implements Parcelable {
    private String workField;
    private String result;
    private double firstValue;
    private double secondValue;

    public Fields() {
        setFirstValue(0);
        setSecondValue(0);
    }

    public double getFirstValue() {
        return firstValue;
    }

    public double getSecondValue() {
        return secondValue;
    }

    public void setFirstValue(double firstValue) {
        this.firstValue = firstValue;
    }

    public void setSecondValue(double secondValue) {
        this.secondValue = secondValue;
    }

    protected Fields(Parcel in) {
        workField = in.readString();
        result = in.readString();
        firstValue = in.readDouble();
        secondValue = in.readDouble();
    }

    public static final Creator<Fields> CREATOR = new Creator<Fields>() {
        @Override
        public Fields createFromParcel(Parcel in) {
            return new Fields(in);
        }

        @Override
        public Fields[] newArray(int size) {
            return new Fields[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workField);
        dest.writeString(result);
        dest.writeDouble(firstValue);
        dest.writeDouble(secondValue);
    }
}
