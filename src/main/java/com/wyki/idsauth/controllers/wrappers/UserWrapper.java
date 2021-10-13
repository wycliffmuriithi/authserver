package com.wyki.idsauth.controllers.wrappers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data @JsonIgnoreProperties(ignoreUnknown = true) @JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWrapper {
    String name;
    String email;
    String phonenumber;
    String password;
    Long roleid;
    List<String> rolename;
    String createdby;
    @JsonFormat(shape = JsonFormat.Shape.STRING,  pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Nairobi")
    Date creationdate;
    Boolean active;
}
