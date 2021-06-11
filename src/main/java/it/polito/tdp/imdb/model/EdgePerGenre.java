package it.polito.tdp.imdb.model;

public class EdgePerGenre {
	
	private Actor attore1;
	private Actor attore2;
	private Integer filmInsieme;
	
	public EdgePerGenre(Actor attore1, Actor attore2, Integer filmInsieme) {
		this.attore1 = attore1;
		this.attore2 = attore2;
		this.filmInsieme = filmInsieme;
	}

	public Actor getAttore1() {
		return attore1;
	}

	public void setAttore1(Actor attore1) {
		this.attore1 = attore1;
	}

	public Actor getAttore2() {
		return attore2;
	}

	public void setAttore2(Actor attore2) {
		this.attore2 = attore2;
	}

	public Integer getFilmInsieme() {
		return filmInsieme;
	}
	
	public void setFilmInsieme(Integer filmInsieme) {
		this.filmInsieme = filmInsieme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attore1 == null) ? 0 : attore1.hashCode());
		result = prime * result + ((attore2 == null) ? 0 : attore2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdgePerGenre other = (EdgePerGenre) obj;
		if (attore1 == null) {
			if (other.attore1 != null)
				return false;
		} else if (!attore1.equals(other.attore1))
			return false;
		if (attore2 == null) {
			if (other.attore2 != null)
				return false;
		} else if (!attore2.equals(other.attore2))
			return false;
		return true;
	}

}
