package yt.item4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import yt.item4.DbConnCtrl;
import yt.item4.bean.Brand;;

public class JdbcBrandDaoImpl implements BrandDao {

	private Connection conn; //TODO static?

	public JdbcBrandDaoImpl() {
		conn = DbConnCtrl.getConnection();
	}

	@Override
	public Brand selectBrandById(int brandId) throws SQLException {
		String query = "SELECT * FROM brands WHERE BrandId= ?";
		Brand brand = new Brand(brandId);

		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, brand.getBrandId());

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {	//set is not empty 
			brand.setBrandName(resultSet.getString("brandName")).setCountry(resultSet.getString("country")).setWebsite(resultSet.getString("website"));
			resultSet.close();
			preparedStatement.close();
			return brand;
		}
		return null;
	}

	@Override
	public List<Brand> readFullBrands() {
		List<Brand> brandList = new ArrayList<Brand>();

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM brands");
			while (resultSet.next()) {
				Brand brand = new Brand(Integer.valueOf(resultSet.getString("brandId")));
				brand.setBrandName(resultSet.getString("brandName")).setCountry(resultSet.getString("country")).setWebsite(resultSet.getString("website"));
				brandList.add(brand);
			}
			resultSet.close();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return brandList;

	}

	@Override
	public boolean deleteBrand(int brandId) throws SQLException {

		String[] elements = { String.valueOf(brandId) };
		return executeStatement("DELETE FROM brands WHERE BrandId=?", elements);

	}

	@Override
	public boolean updateBrand(Brand brand) throws SQLException {

		String[] elements = { brand.getBrandName(), brand.getWebsite(), brand.getCountry(), String.valueOf(brand.getBrandId()) };
		return executeStatement("UPDATE brands SET BrandName=?, Website=?, Country=? WHERE BrandId=?", elements);
	}

	@Override
	public boolean insertBrand(Brand brand) throws SQLException {
		String[] elements = { brand.getBrandName(), brand.getWebsite(), brand.getCountry() };
		return executeStatement("INSERT brands (BrandName, Website, Country) VALUES (?,?,?)", elements);

	}

	private boolean executeStatement(String sqlStatement, String[] dbData) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
		for (int i = 1; i <= dbData.length; i++) {
			preparedStatement.setString(i, dbData[i - 1]);
		}
		int updateStatus = preparedStatement.executeUpdate();

		preparedStatement.close();
		return isUpdateSucceed(updateStatus);
	}

	private boolean isUpdateSucceed(int updateStatus) {
		if (updateStatus == 0) {
			return false;
		}
		return true;
	}

}
