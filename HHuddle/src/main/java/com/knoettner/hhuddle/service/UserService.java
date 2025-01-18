package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.MyUserDto;

public interface UserService {
    Long getHouseIdByUser(Long userId);
    Boolean hasUserChangedTempPW(Long userId);
    MyUserDto updatePassword(String mail, String newPw);
}
