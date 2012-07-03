package com.proquest.interview.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.proquest.interview.phonebook.PhoneBookImpl;

import junit.framework.TestCase;

public class DataBaseUtilTest extends TestCase {
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DatabaseUtil.initDB();		
	};
	
	@Test
	public void testGetConnection(){
		Connection con = null;
		try {
			con = DatabaseUtil.getConnection();
			assertNotNull(con);
			assertEquals("org.hsqldb.jdbc.JDBCConnection", con.getClass().getName());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			DatabaseUtil.closeResources(null, null, con);
		}
	}
	
	@Test
	public void testResourcesShouldBeCleared(){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DatabaseUtil.getConnection();
			ps = con.prepareStatement("SELECT * FROM PHONEBOOK");
			rs = ps.executeQuery();
			
			assertNotNull(con);
			assertNotNull(ps);
			assertNotNull(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			DatabaseUtil.closeResources(rs, ps, con);
		}
		
		try {
			assertTrue(rs.isClosed());
			assertTrue(ps.isClosed());
			assertTrue(con.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
