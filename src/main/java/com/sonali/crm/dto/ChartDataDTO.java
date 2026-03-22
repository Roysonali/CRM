package com.sonali.crm.dto;

import java.util.List;


public class ChartDataDTO {
	
	private List<String> labels;
	private List<Long> data;
	
	public ChartDataDTO(List<String> labels, List<Long> data) {
		super();
		this.labels = labels;
		this.data = data;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<Long> getData() {
		return data;
	}

	public void setData(List<Long> data) {
		this.data = data;
	}
	
	
}
