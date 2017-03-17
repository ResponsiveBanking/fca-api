package fca.sprint.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import fca.sprint.models.Merchant;
import fca.sprint.models.Transaction;
import fca.sprint.repositories.MerchantRepository;
import fca.sprint.repositories.TransactionRepository;

@Configuration
public class DbConfig {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Bean
  public CommandLineRunner populateDatabase(TransactionRepository transactionRepository, MerchantRepository merchantRepository) {
    return (String... args) -> {

      JsonNode rootNode = objectMapper.readTree(DbConfig.class.getResourceAsStream(
          "/transactions.json"));

      List<Transaction> transactions = transactionRepository.save(getTransactionsFromJson(rootNode));
    };
  }

  private List<Transaction> getTransactionsFromJson(JsonNode rootNode) {
    return objectMapper.convertValue(rootNode.path("transactions").elements(),
        new TypeReference<List<Transaction>>() {
        });
  }
}
