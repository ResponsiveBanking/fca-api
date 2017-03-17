package fca.sprint.controllers;

import com.google.common.collect.Lists;

import fca.sprint.exceptions.NotFoundException;
import fca.sprint.logging.annotations.Log;
import fca.sprint.logging.annotations.Profile;
import fca.sprint.models.AccountInformation;
import fca.sprint.models.ErrorResponse;
import fca.sprint.models.Merchant;
import fca.sprint.models.Metadata;
import fca.sprint.models.Overspend;
import fca.sprint.models.Purchase;
import fca.sprint.models.Transaction;
import fca.sprint.models.TransactionCollection;
import fca.sprint.models.TransactionLock;
import fca.sprint.repositories.MerchantRepository;
import fca.sprint.repositories.TransactionLockRepository;
import fca.sprint.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@Profile
@RestController
@RequestMapping("/transactions")
public class TransactionController {

  private RestTemplate restTemplate;
  private HttpEntity httpEntity;
  private TransactionRepository transactionRepository;
  private TransactionLockRepository transactionLockRepository;
  private MerchantRepository merchantRepository;

  private String accountId = "acc_000098iHM9GGgWiktQ2Ql7";

  @Autowired
  public TransactionController(RestTemplate restTemplate,
                               HttpEntity httpEntity,
                               TransactionRepository transactionRepository,
                               TransactionLockRepository transactionLockRepository,
                               MerchantRepository merchantRepository) {
    this.restTemplate = restTemplate;
    this.httpEntity = httpEntity;
    this.transactionRepository = transactionRepository;
    this.transactionLockRepository = transactionLockRepository;
    this.merchantRepository = merchantRepository;
  }

  @RequestMapping("/{transactionId}")
  public Transaction getTransaction(@PathVariable String transactionId) {
    return transactionRepository.findById(transactionId)
        .orElseThrow(() -> new NotFoundException("Transaction with id: " + transactionId + " was not found"));
  }

  @GetMapping
  public TransactionCollection getTransaction() {
    TransactionCollection transactions = new TransactionCollection();
    List<Transaction> ts = transactionRepository.findAll();
    List<Merchant> m = merchantRepository.findAll();
    transactions.setTransactions(transactionRepository.findAll());
    return transactions;
  }

  @PostMapping
  public ResponseEntity<?> postTransaction() {
    Transaction transaction = new Transaction();
    UUID uuid = UUID.randomUUID();
    transaction.setId(uuid.toString());
    Instant dateNow = Instant.now();
    transaction.setCreated(dateNow);
    transaction.setUpdated(dateNow);
    transaction.setSettled(dateNow);
    transaction.setAmount(-900.00);
    transaction.setDescription("AMAZON");
    transaction.setCategory("entertainment");
    transaction.setCurrency("GBP");
    transaction.setNotes("headphones");
    transaction.setAccount_balance(10577.00 - 5900);
    transaction.setAccount_id("acc_000098iHM9GGgWiktQ2Ql7");
    transaction.setLocal_amount(-900.00);
    transaction.setLocal_currency("GBP");
    transaction.setScheme("gps_mastercard");
    transaction.setDedupe_id("975933302170309197320009864");
    transaction.setIs_load(false);
    transaction.setInclude_in_spending(false);
    transaction.setOriginator(false);
    transaction.setName("Amazon");
    transaction.setLogo("https://www.seeklogo.net/wp-content/uploads/2016/10/amazon-logo-preview.png");
    transactionRepository.save(transaction);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{transactionId}")
        .buildAndExpand(transaction.getId()).toUri();

    return ResponseEntity.created(location).body(transaction);
  }

  @RequestMapping("/metadata")
  public Metadata getMetadata() {
    Metadata metadata = new Metadata();
    List<Transaction> transactions = transactionRepository.findAll();
    metadata.setAverageDailySpend(getAverageDailySpend(transactions));
    List<Double> amounts = transactions.stream().map(Transaction::getAmount).collect(Collectors.toList());
    metadata.setMaxTransactionAmount((Collections.min(amounts).floatValue()));

    Map<LocalTime, List<Transaction>> transactionsByTime = transactions
        .stream()
        .filter(ts -> ts.getAmount() < 0)
        .collect(Collectors.groupingBy(t -> t.getCreated().atZone(ZoneId.systemDefault()).toLocalTime().truncatedTo(ChronoUnit.HOURS)));

    List<String> times = transactionsByTime.entrySet().stream()
        .sorted(Comparator.comparing(t -> t.getValue().size()))
        .map(ts -> ts.getKey().toString())
        .collect(Collectors.toList());

    metadata.setSpendingPeriod(Lists.reverse(times));
    return metadata;
  }

  @RequestMapping("/purchase")
  @PostMapping
  public ResponseEntity<?> initialPurchase(@RequestBody Purchase purchase) {
    List<TransactionLock> locks = transactionLockRepository.findAll();

    for (TransactionLock lock : locks) {
      if (lock.getStartDate().isBefore(Instant.now()) && lock.getEndDate().isAfter(Instant.now())) {
        return ResponseEntity.ok(new ErrorResponse("Transaction lock period is active"));
      }
    }

    AccountInformation accountInformation = new AccountInformation();
    accountInformation.setPurchasePrice(purchase.getPrice());
    accountInformation.setMetadata(getMetadata());
    accountInformation.setCurrentMinimumPayment(5.00);
    accountInformation.setNewMinimumPayment(5.00 + ((purchase.getPrice() * (1.0f / 100.0f))));
    accountInformation.setSavings(0.00);

    return ResponseEntity.ok(accountInformation);
  }

  @RequestMapping("/lock")
  @PostMapping
  public ResponseEntity<?> createLock(@RequestBody TransactionLock lock) {
    return ResponseEntity.ok(transactionLockRepository.save(lock));
  }

  @RequestMapping("/overspend")
  public Overspend overspend() {
    List<Transaction> transactions = transactionRepository.findAll();
    Instant day = Instant.now().minusSeconds(86400);
    List<Transaction> todayTransactions = transactions.stream().filter(t -> t.getCreated().isAfter(day)).collect(Collectors.toList());
    Overspend overspend = new Overspend();
    overspend.setAverageDailySpend(getAverageDailySpend(transactions));
    overspend.setCurrentSpend(todayTransactions.isEmpty() ? 0.00 : getTotalSpend(todayTransactions));
    return overspend;
  }

  private float getTotalSpend(List<Transaction> transactions) {
    float total = 0;
    for (Transaction t : transactions)
      total+=t.getAmount();
    return total;
  }

  private float getAverageDailySpend(List<Transaction> transactions) {
    Map<LocalDate, List<Transaction>> transactionsByDate = transactions
        .stream()
        .filter(ts -> ts.getAmount() < 0)
        .collect(Collectors.groupingBy(t -> t.getCreated().atZone(ZoneId.systemDefault()).toLocalDate()));
    float totalSumAverage = 0;
    for (List<Transaction> t : transactionsByDate.values()) {
      float daySum = 0;
      for (Transaction transaction : t) {
        daySum += transaction.getAmount();
      }
      float averagePerDay = daySum / t.size();
      totalSumAverage += averagePerDay;
    }

    return totalSumAverage / transactionsByDate.size();
  }


  @RequestMapping("/balance")
  public ResponseEntity<?> getBalance() {
    return restTemplate.exchange("https://api.monzo.com/balance?account_id=" + accountId, HttpMethod.GET, httpEntity, Object.class);
  }

}