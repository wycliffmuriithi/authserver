package com.wyki.idsauth.controllers;

import com.wyki.idsauth.controllers.wrappers.*;
import com.wyki.idsauth.db.entities.GroupRoles;
import com.wyki.idsauth.db.entities.Groups;
import com.wyki.idsauth.db.entities.OrganizationGroups;
import com.wyki.idsauth.db.entities.Organizations;
import com.wyki.idsauth.services.dao.OrganizationDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/organization")
@Slf4j @AllArgsConstructor
public class OrganizationController {
    OrganizationDao organizationDao;

    @GetMapping("/orglist")
    public ResponseEntity<ResponseWrapper> getOrgList(Pageable pageable){
        //return list of organizations
        Page<Organizations> organizationsPage = organizationDao.listOrganizations(pageable);
        ResponseWrapper responseWrapper = new ResponseWrapper("success","org list", organizationsPage.toList());
        return ResponseEntity.ok().body(responseWrapper);
    }

    @PostMapping("/createorg")
    public  ResponseEntity<ResponseWrapper> createOrg(@RequestBody OrganizationDTO organizationDTO) throws URISyntaxException {
        User loggedinuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ResponseWrapper responseWrapper = organizationDao.createOrganization(organizationDTO.getName(),loggedinuser.getUsername());
        return responseWrapper.getStatus().equals("success") ?
                ResponseEntity.created(new URI("/")).body(responseWrapper):
                ResponseEntity.badRequest().body(responseWrapper);
    }

    @GetMapping("/grouplist")
    public ResponseEntity<ResponseWrapper> listGroups(Pageable pageable){
        Page<Groups> groupsPage = organizationDao.listGroups(pageable);
        ResponseWrapper responseWrapper = new ResponseWrapper("success","groups list", groupsPage.toList());
        return ResponseEntity.ok().body(responseWrapper);
    }


    @PostMapping("/creategroup")
    public  ResponseEntity<ResponseWrapper> createGroup(@RequestBody GroupDTO groupDTO) throws URISyntaxException {
        User loggedinuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ResponseWrapper responseWrapper = organizationDao.createGroup(groupDTO.getName(),loggedinuser.getUsername());
        return responseWrapper.getStatus().equals("success") ?
                ResponseEntity.created(new URI("/")).body(responseWrapper):
                ResponseEntity.badRequest().body(responseWrapper);
    }

    @PostMapping("/mapgrouprole")
    public ResponseEntity<ResponseWrapper> mapGroupRoles(@RequestBody GroupRolesDTO groupRolesDTO) throws URISyntaxException {
        User loggedinuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseWrapper responseWrapper = organizationDao.mapGroupRole(groupRolesDTO.getGroupid(),
                groupRolesDTO.getRoleid(), loggedinuser.getUsername());
        return responseWrapper.getStatus().equals("success") ?
                ResponseEntity.created(new URI("/")).body(responseWrapper):
                ResponseEntity.badRequest().body(responseWrapper);
    }


    @GetMapping("/listgrouprolemapping")
    public ResponseEntity<ResponseWrapper> listGroupRolesMapping(Pageable pageable){
        Page<GroupRoles> groupRolesPage = organizationDao.listGroupRoles(pageable);
        ResponseWrapper responseWrapper = new
                ResponseWrapper("success","roles mapped to group list", groupRolesPage.toList());
        return ResponseEntity.ok().body(responseWrapper);
    }

    @PostMapping("/mapgrouporg")
    public ResponseEntity<ResponseWrapper> mapOrgGroups(@RequestBody OrgGroupDTO orgGroupDTO) throws URISyntaxException {
        User loggedinuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseWrapper responseWrapper = organizationDao.mapOrganizationGroup(orgGroupDTO.getGroupid(),
                orgGroupDTO.getOrganizationid(), loggedinuser.getUsername());
        return responseWrapper.getStatus().equals("success") ?
                ResponseEntity.created(new URI("/")).body(responseWrapper):
                ResponseEntity.badRequest().body(responseWrapper);
    }

    @GetMapping("/listgrouporgmapping")
    public ResponseEntity<ResponseWrapper> listOrgGroupMapping(Pageable pageable, @RequestParam Optional<Long> organizationid){
        Page<OrganizationGroups> organizationGroupsPage = organizationDao.listOrganizationGroups(pageable,organizationid);
        ResponseWrapper responseWrapper = new
                ResponseWrapper("success","groups mapped to organization list", organizationGroupsPage.toList());
        return ResponseEntity.ok().body(responseWrapper);
    }

    @PostMapping("/mapusergrouprole")
    public ResponseEntity<ResponseWrapper> mapUserGroupRoless(@RequestBody UserGroupRolesDTO userGroupRolesDTO) throws URISyntaxException {
        User loggedinuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseWrapper responseWrapper = organizationDao.mapGrouproletoUser(userGroupRolesDTO.getGrouproleid(),
                userGroupRolesDTO.getUserid(), loggedinuser.getUsername());
        return responseWrapper.getStatus().equals("success") ?
                ResponseEntity.created(new URI("/")).body(responseWrapper):
                ResponseEntity.badRequest().body(responseWrapper);
    }

}
