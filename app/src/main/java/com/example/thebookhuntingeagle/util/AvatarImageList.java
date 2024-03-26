package com.example.thebookhuntingeagle.util;

import com.example.thebookhuntingeagle.R;

import java.util.Arrays;
import java.util.List;

public class AvatarImageList {

    private int currentAvatar = 0;
    private final Integer[] avatarIds = {
            R.drawable.avatar0,
            R.drawable.avatar1,
            R.drawable.avatar2
    };
    List<Integer> avatarList = Arrays.asList(avatarIds);

    public Integer previousAvatar() {
        currentAvatar = (--currentAvatar+avatarList.size())%avatarList.size();
        return getCurrentAvatar();
    }

    public Integer nextAvatar() {
        currentAvatar = (++currentAvatar)%avatarList.size();
        return getCurrentAvatar();
    }

    public Integer getCurrentAvatar() {
        if (avatarList.size() == 0)
            return null;

        return Integer.valueOf(avatarList.get(currentAvatar));
    }

    public Integer setCurrentAvatar(int avatar) {
        currentAvatar = avatarList.indexOf(avatar);
        return getCurrentAvatar();
    }
}
