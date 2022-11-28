package model;

public class ResourceRequest extends Request {
    private String resourceType;
    private int numRequired;


    public ResourceRequest(String requestStatus, String description,
                           String schoolID, String resourceType, int numRequired) {
        super(requestStatus, description, schoolID);
        this.resourceType = resourceType;
        this.numRequired = numRequired;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public int getNumRequired() {
        return numRequired;
    }

    public void setNumRequired(int numRequired) {
        this.numRequired = numRequired;
    }

    @Override
    public String toString() {
        return "ResourceRequest{" +
                "resourceType='" + resourceType + '\'' +
                ", numRequired=" + numRequired +
                '}';
    }
}
