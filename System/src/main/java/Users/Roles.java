package Users;

public abstract class Roles {
    protected RegisteredTypes type;

    public Roles(RegisteredTypes type) {
        this.type = type;
    }


    public abstract RegisteredTypes getType();
}
