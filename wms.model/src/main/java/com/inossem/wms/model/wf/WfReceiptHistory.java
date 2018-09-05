package com.inossem.wms.model.wf;

import java.util.Date;

public class WfReceiptHistory extends WfReceiptHistoryKey {
	private String receiptUserId;

	private Date createTime;

	private Date modifyTime;

	private String receiptCode;

	public String getReceiptCode() {
		return receiptCode;
	}

	public void setReceiptCode(String receiptCode) {
		this.receiptCode = receiptCode;
	}

	public String getReceiptUserId() {
		return receiptUserId;
	}

	public void setReceiptUserId(String receiptUserId) {
		this.receiptUserId = receiptUserId == null ? null : receiptUserId.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}