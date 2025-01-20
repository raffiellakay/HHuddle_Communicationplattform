package com.knoettner.hhuddle.service;

public interface UserService {
    Long getHouseIdByUser(Long userId);
    Boolean hasUserChangedFirstPW(Long userId);
}
