package dev.mouhieddine.finance.web.rest;

import dev.mouhieddine.finance.domain.enumeration.TimePeriod;
import dev.mouhieddine.finance.domain.enumeration.TransactionType;
import dev.mouhieddine.finance.repository.TransactionRepository;
import dev.mouhieddine.finance.security.SecurityUtils;
import dev.mouhieddine.finance.service.TransactionService;
import dev.mouhieddine.finance.service.dto.RevenueSourceDto;
import dev.mouhieddine.finance.service.dto.TransactionDTO;
import dev.mouhieddine.finance.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link dev.mouhieddine.finance.domain.Transaction}.
 */
@RestController
@RequestMapping("/api")
public class TransactionResource {

    private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    private static final String ENTITY_NAME = "transaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    public TransactionResource(TransactionService transactionService, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    /**
     * {@code POST  /transactions} : Create a new transaction.
     *
     * @param transactionDTO the transactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionDTO, or with status {@code 400 (Bad Request)} if the transaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transactions")
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) throws URISyntaxException {
        log.debug("REST request to save Transaction : {}", transactionDTO);
        if (transactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new transaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionDTO result = transactionService.save(transactionDTO);
        return ResponseEntity
            .created(new URI("/api/transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transactions/:id} : Updates an existing transaction.
     *
     * @param id             the id of the transactionDTO to save.
     * @param transactionDTO the transactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDTO,
     * or with status {@code 400 (Bad Request)} if the transactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transactions/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransactionDTO transactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Transaction : {}, {}", id, transactionDTO);
        if (transactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (
            !transactionRepository.existsById(id) ||
            !transactionService.findOne(id).get().getWallet().getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin().get())
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransactionDTO result = transactionService.save(transactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transactions/:id} : Partial updates given fields of an existing transaction, field will ignore if it is null
     *
     * @param id             the id of the transactionDTO to save.
     * @param transactionDTO the transactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDTO,
     * or with status {@code 400 (Bad Request)} if the transactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transactions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TransactionDTO> partialUpdateTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransactionDTO transactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transaction partially : {}, {}", id, transactionDTO);
        if (transactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (
            !transactionRepository.existsById(id) ||
            !transactionService.findOne(id).get().getWallet().getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin().get())
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransactionDTO> result = transactionService.partialUpdate(transactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions() {
        log.debug("REST request to get all Transactions");
        return transactionService.findByCurrentUser();
    }

    /**
     * {@code GET  /transactions/:id} : get the "id" transaction.
     *
     * @param id the id of the transactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        log.debug("REST request to get Transaction : {}", id);
        Optional<TransactionDTO> transactionDTO = transactionService.findOne(id);
        if (transactionDTO.isPresent()) {
            if (!transactionDTO.get().getWallet().getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin().get())) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }
        }
        return ResponseUtil.wrapOrNotFound(transactionDTO);
    }

    /**
     * {@code DELETE  /transactions/:id} : delete the "id" transaction.
     *
     * @param id the id of the transactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        log.debug("REST request to delete Transaction : {}", id);
        Optional<TransactionDTO> transactionDTO = transactionService.findOne(id);
        if (transactionDTO.isPresent()) {
            if (!transactionDTO.get().getWallet().getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin().get())) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }
        }
        transactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /transactions/earnings-by-year/:year} : get sum of the all the earnings of the current year
     *
     * @param year the year of interest.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the double value.
     */
    @GetMapping("/transactions/earnings-by-year/{year}")
    public ResponseEntity<Double> getEarningsByYear(@PathVariable int year, TimeZone timeZone) {
        log.debug("REST request to get earnings by year : {}", year);
        return new ResponseEntity<Double>(
            transactionService.findSumByPeriod(TransactionType.INCOME, TimePeriod.YEAR, year, timeZone),
            HttpStatus.OK
        );
    }

    /**
     * {@code GET  /transactions/stats : get sum of the all the earnings/expenses of specified period
     *
     * @param timePeriod the month of interest.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the double value.
     */
    @GetMapping("/transactions/stats")
    public ResponseEntity<Double> getStats(
        @RequestParam TransactionType transactionType,
        @RequestParam TimePeriod timePeriod,
        @RequestParam int periodValue,
        TimeZone timeZone
    ) {
        log.debug("REST request to get earnings by  : {}", timePeriod);
        return new ResponseEntity<Double>(
            transactionService.findSumByPeriod(transactionType, timePeriod, periodValue, timeZone),
            HttpStatus.OK
        );
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("/transactions/incomes-expenses-by-month/{year}")
    public LinkedHashMap<String, LinkedHashMap<String, Double>> getAllIncomesAndExpensesGroupByMonth(@PathVariable int year) {
        log.debug("REST request to get all Transactions");
        return transactionService.findAllIncomesAndExpensesAndGroupByMonth(year);
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("/transactions/expenses-by-category/{year}")
    public LinkedHashMap<String, Double> getAllExpensesGroupByCategory(@PathVariable int year) {
        log.debug("REST request to get all Transactions");
        return transactionService.findAllGroupByCategories(year, TransactionType.EXPENSE);
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("/transactions/incomes-by-category/{year}")
    public LinkedHashMap<String, Double> getAllIncomesGroupByCategory(@PathVariable int year) {
        log.debug("REST request to get all Transactions");
        return transactionService.findAllGroupByCategories(year, TransactionType.INCOME);
    }

    @GetMapping("/transactions/income-overview/{year}")
    public LinkedHashMap<String, Double> getAllIncomesGroupByMonth(@PathVariable int year, TimeZone timeZone) {
        log.debug("REST request to get the income of a year grouped by month");
        return transactionService.getOverviewByTransactionType(TransactionType.INCOME, year, timeZone);
    }

    @GetMapping("/transactions/expenses-overview/{year}")
    public LinkedHashMap<String, Double> getAllExpensesGroupByMonth(@PathVariable int year, TimeZone timeZone) {
        log.debug("REST request to get the expenses of a year grouped by month");
        return transactionService.getOverviewByTransactionType(TransactionType.EXPENSE, year, timeZone);
    }

    @GetMapping("/transactions/revenue-sources/{year}")
    public List<RevenueSourceDto> getRevenueSourceByYear(@PathVariable int year, TimeZone timeZone) {
        log.debug("REST request to get the revenue sources of a year grouped by category");
        return transactionService.getSourcesByTransactionType(TransactionType.INCOME, year, timeZone);
    }

    @GetMapping("/transactions/expenses-sources/{year}")
    public List<RevenueSourceDto> getExpensesSourceByYear(@PathVariable int year, TimeZone timeZone) {
        log.debug("REST request to get the revenue sources of a year grouped by category");
        return transactionService.getSourcesByTransactionType(TransactionType.EXPENSE, year, timeZone);
    }
}
