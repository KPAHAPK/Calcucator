package com.android1.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class FieldsAndValues implements Parcelable {
    private String workField;
    private String result;
    private double firstValue;
    private double secondValue;

    public FieldsAndValues() {
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

    protected FieldsAndValues(Parcel in) {
        workField = in.readString();
        result = in.readString();

    }

    public static final Creator<FieldsAndValues> CREATOR = new Creator<FieldsAndValues>() {
        @Override
        public FieldsAndValues createFromParcel(Parcel in) {
            return new FieldsAndValues(in);
        }

        @Override
        public FieldsAndValues[] newArray(int size) {
            return new FieldsAndValues[size];
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

    }
}
