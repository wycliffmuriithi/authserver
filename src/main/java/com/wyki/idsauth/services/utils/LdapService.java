package com.wyki.idsauth.services.utils;

import com.wyki.idsauth.wrappers.ContactWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LdapService {
    @Autowired
    private LdapTemplate ldapTemplate;


    public List<ContactWrapper> search(String username) {
        return ldapTemplate.search(
                "DC=KRATEST2,DC=GOV",
                "(&(objectCategory=person)(sAMAccountName=" + username + ")(|(mobile=*)(ipPhone=*)(telephoneNumber=*)(mail=*)))",
                (AttributesMapper<ContactWrapper>) attrs -> {
                    ContactWrapper contactWrapper = new ContactWrapper();
                    if (attrs.get("mail") != null) {
                        contactWrapper.setEmail((String)attrs.get("mail").get());
                    }
                    if (attrs.get("mobile") != null) {
                        contactWrapper.setMobile((String)attrs.get("mobile").get());
                    }
                    if (attrs.get("telephoneNumber") != null) {
                        contactWrapper.setPhonenumber((String)attrs.get("telephoneNumber").get());
                    }
                    return contactWrapper;
                });
    }
}
