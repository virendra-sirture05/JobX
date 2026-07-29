package com.project.referral.dummyclient;

import com.project.referral.client.CompanyClient;
import com.project.referral.common.dto.response.CompanyResponse;
import com.project.referral.common.dto.response.CompanySummaryResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DummyCompanyClient implements CompanyClient {

    @Override
    public CompanyResponse getMyCompany(Long ownerId) {

        CompanyResponse response = new CompanyResponse();

        response.setId(1L);
        response.setOwnerId(ownerId);
        response.setName("Dummy Technologies");
        response.setSlug("dummy-technologies");
        response.setTagline("Building Future");
        response.setDescription("Dummy Company");
        response.setWebsite("https://dummy.com");
        response.setEmail("admin@dummy.com");
        response.setPhone("9999999999");
        response.setVerified(true);
        response.setActive(true);

        return response;
    }

    @Override
    public CompanySummaryResponse getCompanySummaryById(Long id) {

        CompanySummaryResponse summary = new CompanySummaryResponse();

        summary.setId(id);
        summary.setName("Dummy Technologies");
        summary.setSlug("dummy-technologies");
        summary.setTagline("Dummy Tagline");
        summary.setCity("Pune");
        summary.setCountry("India");
        summary.setVerified(true);

        return summary;
    }
}