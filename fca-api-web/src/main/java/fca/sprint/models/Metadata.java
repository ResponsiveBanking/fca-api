package fca.sprint.models;


import java.util.List;

public class Metadata {
  public float getAverageDailySpend() {
    return averageDailySpend;
  }

  public void setAverageDailySpend(float averageDailySpend) {
    this.averageDailySpend = averageDailySpend;
  }

  public float getMaxTransactionAmount() {
    return maxTransactionAmount;
  }

  public void setMaxTransactionAmount(float maxTransactionAmount) {
    this.maxTransactionAmount = maxTransactionAmount;
  }

  public List<String> getSpendingPeriod() {
    return spendingPeriod;
  }

  public void setSpendingPeriod(List<String> spendingPeriod) {
    this.spendingPeriod = spendingPeriod;
  }

  private float averageDailySpend;
  private float maxTransactionAmount;
  private List<String> spendingPeriod;

}
