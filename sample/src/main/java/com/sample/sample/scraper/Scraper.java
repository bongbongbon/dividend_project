package com.sample.sample.scraper;

import com.sample.sample.model.Company;
import com.sample.sample.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
