package zzu.group.www.selfserviceorderingsystem.javabean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private int role;
    public User(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
