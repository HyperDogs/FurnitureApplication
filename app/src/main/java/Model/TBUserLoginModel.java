package Model;

/**
 * Created by marisalom on 25/11/2017.
 */

public class TBUserLoginModel {
    public static String ulUserLoginId;
    public static String ulName;
    private String ulPass;
    private String ulDesc;
    private String ulGroupId;
    private String ulStatus;
    private String ulEmployeeId;
    private String ulBranchId;
    private String ulSetPermission;
    private String ulRemoteAddr;

    public String getUlUserLoginId() {
        return ulUserLoginId;
    }

    public void setUlUserLoginId(String ulUserLoginId) {
        this.ulUserLoginId = ulUserLoginId;
    }

    public String getUlName() {
        return ulName;
    }

    public void setUlName(String ulName) {
        this.ulName = ulName;
    }

    public String getUlPass() {
        return ulPass;
    }

    public void setUlPass(String ulPass) {
        this.ulPass = ulPass;
    }

    public String getUlDesc() {
        return ulDesc;
    }

    public void setUlDesc(String ulDesc) {
        this.ulDesc = ulDesc;
    }

    public String getUlGroupId() {
        return ulGroupId;
    }

    public void setUlGroupId(String ulGroupId) {
        this.ulGroupId = ulGroupId;
    }

    public String getUlStatus() {
        return ulStatus;
    }

    public void setUlStatus(String ulStatus) {
        this.ulStatus = ulStatus;
    }

    public String getUlEmployeeId() {
        return ulEmployeeId;
    }

    public void setUlEmployeeId(String ulEmployeeId) {
        this.ulEmployeeId = ulEmployeeId;
    }

    public String getUlBranchId() {
        return ulBranchId;
    }

    public void setUlBranchId(String ulBranchId) {
        this.ulBranchId = ulBranchId;
    }

    public String getUlSetPermission() {
        return ulSetPermission;
    }

    public void setUlSetPermission(String ulSetPermission) {
        this.ulSetPermission = ulSetPermission;
    }

    public String getUlRemoteAddr() {
        return ulRemoteAddr;
    }

    public void setUlRemoteAddr(String ulRemoteAddr) {
        this.ulRemoteAddr = ulRemoteAddr;
    }
}
