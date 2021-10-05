package dto;

public class Tool {

    private String toolType;
    private String brand;
    private String toolCode;

    public Tool(String toolType, String brand, String toolCode) {
        this.toolType = toolType;
        this.brand = brand;
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String toString() {
        String toString = "Tool Type: " + toolType;
        toString += ", Brand: " + brand;
        toString += ", Tool Code: " + toolCode;

        return toString;
    }

    public boolean equals(Tool tool) {
        try {
            return toolType == tool.getToolType() &&
                    brand.equals(tool.getBrand()) &&
                    toolCode.equals(tool.getToolCode());
        } catch(NullPointerException e) {
            return false;
        }
    }
}
