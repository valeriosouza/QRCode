/**
 * 
 */
package com.example.qrcode;

import java.io.Serializable;

/**
 * @author vagnnermartins
 *
 */
@SuppressWarnings("serial")
public class UsuarioDTO implements Serializable{
	private String nome;
	private String sobrenome;
	private String nascimento;
	
	public UsuarioDTO() {
		super();
	}
	public UsuarioDTO(String nome, String sobrenome, String nascimento) {
		super();
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.nascimento = nascimento;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getNascimento() {
		return nascimento;
	}
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	
}
