/*
 * Axelor Business Solutions
 * 
 * Copyright (C) 2005-2021 Axelor (<http://axelor.com>).
 * 
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.contact.db;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "CONTACT_CONTACT", indexes = { @Index(columnList = "name") })
public class Contact extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_CONTACT_SEQ")
	@SequenceGenerator(name = "CONTACT_CONTACT_SEQ", sequenceName = "CONTACT_CONTACT_SEQ", allocationSize = 1)
	private Long id;

	@NotNull
	private String firstName;

	private String lastName;

	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String name;

	@NotNull
	private Long contactNumber = 0L;

	private Integer testint = 0;

	private Boolean testBoolean = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Contact() {
	}

	public Contact(String name) {
		this.name = name;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		try {
			name = computeName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getName()", e);
		}
		return name;
	}

	protected String computeName() {
		if (firstName == null) {
			return null;
		}
		return firstName + " " + lastName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getContactNumber() {
		return contactNumber == null ? 0L : contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Integer getTestint() {
		return testint == null ? 0 : testint;
	}

	public void setTestint(Integer testint) {
		this.testint = testint;
	}

	public Boolean getTestBoolean() {
		return testBoolean == null ? Boolean.FALSE : testBoolean;
	}

	public void setTestBoolean(Boolean testBoolean) {
		this.testBoolean = testBoolean;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof Contact)) return false;

		final Contact other = (Contact) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("firstName", getFirstName())
			.add("lastName", getLastName())
			.add("contactNumber", getContactNumber())
			.add("testint", getTestint())
			.add("testBoolean", getTestBoolean())
			.omitNullValues()
			.toString();
	}
}
