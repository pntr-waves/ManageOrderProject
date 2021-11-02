package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.shape.Cone;
import model.shape.Cube;
import model.shape.Cylinder;
import model.shape.Shape;
import model.shape.Sphere;

public class ShapeDetailDAO {
	
	public void editShape(Shape shape) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		String sql = "update \"Shape\" set \"property\" = ?::JSON, \"name\" = ?, \"material\" = ? where \"id\" = ?";
		
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, shape.getStringProperty());
			pstm.setString(2, shape.getName());
			pstm.setString(3, shape.getMaterial().getName());
			pstm.setInt(4, shape.getId());
			
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addShape(Shape shape, int orderId) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		String sql = "insert into \"Shape\"(\"orderId\", \"property\", \"name\", \"material\") values(?, ?::JSON, ?, ?)";

		try {
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, orderId);
			pstm.setString(2, shape.getStringProperty());
			pstm.setString(3, shape.getName());
			pstm.setString(4, shape.getMaterial().getName());

			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Shape> getListShapeByOrderId(int id) throws SQLException {
		List<Shape> listShape = new ArrayList<>();
		Connection connection = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		String sql = "select \"id\", \"name\" from \"Shape\" where \"orderId\" = ?";

		try {
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				int shapeId = rs.getInt("id");
				Shape shape = getShape(shapeId, name);
				listShape.add(shape);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstm != null) {
				pstm.close();
			}
		}
		return listShape;
	}

	public int deleteShapeByOrderId(int orderId) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		String sql = "delete from \"Shape\" where \"orderId\" = ?";
		PreparedStatement pstm = null;
		int rowDelete = 0;
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, orderId);
			rowDelete = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstm != null) {
				pstm.close();
			}
		}

		return rowDelete;
	}
	
	public int deleteShapeById(int id) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		String sql = "delete from \"Shape\" where \"id\" = ?";
		PreparedStatement pstm = null;
		int rowDelete = 0;
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, id);
			rowDelete = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstm != null) {
				pstm.close();
			}
		}

		return rowDelete;
	}

	private Shape getShape(int id, String name) throws SQLException {
		String sql = getShapePropertySql(name);
		Connection connection = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		Shape shape = null;
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				switch (name) {
				case "Cone": {
					double height = rs.getDouble("height");
					double radius = rs.getDouble("radius");
					String material = rs.getString("material");
					shape = new Cone(id, material, height, radius);
					break;
				}
				case "Cylinder": {
					double height = rs.getDouble("height");
					double radius = rs.getDouble("radius");
					String material = rs.getString("material");
					shape = new Cone(id, material, height, radius);
					break;
				}
				case "Cube": {
					double edge = rs.getDouble("edge");
					String material = rs.getString("material");
					shape = new Cube(id, material, edge);
				}
				case "Sphere": {
					double radius = rs.getDouble("radius");
					String material = rs.getString("material");
					shape = new Sphere(id, material, radius);
				}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstm != null) {
				pstm.close();
			}
		}
		return shape;
	}

	private String getShapePropertySql(String name) {
		Shape shape = null;
		switch (name) {
		case "Cone": {
			shape = new Cone();
			break;
		}
		case "Cylinder": {
			shape = new Cylinder();
			break;
		}
		case "Cube": {
			shape = new Cube();
			break;
		}
		case "Sphere": {
			shape = new Sphere();
			break;
		}
		}
		List<String> listProperty = shape.getListProperty();
		String subSql = "";
		for (int i = 0; i < listProperty.size(); i++) {
			String separateSql;
			if ((i == listProperty.size() - 1) && listProperty.size() > 1) {
				separateSql = ", ";
			} else {
				separateSql = "";
			}
			subSql += separateSql
					+ String.format("\"property\"->>'%s' as %s", listProperty.get(i), listProperty.get(i));
		}
		String sql = String.format("select %s, \"material\" from \"Shape\" where \"id\" = ?", subSql);

		return sql;
	}

	public static void main(String[] args) throws SQLException {
		ShapeDetailDAO shapeDetailDAO = new ShapeDetailDAO();
		Cone cone = new Cone("Wooden", 3, 4);
		shapeDetailDAO.addShape(cone, 17);
	}
}
