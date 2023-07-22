package com.zybooks.wgu;

import java.util.ArrayList;

public class TermsObject {

    private final static ArrayList<TermsObject> TermsObject = new ArrayList<>();
    private final int id;
    private final String title;
    private final String startDate;
    private final String endDate;

    public TermsObject() {
        id = 0;
        title = "You need a term, buster";
        startDate = "12/16/1985";
        endDate = "12/16/2023";
    }

    public TermsObject(int id, String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }

    public static void addTerms (TermsObject termsObject) {
        TermsObject.add(termsObject);
    }

    public static void deleteTerms () {
        TermsObject.clear();
    }

    public static ArrayList<TermsObject> getTerms() {
        return TermsObject;
    }
}
