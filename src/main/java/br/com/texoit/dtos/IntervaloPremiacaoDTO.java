package br.com.texoit.dtos;

public class IntervaloPremiacaoDTO implements Comparable<IntervaloPremiacaoDTO> {

	private String producer;
	private Integer interval;
	private Integer previousWin;
	private Integer followingWin;

	public static int compareIntervalos(IntervaloPremiacaoDTO a, IntervaloPremiacaoDTO b) {
	    return a.interval.compareTo(b.interval);
	  }

	  public String getProducer() {
	    return producer;
	  }

	  public void setProducer(String producer) {
	    this.producer = producer;
	  }

	  public Integer getInterval() {
	    return interval;
	  }

	  public void setInterval(Integer interval) {
	    this.interval = interval;
	  }

	  public Integer getPreviousWin() {
	    return previousWin;
	  }

	  public void setPreviousWin(Integer previousWin) {
	    this.previousWin = previousWin;
	  }

	  public Integer getFollowingWin() {
	    return followingWin;
	  }

	  public void setFollowingWin(Integer followingWin) {
	    this.followingWin = followingWin;
	  }

	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((producer == null) ? 0 : producer.hashCode());
	    return result;
	  }

	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj) {
	      return true;
	    }
	    if (obj == null) {
	      return false;
	    }
	    if (getClass() != obj.getClass()) {
	      return false;
	    }
	    IntervaloPremiacaoDTO other = (IntervaloPremiacaoDTO) obj;
	    if (producer == null) {
	      return other.producer == null;
	    } else {
	      return producer.equals(other.producer);
	    }
	  }


	  @Override
	  public int compareTo(IntervaloPremiacaoDTO o) {
	    return getInterval().compareTo(o.getInterval());
	  }


}
