package Domain.Users;


import java.util.ArrayList;
import java.util.List;


public class SystemUser {

    List<Role> roles;

    protected String username;
//    private String pass;
    private String name;

    public SystemUser(String username,String pass, String name) {
        this(username,name);

        //TODO: Add to database?????
    }

    public SystemUser(String username,String name)
    {
        this.roles = new ArrayList<>();
        this.username = username;
        this.name = name;
    }

//

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void addNewRole(Role role)
    {
        roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public boolean isType(RoleTypes roleType) {
        for(Role role : roles)
        {
            if(role.type.equals(roleType))
            {
                return true;
            }
        }
        return false;
    }

    public Role getRole(RoleTypes roleType)
    {
        for(Role role : roles)
        {
            if(role.type.equals(roleType))
            {
                return role;
            }
        }
        return null;
    }
}
