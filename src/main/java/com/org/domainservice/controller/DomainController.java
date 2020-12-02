package com.org.domainservice.controller;

import com.org.domainservice.dto.DomainDTO;
import com.org.domainservice.dto.DomainUpdateRequestDTO;
import com.org.domainservice.dto.DomainsResponseDTO;
import com.org.domainservice.exception.GenericAPIException;
import com.org.domainservice.service.IDomainService;
import com.org.domainservice.util.Constants;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


@RestController
public class DomainController {

  private IDomainService domainService;

  public DomainController(IDomainService domainService) {
    this.domainService = domainService;
  }

  @GetMapping("/domains")
  public ResponseEntity<DomainsResponseDTO> getDomains(
      final WebRequest request) {
    String departmentId = isDepartmentDetailsExistsInHeader(request)
        .orElseThrow(() -> new GenericAPIException("Department details missing in header"));
    String organizationId = isOrganizationDetailsExistsInHeader(request)
        .orElseThrow(() -> new GenericAPIException("Organization details missing in header"));
    return ResponseEntity.ok(domainService.getAllDomainsForADepartment(
        Long.parseLong(departmentId),
        Long.parseLong(organizationId)));
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
          Long.parseLong(departmentId),
          Long.parseLong(organizationId));
    } else {
      throw new GenericAPIException("Invalid Request format");
    }
    return ResponseEntity.ok(response);
  }

  private Optional<String> isDepartmentDetailsExistsInHeader(final WebRequest request) {
    return Optional.ofNullable(request.getHeader(Constants.DEPARTMENT_ID));
  }

  private Optional<String> isOrganizationDetailsExistsInHeader(final WebRequest request) {
    return Optional.ofNullable(request.getHeader(Constants.ORG_COLLAB_HEADER));
  }
}
