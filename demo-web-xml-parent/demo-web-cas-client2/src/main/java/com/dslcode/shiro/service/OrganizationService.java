package com.dslcode.shiro.service;

import com.dslcode.shiro.entity.Organization;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface OrganizationService {


    Organization createOrganization(Organization organization);
    Organization updateOrganization(Organization organization);
    void deleteOrganization(Long organizationId);

    Organization findOne(Long organizationId);
    List<Organization> findAll();

    Object findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
