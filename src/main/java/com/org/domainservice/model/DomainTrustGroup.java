package com.org.domainservice.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.org.domainservice.model.id.DomainTrustGroupId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "domain_trustgroup_department")
@IdClass(DomainTrustGroupId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomainTrustGroup {


  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;
  @Id
  @Column(insertable = false, updatable = false)
  private UUID domainId;
  @Id
  @Column(insertable = false, updatable = false)
  private UUID deptId;
  private UUID tgFlavourId;
  @Id
  @Column(insertable = false, updatable = false)
  private UUID orgCollabId;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "domainId")
  private Domain domain;
}
