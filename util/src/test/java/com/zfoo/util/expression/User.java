package com.zfoo.util.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-21 12:49
 */
public class User {

    private String userName;
    private int credits;
    private List<String> interestsList;
    private String[] interestsArray;
    private Map<String, String> interestsMap;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }


    public boolean isVipMember(String userName) {
        return "tom".equals(userName) || "jony".equals(userName);
    }

    public boolean validatePassword(String password) {
        return "123456".equals(password);
    }

    private boolean validatePassword2(String password) {
        return "123456".equals(password);
    }

    public static boolean validatePassword3(String password) {
        return "123456".equals(password);
    }

    public void addInterests(String... interests) {
        if (interestsList == null) {
            interestsList = new ArrayList();
        }
        for (String interest : interests) {
            interestsList.add(interest);
        }
    }


    public List<String> getInterestsList() {
        return interestsList;
    }

    public void setInterestsList(List<String> interestsList) {
        this.interestsList = interestsList;
    }

    public Map<String, String> getInterestsMap() {
        return interestsMap;
    }

    public void setInterestsMap(Map<String, String> interestsMap) {
        this.interestsMap = interestsMap;
    }

    public String[] getInterestsArray() {
        return interestsArray;
    }

    public void setInterestsArray(String[] interestsArray) {
        this.interestsArray = interestsArray;
    }


}
