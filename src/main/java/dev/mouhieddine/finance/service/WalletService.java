package dev.mouhieddine.finance.service;

import dev.mouhieddine.finance.domain.User;
import dev.mouhieddine.finance.domain.Wallet;
import dev.mouhieddine.finance.repository.UserRepository;
import dev.mouhieddine.finance.repository.WalletRepository;
import dev.mouhieddine.finance.security.SecurityUtils;
import dev.mouhieddine.finance.service.dto.WalletDTO;
import dev.mouhieddine.finance.service.mapper.WalletMapper;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Wallet}.
 */
@Service
@Transactional
public class WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletService.class);

    private final WalletRepository walletRepository;

    private final UserRepository userRepository;

    private final WalletMapper walletMapper;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.walletMapper = walletMapper;
    }

    /**
     * Save a wallet.
     *
     * @param walletDTO the entity to save.
     * @return the persisted entity.
     */
    public WalletDTO save(WalletDTO walletDTO) {
        log.debug("Request to save Wallet : {}", walletDTO);
        Wallet wallet = walletMapper.toEntity(walletDTO);
        if (wallet.getOrder() == null) wallet.setOrder(0);
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        wallet.setUser(user);
        wallet = walletRepository.save(wallet);
        return walletMapper.toDto(wallet);
    }

    /**
     * Partially update a wallet.
     *
     * @param walletDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WalletDTO> partialUpdate(WalletDTO walletDTO) {
        log.debug("Request to partially update Wallet : {}", walletDTO);

        return walletRepository
            .findById(walletDTO.getId())
            .map(
                existingWallet -> {
                    walletMapper.partialUpdate(existingWallet, walletDTO);
                    return existingWallet;
                }
            )
            .map(walletRepository::save)
            .map(walletMapper::toDto);
    }

    /**
     * Get all the wallets of the current logged user.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WalletDTO> findByUserIsCurrentUser() {
        log.debug("Request to get all Wallets of the current user");
        return walletRepository
            .findByUserIsCurrentUser()
            .stream()
            .map(walletMapper::toDto)
            .sorted(Comparator.comparing(WalletDTO::getOrder))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the wallets.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WalletDTO> findAll() {
        log.debug("Request to get all Wallets");
        return walletRepository.findAll().stream().map(walletMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one wallet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WalletDTO> findOne(Long id) {
        log.debug("Request to get Wallet : {}", id);
        return walletRepository.findById(id).map(walletMapper::toDto);
    }

    /**
     * Delete the wallet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Wallet : {}", id);
        walletRepository.deleteById(id);
    }
}
