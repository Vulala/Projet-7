package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "bidList")
public class BidList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bidListId;
	@NotBlank(message = "Account is mandatory")
	private String account;
	@NotBlank(message = "Type is mandatory")
	private String type;
	@Digits(message = "Must be a decimal", fraction = 15, integer = 15)
	private Double bidQuantity;
	@Digits(message = "Must be a decimal", fraction = 15, integer = 15)
	private Double askQuantity;
	@Digits(message = "Must be a decimal", fraction = 15, integer = 15)
	private Double bid;
	@Digits(message = "Must be a decimal", fraction = 15, integer = 15)
	private Double ask;
	private String benchmark;
	private Timestamp bidListDate;
	private String commentary;
	private String security;
	private String status;
	private String trader;
	private String book;
	private String creationName;
	private Timestamp creationDate;
	private String revisionName;
	private Timestamp revisionDate;
	private String dealName;
	private String dealType;
	private String sourceListId;
	private String side;

	public BidList() {
	}

	public BidList(Integer bidListId, @NotBlank(message = "Account is mandatory") String account,
			@NotBlank(message = "Type is mandatory") String type,
			@Digits(message = "Must be a decimal", fraction = 15, integer = 15) Double bidQuantity) {
		this.bidListId = bidListId;
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	public Integer getBidListId() {
		return bidListId;
	}

	public void setBidListId(Integer bidListId) {
		this.bidListId = bidListId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBidQuantity() {
		return bidQuantity;
	}

	public void setBidQuantity(Double bidQuantity) {
		this.bidQuantity = bidQuantity;
	}

	public Double getAskQuantity() {
		return askQuantity;
	}

	public void setAskQuantity(Double askQuantity) {
		this.askQuantity = askQuantity;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public Timestamp getBidListDate() {
		return bidListDate;
	}

	public void setBidListDate(Timestamp bidListDate) {
		this.bidListDate = bidListDate;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getRevisionName() {
		return revisionName;
	}

	public void setRevisionName(String revisionName) {
		this.revisionName = revisionName;
	}

	public Timestamp getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Timestamp revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getSourceListId() {
		return sourceListId;
	}

	public void setSourceListId(String sourceListId) {
		this.sourceListId = sourceListId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

}
