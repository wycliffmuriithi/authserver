package com.wyki.idsauth.controllers.wrappers;

import lombok.Data;

@Data
public class UserStats {
    long registeredusers, activeusers, inactiveusers, newusers;
}
