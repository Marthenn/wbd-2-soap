package webwbd.model;

import lombok.Getter;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlType;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountWrapper", propOrder = {
        "accounts"
})
public class AccountWrapper {
    private List<Account> accounts;

    public AccountWrapper(List<Account> accounts) {this.accounts = accounts;}
    public AccountWrapper() {}
}
