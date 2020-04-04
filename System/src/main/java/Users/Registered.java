package Users;
import javax.security.enterprise.credential.Password;


public class Registered extends Roles{

    protected String username;
    private Password pass;
    private String name;

    public Registered(RegisteredTypes type, String username, String pass, String name) {
        super(type);
        this.username = username;
        this.pass = new Password(pass);
        this.name = name;
    }

    public boolean login(String usrNm, String pswrd){
        if(usrNm != this.username || !this.pass.compareTo(pswrd))
        {
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
