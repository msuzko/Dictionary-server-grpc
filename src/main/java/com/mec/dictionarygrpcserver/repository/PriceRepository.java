package com.mec.dictionarygrpcserver.repository;

import com.mec.dictionarygrpcserver.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Instrument, Long> {
}