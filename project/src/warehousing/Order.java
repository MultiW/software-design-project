package warehousing;



public class Order {
  private String model;
  private String colour;
  private String frontSku;
  private String backSku;
  private SkuTranslation translationTable;

  /**
   * An order is for a single minivan. Each order should have the model and colour of minivan. Each
   * order was read by FAX. Then the Fax create orders. When each order be created, it should have
   * its frontSKU and backSKU
   * 
   * @param model is model in the order
   * @param colour is colour in the order
   */
  public Order(String model, String colour) {
    translationTable = new SkuTranslation();
    this.model = model;
    this.colour = colour;
    frontSku = translationTable.getFrontSku(model, colour);
    backSku = translationTable.getBackSku(model, colour);
  }

  /**
   * Method used to get order's model.
   */
  public String getModel() {
    return model;
  }

  /**
   * Method used to get order's colour.
   */
  public String getColour() {
    return colour;
  }

  /**
   * Method used to get order's front SKU.
   */
  public String getFrontSku() {
    return frontSku;
  }

  /**
   * Method used to get order's back SKU.
   */
  public String getBackSku() {
    return backSku;
  }

}

