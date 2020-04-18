package Domain.Users;

import Domain.UserComplaints;

import java.util.List;

public class SystemAdmin extends Role {
    private List<UserComplaints> complaintsToReview;
    public SystemAdmin(SystemUser systemUser) {
        super(RoleTypes.SYSTEM_ADMIN,systemUser);
    }
}
