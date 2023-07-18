package com.persistent.ebaySailpoint.restClient.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RoleWrapper {
    private List<Role> roles;
}
