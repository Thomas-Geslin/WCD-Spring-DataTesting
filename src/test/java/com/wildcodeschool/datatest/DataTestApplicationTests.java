package com.wildcodeschool.datatest;

import com.wildcodeschool.datatest.entity.Fire;
import com.wildcodeschool.datatest.entity.Fireman;
import com.wildcodeschool.datatest.repository.FireRepository;
import com.wildcodeschool.datatest.repository.FiremanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DataTestApplicationTests {

	@Autowired
	FireRepository fireRepository;

	@Autowired
	FiremanRepository firemanRepository;

	@Test
	public void testCreateFire() {
		int severity = 8;
		Instant date = Instant.now();
		var fire = new Fire(severity, date);

		// flush envoie les données instantanément à la base
		// permettant au findById de récuperer les valeurs actualisées
		fireRepository.saveAndFlush(fire);

		Optional<Fire> fromDB = fireRepository.findById(fire.getId());

		assertTrue(fromDB.isPresent());
		assertEquals(fire.getId(), fromDB.get().getId());
		assertEquals(date, fromDB.get().getDate());
		assertEquals(severity, fromDB.get().getSeverity());
	}


	@Test
	public void testLinkFireAndFireman() {
		Fire fire01 = new Fire(7, Instant.now());
		Fire fire02 = new Fire(2, Instant.now());
		Fire fire03 = new Fire(4, Instant.now());

		List<Fire> fireList = new ArrayList<>();
		fireList.add(fire01);
		fireList.add(fire02);
		fireList.add(fire03);

		Fireman fireman = new Fireman("John Doe", fireList);

		fireRepository.saveAndFlush(fire01);
		fireRepository.saveAndFlush(fire02);
		fireRepository.saveAndFlush(fire03);

		firemanRepository.saveAndFlush(fireman);

		Optional<Fireman> firemanDB = firemanRepository.findById(fireman.getId());

		assertTrue(firemanDB.isPresent());
		assertEquals(fireman.getId(), firemanDB.get().getId());
		assertEquals(fireman.getName(), firemanDB.get().getName());
		assertEquals(fireman.getFires(), firemanDB.get().getFires());
	}


	@Test
	public void testVeteranMethod() {
		Fire fire01 = new Fire(7, Instant.now());
		Fire fire02 = new Fire(2, Instant.now());
		Fire fire03 = new Fire(4, Instant.now());

		List<Fire> bigFireList = new ArrayList<>();
		bigFireList.add(fire01);
		bigFireList.add(fire02);
		bigFireList.add(fire03);

		List<Fire> smallFireList = new ArrayList<>();
		smallFireList.add(fire01);
		smallFireList.add(fire02);

		Fireman firemanVeteran = new Fireman("John Doe", bigFireList);
		Fireman fireman02 = new Fireman("John Doe", smallFireList);
		Fireman fireman03 = new Fireman("John Doe");

		Optional<Fireman> veteran = firemanRepository.findById(firemanVeteran.getId());
		Optional<Fireman> testingVeteran = firemanRepository.getVeteran();

		fireRepository.saveAndFlush(fire01);
		fireRepository.saveAndFlush(fire02);
		fireRepository.saveAndFlush(fire03);

		firemanRepository.saveAndFlush(firemanVeteran);
		firemanRepository.saveAndFlush(fireman02);
		firemanRepository.saveAndFlush(fireman03);

		assertEquals(veteran, testingVeteran);
	}


	@Test
	public void testSoloFiremanReturn() {
		Fireman fireman = new Fireman("John Doe");
		firemanRepository.saveAndFlush(fireman);

		Optional<Fireman> firemanDB = firemanRepository.findById(fireman.getId());
		assertEquals(fireman, firemanDB.get());
	}


	@Test
	public void testNoFiremanReturn() {
		var firemanDB = firemanRepository.findById(1L);
		assertTrue(firemanDB.isEmpty());
	}
}
