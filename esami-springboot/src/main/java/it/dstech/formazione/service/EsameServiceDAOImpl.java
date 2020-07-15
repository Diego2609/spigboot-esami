package it.dstech.formazione.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.formazione.models.Esame;
import it.dstech.formazione.models.Esito;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.repository.EsameRepository;

@Service
public class EsameServiceDAOImpl implements EsameServiceDAO {
	@Autowired
	private EsameRepository esameRepo;

	@Override
	public Esame add(Esame esame) {
		esame.setListaStudenti(new ArrayList<Utente>());
		esameRepo.save(esame);
		return esame;
	}

	@Override
	public List<Esame> findAll() {

		return esameRepo.findAll();
	}

	@Override
	public void remove(Esame esame) {
		esameRepo.delete(esame);

	}

	@Override
	public Esame edit(Esame esame) {
		esameRepo.save(esame);
		return esame;
	}

	@Override
	public Esame findById(Long Id) {
		return esameRepo.findById(Id).get();
	}

	@Override
	public List<Esame> filtraEsami(List<Esame> listaEsamiSvolti, Long idUtente) {

		Set<Esame> listaEsamiChePuoFare = new HashSet<Esame>();
		List<Esame> listaTuttiEsami = esameRepo.findAll();
		listaTuttiEsami.removeAll(listaEsamiSvolti);
		Map<String, Esame> mappaEsamiBocciati = new HashMap<String, Esame>();

		Collections.sort(listaTuttiEsami, new Comparator<Esame>() {
			public int compare(Esame o1, Esame o2) {
				if (o1.getId() < o2.getId())
					return -1;
				if (o1.getId() > o2.getId())
					return 1;
				else
					return 0;
			}
		});

		for (Esame esame : listaEsamiSvolti) {
			for (Esito esito : esame.getEsito()) {
				if (esito.getUtente().getIdUtente() == idUtente) {
					if (esito.getVoto() < 18)
						mappaEsamiBocciati.put(esame.getMateria(), esame);
				}
			}
		}

		List<Esame> listaEsamiBocciato = new ArrayList<Esame>(mappaEsamiBocciati.values());

		for (Esame esame : listaTuttiEsami) {

			if (listaEsamiBocciato.size() > 0) {
				for (Esame esameBocciato : listaEsamiBocciato) {
					if (esame.getMateria().equals(esameBocciato.getMateria())) {
						listaEsamiChePuoFare
								.addAll(checkEsame(esame.getMateria(), esameBocciato.getId(), listaTuttiEsami));
					} else {
						listaEsamiChePuoFare.add(esame);
					}
				}
			} else
				listaEsamiChePuoFare.add(esame);

		}
		return new ArrayList<Esame>(listaEsamiChePuoFare);
	}

	public List<Esame> checkEsame(String materia, Long idEsameBocciato, List<Esame> lista) {

		List<Esame> esamiMateria = new ArrayList<Esame>();
		for (Esame esame : lista) {
			if (materia.equals(esame.getMateria())) {
				if (esame.getId() > idEsameBocciato)
					esamiMateria.add(esame);
			}
		}

		Collections.sort(esamiMateria, new Comparator<Esame>() {
			public int compare(Esame o1, Esame o2) {
				if (o1.getId() < o2.getId())
					return -1;
				if (o1.getId() > o2.getId())
					return 1;
				else
					return 0;
			}
		});

		if (esamiMateria.size() > 2) {
			esamiMateria.remove(0);
			esamiMateria.remove(0);
			return esamiMateria;
		} else {
			return new ArrayList<Esame>();
		}
	}

	public List<Esame> ordinaPerVoto(List<Esame> listaEsami, Long idUtente) {
		Collections.sort(listaEsami, new Comparator<Esame>() {
			public int compare(Esame o1, Esame o2) {
				for (Esito esito : o1.getEsito()) {
					for (Esito esito2 : o2.getEsito()) {
						if (esito.getUtente().getIdUtente() == idUtente
								&& esito2.getUtente().getIdUtente() == idUtente) {
							if (esito.getVoto() < esito2.getVoto())
								return 1;
							if (esito.getVoto() > esito2.getVoto())
								return -1;

						}
					}
				}
				return 0;
			}
		});
		return listaEsami;

	}
}