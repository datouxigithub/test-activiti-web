package dtx.test.activiti.web.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class HolidayModel implements Serializable{
    private int id,days,status;
    private String reason;
    private Set<ApproverOptionModel> options=new HashSet<>();

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false)
    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Column(nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(nullable = false,columnDefinition = "text")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @OneToMany(mappedBy = "holidayModel",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<ApproverOptionModel> getOptions() {
        return options;
    }

    public void setOptions(Set<ApproverOptionModel> options) {
        this.options = options;
    }
}
