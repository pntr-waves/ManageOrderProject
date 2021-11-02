package servlet.manage.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.ServiceFactory;
import model.order.OrderDetail;
import model.shape.Shape;
import service.IOrderDetailService;
import service.IShapeDetailService;

@WebServlet("/ManageOrder")
public class ManageOrderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// params: ?action=delete&orderId=x;
		IOrderDetailService orderDetailService = ServiceFactory.get(IOrderDetailService.class);
		IShapeDetailService shapeDetailService = ServiceFactory.get(IShapeDetailService.class);
		String action = "";
		if (req.getParameter("action") != null && req.getParameter("action") != "") {
			action = req.getParameter("action");
		}

		switch (action) {
		case "delete": {
			String orderId = req.getParameter("orderId");
			int rowDelete = orderDetailService.deleteOrder(Integer.parseInt(orderId));
			if (rowDelete > 0) {
				JSONObject jsonResp = new JSONObject();
				jsonResp.put("status", "Delete Success!");
				jsonResp.put("statusCode", 1);

				resp.addHeader("Access-Control-Allow-Origin", "*");
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");

				PrintWriter out = resp.getWriter();
				out.write(jsonResp.toString());
				out.flush();
			}
			break;
		}
		default: {
			String orderId = req.getParameter("orderId");
			System.out.println("done");
			JSONArray jsonArray = new JSONArray();
			List<Shape> listShape = shapeDetailService.getListShapeByOrderId(Integer.parseInt(orderId));
			for (Shape s : listShape) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.putAll(s.getMapType());
				jsonObject.putAll(s.getMapProperty());

				jsonArray.add(jsonObject);
			}

			resp.addHeader("Access-Control-Allow-Origin", "*");
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			PrintWriter out = resp.getWriter();
			out.write(jsonArray.toString());
			out.flush();
			break;
		}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IOrderDetailService orderDetailService = ServiceFactory.get(IOrderDetailService.class);
		JSONArray jsonArray = new JSONArray();

		List<OrderDetail> listOrderDetail = orderDetailService.getListOrderDetail();
		for (OrderDetail od : listOrderDetail) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", od.getId());
			jsonObject.put("orderNumber", od.getOrderNumber());
			jsonObject.put("orderDate", od.getOrderDate().toString());
			jsonObject.put("orderDescription", od.getOrderDescription());
			jsonObject.put("totalPrice", od.getTotalPrice());
			jsonObject.put("totalItem", od.getTotalItem());

			jsonArray.add(jsonObject);

		}
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.write(jsonArray.toString());
		out.flush();
	}
}
