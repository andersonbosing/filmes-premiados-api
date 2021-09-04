package br.com.texoit.dtos;

import io.swagger.annotations.ApiModel;

@ApiModel
public class IntervaloDTO {

	private IntervaloPremiacaoDTO min;
	private IntervaloPremiacaoDTO max;
	public IntervaloPremiacaoDTO getMin() {
		return min;
	}
	public void setMin(IntervaloPremiacaoDTO min) {
		this.min = min;
	}
	public IntervaloPremiacaoDTO getMax() {
		return max;
	}
	public void setMax(IntervaloPremiacaoDTO max) {
		this.max = max;
	}

}
