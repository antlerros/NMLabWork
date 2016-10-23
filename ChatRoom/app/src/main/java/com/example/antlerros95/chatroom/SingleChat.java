package com.example.antlerros95.chatroom;

/**
 * Created by antlerros95 on 23/10/2016.
 */

public class SingleChat {
    private String mUserID;
    private String mContent;

    public SingleChat(String userID, String content) {
        mUserID = userID;
        mContent = content;
    }

    public String getID() {
        return mUserID;
    }

    public String getContent() {
        return mContent;
    }
}
