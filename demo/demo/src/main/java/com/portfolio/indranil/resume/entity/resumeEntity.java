package com.portfolio.indranil.resume.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "resume")
public class resumeEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="idr", nullable=false, unique=true)
    private Long id;
   
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="resumename", length=Integer.MAX_VALUE, nullable=true)
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Column(name="pick")
	private int pick;


	public int getPick() {
		return pick;
	}

	public void setPick(int pick) {
		this.pick = pick;
	}
	
}
