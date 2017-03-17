package fca.sprint.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import fca.sprint.config.InstantDeserializer;
import fca.sprint.config.InstantSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Transaction {

  @Id
  private String id;
  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = InstantDeserializer.class)
  private Instant created;
  private String description;
  private Double amount;
  private String currency;
  private String notes;
  private Double account_balance;
  private String category;
  private boolean is_load;
  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = InstantDeserializer.class)
  private Instant settled;
  private Double local_amount;
  private String local_currency;
  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = InstantDeserializer.class)
  private Instant updated;
  private String account_id;
  private String scheme;
  private String dedupe_id;
  private boolean originator;
  private boolean include_in_spending;
  private String name;
  private String logo;
  private String suggested_name;
  private String foursquare_category_icon;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getSuggested_name() {
    return suggested_name;
  }

  public void setSuggested_name(String suggested_name) {
    this.suggested_name = suggested_name;
  }

  public String getFoursquare_category_icon() {
    return foursquare_category_icon;
  }

  public void setFoursquare_category_icon(String foursquare_category_icon) {
    this.foursquare_category_icon = foursquare_category_icon;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Instant getCreated() {
    return created;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Double getAccount_balance() {
    return account_balance;
  }

  public void setAccount_balance(Double account_balance) {
    this.account_balance = account_balance;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public boolean isIs_load() {
    return is_load;
  }

  public void setIs_load(boolean is_load) {
    this.is_load = is_load;
  }

  public Instant getSettled() {
    return settled;
  }

  public void setSettled(Instant settled) {
    this.settled = settled;
  }

  public Double getLocal_amount() {
    return local_amount;
  }

  public void setLocal_amount(Double local_amount) {
    this.local_amount = local_amount;
  }

  public String getLocal_currency() {
    return local_currency;
  }

  public void setLocal_currency(String local_currency) {
    this.local_currency = local_currency;
  }

  public Instant getUpdated() {
    return updated;
  }

  public void setUpdated(Instant updated) {
    this.updated = updated;
  }

  public String getAccount_id() {
    return account_id;
  }

  public void setAccount_id(String account_id) {
    this.account_id = account_id;
  }

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getDedupe_id() {
    return dedupe_id;
  }

  public void setDedupe_id(String dedupe_id) {
    this.dedupe_id = dedupe_id;
  }

  public boolean isOriginator() {
    return originator;
  }

  public void setOriginator(boolean originator) {
    this.originator = originator;
  }

  public boolean isInclude_in_spending() {
    return include_in_spending;
  }

  public void setInclude_in_spending(boolean include_in_spending) {
    this.include_in_spending = include_in_spending;
  }

}
