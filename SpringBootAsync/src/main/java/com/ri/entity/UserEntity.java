package com.ri.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "USER")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
    private String name;
    private String email;
    private String gender;

}
