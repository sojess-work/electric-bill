package com.kseb.electricbillingservice.services;

import com.kseb.electricbillingservice.constants.CMConstants;
import com.kseb.electricbillingservice.dto.CMResponse;
import com.kseb.electricbillingservice.dto.PriceSlabDto;
import com.kseb.electricbillingservice.entity.PriceSlab;
import com.kseb.electricbillingservice.repository.PriceSlabRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PriceSlabService {

    @Autowired
    PriceSlabRepository priceSlabRepository;

    /**
     * Method to save price slab information to db
     * @param priceSlabDto
     * @return
     */
    @Transactional
    @CacheEvict(value = "priceSlabs",allEntries = true)
    public CMResponse addNewPriceSlab(PriceSlabDto priceSlabDto){
        log.info("Fetching price slabs with overlapping date range");

        List<PriceSlab> priceSlabInRange = priceSlabRepository.findByDateRangeAndUnitsAndCustomerType(priceSlabDto.getDateFrom(),
                priceSlabDto.getDateTo(),priceSlabDto.getUnitsMinimum(),priceSlabDto.getUnitsMaximum(),priceSlabDto.getCustomerType());

        log.info("Found {} price slabs records with overlapping date range",priceSlabInRange.size());
        if (priceSlabInRange.isEmpty()){
            PriceSlab priceSlab = new PriceSlab();
            BeanUtils.copyProperties(priceSlabDto,priceSlab);
            priceSlabRepository.save(priceSlab);
            return CMResponse.builder()
                    .data(priceSlabDto)
                    .build();
        }else {
            return CMResponse.builder()
                    .isBadRequest(true)
                    .message(CMConstants.OVERLAPPING_DATE_RANGE)
                    .build();
        }
    }
}
