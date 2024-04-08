package com.sample.sample.service;

import com.sample.sample.model.Company;
import com.sample.sample.model.Dividend;
import com.sample.sample.model.ScrapedResult;
import com.sample.sample.model.constants.CacheKey;
import com.sample.sample.persist.CompanyRepository;
import com.sample.sample.persist.DividendRepository;
import com.sample.sample.persist.entity.CompanyEntity;
import com.sample.sample.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(value = CacheKey.KEY_FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName) {

        log.info("search company");
        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                                                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다"));


        // 2. 조회된 회사 ID 로 배당금 정보 조회
        List<DividendEntity> dividendEntities = dividendRepository.findAllByCompanyId(company.getId());


        List<Dividend> dividends = dividendEntities.stream()
                                                .map(e -> new Dividend(e.getDate(), e.getDividend()))
                                                .collect(Collectors.toList());


        return new ScrapedResult(new Company(company.getTicker(), company.getName()),
                dividends);
    }

}
