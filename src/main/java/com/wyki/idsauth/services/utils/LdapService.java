package com.wyki.idsauth.services.utils;

import com.wyki.idsauth.wrappers.ContactWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LdapService {
    @Autowired
    private LdapTemplate ldapTemplate;


    public List<ContactWrapper> search(String username) {
        ldapTemplate.setIgnorePartialResultException(true);
        return ldapTemplate.search(
                "DC=KRATEST2,DC=GOV",
                "(&(objectCategory=person)(sAMAccountName=k" + username + ")(|(mobile=*)(ipPhone=*)(telephoneNumber=*)(mail=*)))",
                (AttributesMapper<ContactWrapper>) attrs -> {
                    ContactWrapper contactWrapper = new ContactWrapper();
                    if (attrs.get("mail") != null) {
//                        contactWrapper.setEmail((String)attrs.get("mail").get());
                        contactWrapper.setEmail(Optional.of((String)attrs.get("mail").get()));
                    }
                    if (attrs.get("mobile") != null) {
//                        contactWrapper.setMobile((String)attrs.get("mobile").get());
                        contactWrapper.setPhonenumber(Optional.of((String)attrs.get("mobile").get()));
                    }
                    if (attrs.get("telephoneNumber") != null) {
//                        contactWrapper.setPhonenumber((String)attrs.get("telephoneNumber").get());
                        contactWrapper.setPhonenumber(Optional.of((String)attrs.get("telephoneNumber").get()));
                    }
                    return contactWrapper;
                });
    }
}
