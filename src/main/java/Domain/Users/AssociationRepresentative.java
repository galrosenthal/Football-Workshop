package Domain.Users;

public class AssociationRepresentative extends Role {
    public AssociationRepresentative(SystemUser systemUser) {
        super(RoleTypes.ASSOCIATION_REPRESENTATIVE,systemUser);
    }
}
