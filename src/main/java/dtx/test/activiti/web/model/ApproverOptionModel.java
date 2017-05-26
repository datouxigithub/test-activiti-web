package dtx.test.activiti.web.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class ApproverOptionModel implements Serializable{
    private int id;
    private String appvoer;
    private String comment;
//    private HolidayModel HolidayModel;
    private Date approlDate;
    private String userFormId;

    @Column(nullable = false)
    public String getUserFormId() {
        return userFormId;
    }

    public void setUserFormId(String userFormId) {
        this.userFormId = userFormId;
    }

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getApprolDate() {
        return approlDate;
    }

    public void setApprolDate(Date approlDate) {
        this.approlDate = approlDate;
    }

//    @JoinColumn(name = "holidayid",referencedColumnName = "id")
//    @ManyToOne(optional = false,cascade = CascadeType.ALL)
//    public HolidayModel getHolidayModel() {
//        return HolidayModel;
//    }
//
//    public void setHolidayModel(HolidayModel HolidayModel) {
//        this.HolidayModel = HolidayModel;
//    }

    @Column(nullable = false)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
