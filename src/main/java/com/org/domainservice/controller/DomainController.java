package com.org.domainservice.controller;

import com.org.domainservice.dto.DomainsResponseDTO;
import com.org.domainservice.dto.DomainDTO;
import com.org.domainservice.dto.DomainUpdateRequestDTO;
import com.org.domainservice.exception.GenericAPIException;
import com.org.domainservice.service.DomainService;
import com.org.domainservice.util.Constants;
import com.org.domainservice.util.ControllerResponse;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;


@RestController
public class DomainController {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private DomainService domainService;

  @GetMapping("/domains")
  public ResponseEntity<DomainsResponseDTO> getDomains(
      final WebRequest request) {
    try {
      String departmentId = isDepartmentDetailsExistsInHeader(request)
          .orElseThrow(() -> new GenericAPIException("Department details missing in header"));
      String organizationId = isOrganizationDetailsExistsInHeader(request)
          .orElseThrow(() -> new GenericAPIException("Organization details missing in header"));
      return domainService.getAllDomainsForADepartment(
          UUID.fromString(departmentId),
          UUID.fromString(organizationId));
    } catch (Exception e) {
      throw new GenericAPIException("Something went Wrong:: " + e.getLocalizedMessage(), e);
    }
  }

  private Optional<String> isDepartmentDetailsExistsInHeader(final WebRequest request) {
    return Optional.ofNullable(request.getHeader(Constants.DEPARTMENT_ID));
  }

  private Optional<String> isOrganizationDetailsExistsInHeader(final WebRequest request) {
    return Optional.ofNullable(request.getHeader(Constants.ORG_COLLAB_HEADER));
  }

  @PatchMapping("/domains")
  public ResponseEntity<DomainDTO> updateDomain(
      @RequestBody final DomainUpdateRequestDTO domain,
      final WebRequest request) {
    String departmentId = isDepartmentDetailsExistsInHeader(request).orElseThrow(
        () -> new GenericAPIException("Department details missing in header"));
    String organizationId = isOrganizationDetailsExistsInHeader(request).orElseThrow(
        () -> new GenericAPIException("Organization details missing in header"));
    DomainDTO response;
    if (domain != null && domain.getDomainId() != null && domain.getTrustGroupId() != null) {
      response = domainService.updateDomainTrustGroup(domain,
          UUID.fromString(departmentId),
          UUID.fromString(organizationId));
    } else {
      throw new GenericAPIException("Invalid Request format");
    }
    return ControllerResponse.getOkResponseEntity(response);
  }
}
