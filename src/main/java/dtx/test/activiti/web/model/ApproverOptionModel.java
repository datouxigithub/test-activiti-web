package dtx.test.activiti.web.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class ApproverOptionModel implements Serializable{
    private int id;
    private String appvoer;
    private String optionx;
    private HolidayModel HolidayModel;

    @JoinColumn(name = "holidayid",referencedColumnName = "id")
    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    public HolidayModel getHolidayModel() {
        return HolidayModel;
    }

    public void setHolidayModel(HolidayModel HolidayModel) {
        this.HolidayModel = HolidayModel;
    }

    @Column(nullable = false)
    public String getOptionx() {
        return optionx;
    }

    public void setOptionx(String optionx) {
        this.optionx = optionx;
    }
    
    
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
    public String getAppvoer() {
        return appvoer;
    }

    public void setAppvoer(String appvoer) {
        this.appvoer = appvoer;
    }
}
