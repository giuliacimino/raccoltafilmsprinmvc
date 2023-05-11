package it.prova.raccoltafilmspringmvc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.raccoltafilmspringmvc.model.StatoUtente;
import it.prova.raccoltafilmspringmvc.model.Utente;
import it.prova.raccoltafilmspringmvc.repository.utente.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<Utente> listAllUtenti() {
		return (List<Utente>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return repository.findByIdConRuoli(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Utente utenteInstance) {
		// deve aggiornare solo nome, cognome, username, ruoli
		Utente utenteReloaded = repository.findById(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setNome(utenteInstance.getNome());
		utenteReloaded.setCognome(utenteInstance.getCognome());
		utenteReloaded.setUsername(utenteInstance.getUsername());
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		repository.save(utenteReloaded);
	}

	@Transactional
	public void inserisciNuovo(Utente utenteInstance) {
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setDateCreated(new Date());
		repository.save(utenteInstance);
	}

	@Transactional
	public void rimuovi(Long idToDelete) {
		repository.deleteById(idToDelete);
	}

	@Transactional(readOnly = true)
	public List<Utente> findByExample(Utente example) {
		return repository.findByExample(example);
	}

	@Transactional(readOnly = true)
	public Utente eseguiAccesso(String username, String password) {
		return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Transactional
	public Utente findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	
	@Override
	@Transactional
	public void saveOrUpdate(Utente utente) {
		if (utente.getId() == null) {
			// L'utente non esiste ancora nel database, quindi è necessario inserirlo
			repository.save(utente);
		} else {
			// L'utente esiste già nel database, quindi è necessario aggiornarlo
			Utente existingUtente = repository.findById(utente.getId()).orElse(null);
			if (existingUtente != null) {
				existingUtente.setUsername(utente.getUsername());
				existingUtente.setPassword(utente.getPassword());
				// Imposta qui gli altri campi dell'utente che desideri aggiornare
				repository.save(existingUtente);
			} else {
				throw new RuntimeException("L'utente specificato non esiste nel database.");
			}

		}

	}

	@Override
	@Transactional
	public void setPassword(String vecchiaPassword, String nuovaPassword) {
		String username= SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteFromDb= this.findByUsername(username);
		
		//vedere se esiste l'utente
		if (utenteFromDb== null) {
			throw new RuntimeException("elemento non trovato.");
		}
		
		//vedo se la pwd è uguale
		if (!passwordEncoder.matches(vecchiaPassword, utenteFromDb.getPassword())){
			throw new RuntimeException("le password non matchano.");
		}
		utenteFromDb.setPassword(passwordEncoder.encode(nuovaPassword));
		repository.save(utenteFromDb);
		
		
	}

	@Override
	@Transactional
	public void setPasswordDefault(Long utenteId) {
		Utente utente = repository.findById(utenteId).orElse(null);
		if (utente != null) {
		            String newPassword = "Password@1";
		utente.setPassword(passwordEncoder.encode(newPassword));
		repository.save(utente);
		}
		
	}
	
	
}
