package dev.mouhieddine.finance.service;

import dev.mouhieddine.finance.domain.Transaction;
import dev.mouhieddine.finance.domain.enumeration.TimePeriod;
import dev.mouhieddine.finance.domain.enumeration.TransactionType;
import dev.mouhieddine.finance.repository.TransactionRepository;
import dev.mouhieddine.finance.security.SecurityUtils;
import dev.mouhieddine.finance.service.dto.CategoryDTO;
import dev.mouhieddine.finance.service.dto.RevenueSourceDto;
import dev.mouhieddine.finance.service.dto.TransactionDTO;
import dev.mouhieddine.finance.service.dto.WalletDTO;
import dev.mouhieddine.finance.service.mapper.TransactionMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
@Transactional
public class TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final WalletService walletService;

    private final CategoryService categoryService;

    public TransactionService(
        TransactionRepository transactionRepository,
        TransactionMapper transactionMapper,
        WalletService walletService,
        CategoryService categoryService
    ) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.walletService = walletService;
        this.categoryService = categoryService;
    }

    /**
     * Save a transaction.
     *
     * @param transactionDTO the entity to save.
     * @return the persisted entity.
     */
    public TransactionDTO save(TransactionDTO transactionDTO) {
        log.debug("Request to save Transaction : {}", transactionDTO);
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(transaction);
    }

    /**
     * Partially update a transaction.
     *
     * @param transactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransactionDTO> partialUpdate(TransactionDTO transactionDTO) {
        log.debug("Request to partially update Transaction : {}", transactionDTO);

        return transactionRepository
            .findById(transactionDTO.getId())
            .map(
                existingTransaction -> {
                    transactionMapper.partialUpdate(existingTransaction, transactionDTO);
                    return existingTransaction;
                }
            )
            .map(transactionRepository::save)
            .map(transactionMapper::toDto);
    }

    /**
     * Get all the transactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionDTO> findAll() {
        log.debug("Request to get all Transactions");
        return transactionRepository
            .findAll()
            .stream()
            .map(transactionMapper::toDto)
            .map(this::enrichWithDetails)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the transactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Double findSumByPeriod(TransactionType transactionType, TimePeriod timePeriod, int timeValue, TimeZone timeZone) {
        log.debug("Request to get all Transactions");
        return findByCurrentUser()
            .stream()
            .map(this::enrichWithDetails)
            .filter(el -> el.getType() == transactionType)
            .filter(el -> el.getWallet().getCountTotal())
            .filter(el -> el.getCategory() != null)
            .filter(el -> el.getCategory().getShowStatistics())
            .filter(
                el -> {
                    if (timePeriod == TimePeriod.YEAR) {
                        return LocalDate.ofInstant(el.getDate(), timeZone.toZoneId()).getYear() == timeValue;
                    } else {
                        // comparing the month of the transaction to the month required and comparing the year of the Transaction to the current year
                        return (
                            LocalDate.ofInstant(el.getDate(), timeZone.toZoneId()).getMonthValue() == timeValue &&
                            LocalDate.ofInstant(el.getDate(), timeZone.toZoneId()).getYear() ==
                            LocalDate.ofInstant(Instant.now(), timeZone.toZoneId()).getYear()
                        );
                    }
                }
            )
            .reduce(0D, (partialEarning, transactionDTO) -> partialEarning + transactionDTO.getAmount(), Double::sum);
    }

    /**
     * Get all the transactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionDTO> findByCurrentUser() {
        log.debug("Request to get all Transactions");
        return transactionRepository
            .findAll()
            .stream()
            .map(transactionMapper::toDto)
            .map(this::enrichWithDetails)
            .filter(el -> el.getWallet().getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin().get()))
            .sorted((f1, f2) -> f2.getDate().compareTo(f1.getDate()))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one transaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findOne(Long id) {
        log.debug("Request to get Transaction : {}", id);
        return transactionRepository.findById(id).map(transactionMapper::toDto).map(this::enrichWithDetails);
    }

    /**
     * Returns the earnings of the year specified group by month
     *
     * @param year     the year of interest
     * @param timeZone user timeZone
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public LinkedHashMap<String, Double> getOverviewByTransactionType(TransactionType transactionType, int year, TimeZone timeZone) {
        log.debug("Request to get earnings overview for the year : {}", year);

        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        // using linked hashmap because it maintains the order
        LinkedHashMap<String, Double> incomePerMonth = new LinkedHashMap<>();

        // initializing incomes for each month
        Stream.of(months).forEach(month -> incomePerMonth.put(month, 0D));

        findByCurrentUser()
            .stream()
            .filter(
                el ->
                    el.getWallet().getCountTotal() &&
                    el.getCategory() != null &&
                    el.getCategory().getShowStatistics() &&
                    el.getType() == transactionType &&
                    LocalDate.ofInstant(el.getDate(), timeZone.toZoneId()).getYear() == year
            )
            .forEach(
                el -> {
                    // getting the month name
                    String month = LocalDateTime
                        .ofInstant(el.getDate(), timeZone.toZoneId())
                        .getMonth()
                        .getDisplayName(TextStyle.SHORT, Locale.US);
                    // adding current amount to the sum of amounts in the hashmap
                    incomePerMonth.merge(month, el.getAmount(), Double::sum);
                }
            );
        return incomePerMonth;
    }

    /**
     * Returns the earnings of the year specified group by month
     *
     * @param year     the year of interest
     * @param timeZone user timeZone
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public List<RevenueSourceDto> getSourcesByTransactionType(TransactionType transactionType, int year, TimeZone timeZone) {
        log.debug("Request to get revenue sources for the year : {}", year);

        LinkedHashMap<CategoryDTO, Double> sources = new LinkedHashMap<>();

        findByCurrentUser()
            .stream()
            .map(this::enrichWithDetails)
            .filter(
                transactionDTO ->
                    transactionDTO.getWallet().getCountTotal() &&
                    transactionDTO.getCategory() != null &&
                    transactionDTO.getCategory().getShowStatistics() &&
                    LocalDate.ofInstant(transactionDTO.getDate(), timeZone.toZoneId()).getYear() == year &&
                    transactionDTO.getType() == transactionType
            )
            .forEach(transactionDTO -> sources.merge(transactionDTO.getCategory(), transactionDTO.getAmount(), Double::sum));

        List<RevenueSourceDto> sourcesList = new ArrayList<>();

        sources.forEach((key, value) -> sourcesList.add(new RevenueSourceDto(key.getName(), key.getColor(), value)));
        return sourcesList;
    }

    /**
     * Delete the transaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.deleteById(id);
    }

    /**
     * Takes a transactionDto and returns a transactionDto enriched with wallet and category details
     *
     * @param transaction transaction
     * @return transaction enriched with wallet and category details
     */
    public TransactionDTO enrichWithDetails(TransactionDTO transaction) {
        WalletDTO wallet = walletService.findOne(transaction.getWallet().getId()).get();
        if (transaction.getCategory() != null) categoryService
            .findOne(transaction.getCategory().getId())
            .ifPresent(transaction::setCategory);
        transaction.setWallet(wallet);

        return transaction;
    }

    @Transactional(readOnly = true)
    public LinkedHashMap<String, LinkedHashMap<String, Double>> findAllIncomesAndExpensesAndGroupByMonth(int year) {
        log.debug("Request to get all Expenses ");
        List<TransactionDTO> allTransactions = findAll();

        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        LinkedHashMap<String, LinkedHashMap<String, Double>> incomesAndExpenses = new LinkedHashMap<>();

        // expense
        LinkedHashMap<String, Double> expensesByMonth = new LinkedHashMap<>();

        Arrays
            .stream(months)
            .forEach(
                month -> {
                    expensesByMonth.put(month, (double) 0);
                }
            );

        List<TransactionDTO> expenses = allTransactions
            .stream()
            .filter(el -> el.getCategory() != null)
            .filter(el -> el.getType() == TransactionType.EXPENSE)
            .filter(el -> LocalDateTime.ofInstant(el.getDate(), ZoneId.systemDefault()).getYear() == year)
            .collect(Collectors.toList());

        expenses.forEach(
            el -> {
                String month = LocalDateTime
                    .ofInstant(el.getDate(), ZoneId.systemDefault())
                    .getMonth()
                    .getDisplayName(TextStyle.SHORT, Locale.US);
                expensesByMonth.merge(month, el.getAmount(), Double::sum);
            }
        );

        // income
        LinkedHashMap<String, Double> incomesByMonth = new LinkedHashMap<>();
        Arrays
            .stream(months)
            .forEach(
                month -> {
                    incomesByMonth.put(month, (double) 0);
                }
            );

        List<TransactionDTO> incomes = allTransactions
            .stream()
            .filter(el -> el.getCategory() != null)
            .filter(el -> el.getType() == TransactionType.INCOME)
            .filter(el -> LocalDateTime.ofInstant(el.getDate(), ZoneId.systemDefault()).getYear() == year)
            .collect(Collectors.toList());

        incomes.forEach(
            el -> {
                String month = LocalDateTime
                    .ofInstant(el.getDate(), ZoneId.systemDefault())
                    .getMonth()
                    .getDisplayName(TextStyle.SHORT, Locale.US);
                incomesByMonth.merge(month, el.getAmount(), Double::sum);
            }
        );

        incomesAndExpenses.put("incomes", incomesByMonth);
        incomesAndExpenses.put("expenses", expensesByMonth);

        return incomesAndExpenses;
    }

    public LinkedHashMap<String, Double> findAllGroupByCategories(int year, TransactionType type) {
        List<TransactionDTO> allTransactions = findAll()
            .stream()
            .filter(el -> el.getCategory() != null)
            .filter(el -> el.getType() == type)
            .filter(el -> LocalDateTime.ofInstant(el.getDate(), ZoneId.systemDefault()).getYear() == year)
            .collect(Collectors.toList());

        List<String> categories = categoryService.findByUserIsCurrentUser().stream().map(CategoryDTO::getName).collect(Collectors.toList());

        LinkedHashMap<String, Double> transactionsByCategory = new LinkedHashMap<>();

        categories.forEach(cat -> transactionsByCategory.put(cat, (double) 0));

        allTransactions.forEach(
            trn -> {
                transactionsByCategory.merge(trn.getCategory().getName(), trn.getAmount(), Double::sum);
            }
        );

        return transactionsByCategory;
    }
}
