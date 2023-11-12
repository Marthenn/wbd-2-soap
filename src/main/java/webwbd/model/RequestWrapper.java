package webwbd.model;

import lombok.Getter;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestWrapper", propOrder = {
        "requests"
})
public class RequestWrapper {
    private List<Request> requests;

    public RequestWrapper(List<Request> requests) {this.requests = requests;}
    public RequestWrapper() {}
}
