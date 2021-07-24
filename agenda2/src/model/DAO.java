package model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  Módulo de conexão *. */
	// Parâmetros de conexão
	private String driver = "com.mysql.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda2?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// Método de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = (Connection) DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;

		}

	}

	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	public void inserirContato(JavaBeans contato) {

		String sql = "INSERT INTO contatos(nome,fone,email) VALUES (?,?,?)";
		try {

			// abrir a conexão com o banco
			Connection con = conectar();
			// Preparar a query para a execução no banco de dados
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

			// Substituir as interrogações(parâmetros) pelo conteúdo das variáveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			// Executando a query
			pst.executeUpdate();

			// Fechar a conexão com o banco
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 *  CRUD READ *.
	 *
	 * @return the array list
	 */

	public ArrayList<JavaBeans> listarContatos() {
		// criando um objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();

		String sql = "SELECT * FROM contatos ORDER BY nome";
		try {
			// conectar ao banco
			Connection con = conectar();
			// preparando a consulta
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
			// armazendando o resultado da consulta no objetor rs
			ResultSet rs = pst.executeQuery();
			// O laço abaixo será executado enquanto houver contatos no banco
			while (rs.next()) {
				// variáveis de apoio que recebem os dados do banco
				String idcon = String.valueOf(rs.getInt(1));
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);

				// populando o ArrayList

				contatos.add(new JavaBeans(idcon, nome, fone, email));
				// fechando a conexão
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
			return null;

		}
		// retornando a lista populada
		return contatos;

	}

	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	public void selecionarContato(JavaBeans contato) {
		//

		String sql = "SELECT * FROM contatos WHERE idcon = ?";

		try {

			// conectar ao banco
			Connection con = conectar();
			// preparando a query
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
			pst.setString(1, contato.getIdcon());
			// executar a query e armazenando o contato selecionado no objeto ResultSet rs
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				// setando as variáveis JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();

		} catch (Exception e) {

			System.out.println(e);
		}

	}

	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	public void alterarContato(JavaBeans contato) {

		String sql = "UPDATE contatos SET  nome = ? , fone = ? , email= ? WHERE idcon = ?";

		try {
			Connection con = conectar();
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
			
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setInt(4, Integer.parseInt(contato.getIdcon()));
			pst.executeUpdate();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	/**
	 * Excluir contato.
	 *
	 * @param contato the contato
	 */
	public void excluirContato(JavaBeans contato) {
		
		String sql = "DELETE  FROM contatos WHERE idcon = ?";
		
		
		
		try {
			
			//conectar ao banco
			Connection con = conectar();
			//preparar a query para ser executada no java
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
			pst.setString(1, contato.getIdcon());
			//pst.execute();
			pst.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	// teste de conexão
	/**
	 * public void testeConexao() { try { Connection con = conectar();
	 * System.out.println(con); con.close();
	 * 
	 * } catch (Exception e) { System.out.println(e); } }
	 **/

}

