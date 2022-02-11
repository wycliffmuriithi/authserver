package com.wyki.idsauth.wrappers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @JsonIgnoreProperties(ignoreUnknown = true) @NoArgsConstructor @AllArgsConstructor
public class ImposterWrapper {
    @JsonAlias("StaffDetails")
    StaffDetails staffdetails;

    @Data @JsonIgnoreProperties(ignoreUnknown = true) @NoArgsConstructor @AllArgsConstructor
    public class StaffDetails {
        @JsonAlias("RESULT")
        Result result;
        @JsonAlias("STAFF")
        Staff staff;

        @Data @JsonIgnoreProperties(ignoreUnknown = true) @NoArgsConstructor@AllArgsConstructor
        public class Result {
            @JsonAlias("CODE")
            int code;
            String message;
        }

        @Data @JsonIgnoreProperties(ignoreUnknown = true) @NoArgsConstructor@AllArgsConstructor
        public class Staff {
            @JsonAlias("IDNUMBER")
            String idnumber;
            @JsonAlias("EMPNO")
            String empno;
            @JsonAlias("SURNAME")
            String surname;
            @JsonAlias("OTHERNAMES")
            String othernames;
            @JsonAlias("REGIONNAME")
            String regionname;
            @JsonAlias("DEPTNAME")
            String deptname;
            @JsonAlias("STATUS")
            String status;
        }
    }



}
