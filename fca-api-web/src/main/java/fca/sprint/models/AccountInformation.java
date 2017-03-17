package fca.sprint.models;

public class AccountInformation {

  private double purchasePrice;
  private double currentMinimumPayment;
  private double newMinimumPayment;
  private double savings;
  private Metadata metadata;

  public double getNewMinimumPayment() {
    return newMinimumPayment;
  }

  public void setNewMinimumPayment(double newMinimumPayment) {
    this.newMinimumPayment = newMinimumPayment;
  }

  public double getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public double getCurrentMinimumPayment() {
    return currentMinimumPayment;
  }

  public void setCurrentMinimumPayment(double currentMinimumPayment) {
    this.currentMinimumPayment = currentMinimumPayment;
  }

  public double getSavings() {
    return savings;
  }

  public void setSavings(double savings) {
    this.savings = savings;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }
}
