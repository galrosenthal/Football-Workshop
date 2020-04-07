package Domain.Users;

public class Registered extends Roles {
    protected RegisteredTypes type;
    protected String username;
    private String pass;
    private String name;

    public Registered(RegisteredTypes type, String username, String pass, String name) {
        this.type = type;
        this.username = username;
        this.pass = pass;
        this.name = name;
    }

    public boolean login(String usrNm, String pswrd) {
        if (usrNm != this.username || !this.pass.equals(pswrd)) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public RegisteredTypes getType() {
        return type;
    }
}
