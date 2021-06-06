package com.android1.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class FieldsAndValues implements Parcelable {
    private String operator;
    private double firstValue;
    private double secondValue;

    public FieldsAndValues() {
        setFirstValue(0);
        setSecondValue(0);
        setOperator("");
    }


    protected FieldsAndValues(Parcel in) {
        operator = in.readString();
        firstValue = in.readDouble();
        secondValue = in.readDouble();
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(operator);
        dest.writeDouble(firstValue);
        dest.writeDouble(secondValue);
    }
}


