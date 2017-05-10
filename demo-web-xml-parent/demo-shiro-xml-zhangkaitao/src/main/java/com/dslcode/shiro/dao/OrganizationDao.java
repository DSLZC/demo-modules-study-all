package com.dslcode.shiro.dao;


import com.dslcode.shiro.entity.Organization;

import java.util.List;

/**
 * <p>Organization: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface OrganizationDao {

    Organization createOrganization(Organization organization);
    Organization updateOrganization(Organization organization);
    void deleteOrganization(Long organizationId);

    Organization findOne(Long organizationId);
    List<Organization> findAll();

    List<Organization> findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
