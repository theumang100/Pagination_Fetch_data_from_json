package com.wmt.jsonfetch;

public class ExampleItem {
    private String mImageUrl;
    private String mName;
    private String mCountry;
    private int mAge;

    public ExampleItem(String imageUrl, String name, String country, int age) {
        mImageUrl = imageUrl;
        mName = name;
        mCountry = country;
        mAge = age;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public String getCountry() {
        return mCountry;
    }

    public int getAge() {
        return mAge;
    }
}
