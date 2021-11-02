package connection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import model.order.OrderDetail;

public class OrderDetailDAO {
	
	public void editShape(OrderDetail order) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		String sql = "update \"Order\" set \"orderNumber\" = ?, \"orderDate\" = ?, \"orderDescription\" = ?, \"totalPrice\" = ? where \"id\" = ?";
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, order.getOrderNumber());
			pstm.setDate(2, order.getOrderDate());
			pstm.setString(3, order.getOrderDescription());
			pstm.setDouble(4, order.getTotalPrice());
			pstm.setInt(5, order.getId());
			
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int addOrder(OrderDetail order) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		String sql = "insert into \"Order\"(\"orderNumber\", \"orderDate\", \"orderDescription\", \"totalPrice\") values(?, ?, ?, ?) returning id";
		int newId = -1;
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, order.getOrderNumber());
			pstm.setDate(2, (Date) order.getOrderDate());
			pstm.setString(3, order.getOrderDescription());
			pstm.setDouble(4, order.getTotalPrice());
			ResultSet set = pstm.executeQuery();
			while (set.next()) {
				newId = set.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return newId;
	}
	
	public List<OrderDetail> getListOrderDetail() throws SQLException {
		List<OrderDetail> listOrderDetail = new ArrayList<>();
		Connection connection = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		String sql = "select \"Order\".\"id\", \"orderNumber\", \"orderDate\", \"orderDescription\", \"totalPrice\", count(\"Shape\".\"id\") as totalItem from \"Order\" inner join \"Shape\" on \"Order\".\"id\" = \"Shape\".\"orderId\" group by \"Order\".\"id\"";
		OrderDetail orderDetail = null;
		try {
			pstm = connection.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String orderNumber = rs.getString("orderNumber");
				Date orderDate = rs.getDate("orderDate");
				String orderDescription = rs.getString("orderDescription");
				double totalPrice = rs.getDouble("totalPrice");
				long totalItem = rs.getLong("totalItem");
				orderDetail = new OrderDetail(id, orderNumber, orderDate, orderDescription, totalPrice, totalItem);
				listOrderDetail.add(orderDetail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstm != null) {
				pstm.close();
			}
		}

		return listOrderDetail;
	}
	
	public int deleteOrder(int orderId) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		String sql = "delete from \"Order\" where \"id\" = ?";
		PreparedStatement pstm = null;
		int rowDelete = 0;
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, orderId);
			rowDelete = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowDelete;
	}

	public static void main(String[] args) throws SQLException, ParseException {
		OrderDetailDAO od = new OrderDetailDAO();
		String date = "2021-02-01";
		OrderDetail odd = new OrderDetail(2, "3223", date, "", 3203.3);
		od.editShape(odd);
	}
}
