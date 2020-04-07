package Domain.Users;

public class AssociationRepresentor extends Registered {
    public AssociationRepresentor(String username, String pass, String name) {
        super(RegisteredTypes.ASSOCIATION_REPRESENTOR, username, pass, name);
    }
}
