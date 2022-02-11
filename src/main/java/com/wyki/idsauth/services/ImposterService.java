package com.wyki.idsauth.services;

import com.wyki.idsauth.wrappers.ImposterWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ImposterService {

    RestTemplate restTemplate = new RestTemplate();
    @Value("${staff.checker.url}")
    String imposterurl;

    public Map<Boolean, ImposterWrapper> checkStaff(String id) {
        Map<String, Map<String, String>> staffwrapper = new HashMap<>();
        Map<String, String> staffdetails = new HashMap<>();

        staffdetails.put("id", id);
        staffdetails.put("phonenumber", "");
        staffdetails.put("id_type", "ID");
        staffdetails.put("channel", "mobile");
        staffwrapper.put("_postgetstafffull", staffdetails);

        Map<Boolean, ImposterWrapper> response = sendHTTPMessage(staffwrapper);
        return response;
    }

    private Map<Boolean, ImposterWrapper> sendHTTPMessage(Map<String, Map<String, String>> staffwrapper) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map> entity = new HttpEntity<>(staffwrapper, headers);

        Map<Boolean, ImposterWrapper> responsecontainer = new HashMap<>();
        try {
//            log.info("sending "+entity.toString());
            ResponseEntity<ImposterWrapper> responseEntity = restTemplate.postForEntity(imposterurl, entity, ImposterWrapper.class);
            //response may be an array
            ImposterWrapper response = responseEntity.getBody();
            log.debug("response " + response);
//            ImposterWrapper jsonresponse = gson.fromJson(response, ImposterWrapper.class);
            responsecontainer.put(true, response);

        } catch (HttpServerErrorException httpServerErrorException) {

//            log.error(httpServerErrorException.getMessage(), httpServerErrorException);
            responsecontainer.put(false,null);

            log.info(httpServerErrorException.getResponseBodyAsString());

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

        }

        return responsecontainer;
    }
}
