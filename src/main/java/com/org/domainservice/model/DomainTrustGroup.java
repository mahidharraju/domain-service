package com.org.domainservice.model;

import com.org.domainservice.model.id.DomainTrustGroupId;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "domain_trustgroup_department")
@IdClass(DomainTrustGroupId.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "domain_trustgroup_id_seq", initialValue = 1, allocationSize = 100)
@Getter
@Setter
public class DomainTrustGroup {


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domain_trustgroup_id_seq")
  private Long id;
  @Id
  @Column(insertable = false, updatable = false)
  private Long domainId;
  @Id
  @Column(insertable = false, updatable = false)
  private Long deptId;
  private Long tgFlavourId;
  @Id
  @Column(insertable = false, updatable = false)
  private Long orgCollabId;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "domainId")
  private Domain domain;
}
