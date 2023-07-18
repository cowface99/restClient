package com.persistent.ebaySailpoint.restClient.Role;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
    private String roleUniqueName;
    private String roleKey;
    private CatalogAttributes catalogAttributes;
    private Attributes attributes;
    private List<String> parentRoles;
    private List<String> accessPolicies;
    private List<String> entityPublication;

    @Getter
    @Setter
    @ToString
    private class CatalogAttributes {
        private String certifiable;
    }

    @Getter
    @Setter
    @ToString
    private class Attributes {
        private String udf_field1;
        private String udf_field2;
    }

    @Getter
    @Setter
    @ToString
    private class RoleMemberships {
        private String userId;
        private String action;
        private String grantStartDate;
        private String grantEndDate;
    }

}
