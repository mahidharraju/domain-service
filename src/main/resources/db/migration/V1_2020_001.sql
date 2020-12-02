--sudo -u postgres psql
--create database acmeglobaldomain;
--create user domainuser  with encrypted password 'domainuser';
--grant all privileges on database acmeglobaldomain to domainuser;

-- drop table domain_trustgroup_department;
-- drop table domain;
-- drop table flyway_schema_history;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "domain" (
    domain_id uuid NOT NULL,
    name character varying(500) NOT NULL,
    "trust_score" integer NOT NULL,
	relationship character varying(500) NOT NULL,
    "created_date" timestamp without time zone NOT NULL,
    "updated_date" timestamp without time zone,
    "expiry_date" timestamp without time zone,
    "created_by" character varying(500) NOT NULL,
    "updated_by" character varying(500),
	CONSTRAINT "PK_domain_id" PRIMARY KEY ("domain_id")
);


CREATE TABLE domain_trustgroup_department (
    "id" uuid NOT NULL,
	"domain_id" uuid NOT NULL,
   	"tg_flavour_id" uuid NOT NULL,
	"dept_id" uuid NOT NULL,
	"org_collab_id" uuid NOT NULL,
	"created_date" timestamp without time zone NOT NULL,
    "updated_date" timestamp without time zone,
    "expiry_date" timestamp without time zone,
    "created_by" character varying(500) NOT NULL,
    "updated_by" character varying(500),
	CONSTRAINT "PK_Id" PRIMARY KEY ("id"),
	CONSTRAINT "domain_uinque" UNIQUE("id","domain_id","dept_id" , "org_collab_id"),
	CONSTRAINT "FK_domain_id" FOREIGN KEY (domain_id) REFERENCES "domain"(domain_id)
	);
	

/* 

CREATE TABLE collaborationplatform (
    coll_platform_id uuid NOT NULL,
    name character varying(500) NOT NULL,
    image text NOT NULL,
	url text NOT NULL,
    "created_date" timestamp without time zone NOT NULL,
    "updated_date" timestamp without time zone,
    "expiry_date" timestamp without time zone,
    "created_by" character varying(500) NOT NULL,
    "updated_by" character varying(500),
	CONSTRAINT "PK_coll_platform_id" PRIMARY KEY ("coll_platform_id")
);


CREATE TABLE organization (
    org_id uuid NOT NULL,
    name character varying(500) NOT NULL,
    "created_date" timestamp without time zone NOT NULL,
    "updated_date" timestamp without time zone,
    "expiry_date" timestamp without time zone,
    "created_by" character varying(500) NOT NULL,
    "updated_by" character varying(500) NOT NULL,
	CONSTRAINT "PK_org_id" PRIMARY KEY ("org_id")
);
	
	CREATE TABLE domain_trustgroup_department (
    "id" uuid NOT NULL,
	"domain_id" uuid NOT NULL,
    "trust_permission_id" uuid,
	"dept_id" uuid,
	"created_date" timestamp without time zone NOT NULL,
    "updated_date" timestamp without time zone,
    "expiry_date" timestamp without time zone,
    "created_by" character varying(500) NOT NULL,
    "updated_by" character varying(500) NOT NULL,
	CONSTRAINT "PK_domain_trustgroup_department_id" PRIMARY KEY ("id"),
	CONSTRAINT "FK_domain_id" FOREIGN KEY (domain_id) REFERENCES "domain"(domain_id)
	CONSTRAINT "FK_trust_permission_id" FOREIGN KEY (trust_permission_id) REFERENCES trustgroup_permissions(trust_permission_id,trust_group_id,permission_id)
	);
	
	CREATE TABLE organization_collaborators (
    "id" uuid NOT NULL,
	"org_id" uuid NOT NULL,
    "coll_platform_id" uuid,
	"created_date" timestamp without time zone NOT NULL,
    "updated_date" timestamp without time zone,
    "expiry_date" timestamp without time zone,
    "created_by" character varying(500) NOT NULL,
    "updated_by" character varying(500) NOT NULL,
	CONSTRAINT "PK_organization_collaborators_id" PRIMARY KEY ("id"),
	CONSTRAINT "FK_org_id" FOREIGN KEY (org_id) REFERENCES organization(org_id),
	CONSTRAINT "FK_coll_platform_id" FOREIGN KEY (coll_platform_id) REFERENCES collaborationplatform(coll_platform_id)
);
 */





