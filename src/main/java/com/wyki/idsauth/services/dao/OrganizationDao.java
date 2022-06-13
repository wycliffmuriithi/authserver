package com.wyki.idsauth.services.dao;

import com.wyki.idsauth.controllers.wrappers.ResponseWrapper;
import com.wyki.idsauth.db.*;
import com.wyki.idsauth.db.entities.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrganizationDao extends UsersCommonDao {
    OrganizationRepo organizationRepo;
    GroupsRepo groupsRepo;
    OrganizationgroupsRepo organizationgroupsRepo;
    RolesRepo  rolesRepo;
    GrouprolesRepo grouprolesRepo;
//    UserrolesRepo userrolesRepo;

    public ResponseWrapper createOrganization(String name, String createdby) {

        //check that organization does not exist
        if (organizationRepo.countByName(name) > 0) {
            return new ResponseWrapper("failed", "organization already exists");
        }
        Organizations organizations = new Organizations();
        organizations.setName(name);
        organizations.setCreatedby(getUseridfromOptional(createdby));
        organizations = organizationRepo.save(organizations);
        return new ResponseWrapper("success", "organization created", organizations);
    }

    public Page<Organizations> listOrganizations(Pageable pageable) {
        return organizationRepo.findAll(pageable);
    }

    public Page<Groups> listGroups(Pageable pageable) {
        return groupsRepo.findAll(pageable);
    }

    public ResponseWrapper createGroup(String name, String createdby) {
        //check that group does not exist
        if (groupsRepo.countByName(name) > 0) {
            return new ResponseWrapper("failed", "group already exists");
        }
        Groups groups = new Groups();
        groups.setName(name);
        groups.setCreatedby(getUseridfromOptional(createdby));
        groups = groupsRepo.save(groups);
        return new ResponseWrapper("success", "group created", groups);
    }

    public ResponseWrapper mapGroupRole(Long groupid,Long roleid,String createdby){
        if (groupsRepo.findById(groupid).isEmpty() || rolesRepo.findById(roleid).isEmpty()) {
            return new ResponseWrapper("failed", "could not find group or role");
        }
        if (grouprolesRepo.countbyMapping(groupid, roleid) > 0) {
            return new ResponseWrapper("failed", "mapping already exists");
        }

        GroupRoles groupRoles = new GroupRoles();
        groupRoles.setGroupid(groupid);
        groupRoles.setRoleid(roleid);
        groupRoles.setCreatedby(getUseridfromOptional(createdby));
        groupRoles  = grouprolesRepo.save(groupRoles);

        return new ResponseWrapper("success","role mapped to group",groupRoles);
    }


    public ResponseWrapper mapOrganizationGroup(long groupid, long organizationid, String username) {
        if (groupsRepo.findById(groupid).isEmpty() || organizationRepo.findById(organizationid).isEmpty()) {
            return new ResponseWrapper("failed", "could not find group or organization");
        }
        if (organizationgroupsRepo.countbyMapping(groupid, organizationid) > 0) {
            return new ResponseWrapper("failed", "mapping already exists");
        }

        OrganizationGroups organizationGroups = new OrganizationGroups();
        organizationGroups.setGroupid(groupid);
        organizationGroups.setOrganizationid(organizationid);
        organizationGroups.setCreatedby(getUseridfromOptional(username));
        organizationGroups  = organizationgroupsRepo.save(organizationGroups);

        return new ResponseWrapper("success","group mapped to organization",organizationGroups);
    }

    public Page<OrganizationGroups> listOrganizationGroups(Pageable pageable, Optional<Long> organizationid){
        return organizationid.isPresent()?
                organizationgroupsRepo.findAllByOrganizationid(pageable,organizationid.get()): organizationgroupsRepo.findAll(pageable);
    }

    public Page<GroupRoles> listGroupRoles(Pageable pageable){
        return grouprolesRepo.findAll(pageable);
    }
    public ResponseWrapper mapGrouproletoUser(long grouprolesid, long userid, String username) {
        if (grouprolesRepo.findById(grouprolesid).isEmpty() || loadbyUserid(userid).isEmpty()) {
            return new ResponseWrapper("failed", "could not find grouprole or user");
        }
        if (userrolesRepo.countbyUserRole(userid, grouprolesid) > 0) {
            return new ResponseWrapper("failed", "mapping already exists");
        }

        Userroles userroles = new Userroles();
        userroles.setGrouproleid(grouprolesid);
        userroles.setUserid(userid);
        userroles.setCreatedby(getUseridfromOptional(username));
        userroles  = userrolesRepo.save(userroles);

        return new ResponseWrapper("success","role added to user",userroles);
    }
}
