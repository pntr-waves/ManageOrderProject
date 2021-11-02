package servlet.manage.order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.ServiceFactory;
import service.IOrderDetailService;
import service.IShapeDetailService;
import service.OrderDetailService;
import service.ShapeDetailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateOrder")
public class CreateOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("create order");
		String line = null;
		String resultBuffer = "";
		OrderDetailService orderDetailService = (OrderDetailService) ServiceFactory.get(IOrderDetailService.class);
		ShapeDetailService shapeDetailService = (ShapeDetailService) ServiceFactory.get(IShapeDetailService.class);
		JSONObject responseJson = new JSONObject();
		
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null) {
				resultBuffer = line;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("string buffer " + resultBuffer);
		JSONParser parser = new JSONParser();
		JSONObject orderJson = null;
		
		try {
			orderJson = (JSONObject) parser.parse(resultBuffer);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("orderNumber" + (String) orderJson.get("orderNumber"));		
		if (orderJson != null) {
			int id = orderDetailService.addOrder(orderJson);
			System.out.println("new Id" + id);
			if (id != -1) {
				JSONArray listShapeJson = (JSONArray) orderJson.get("listShapeInfo");
				shapeDetailService.addListShape(listShapeJson, id);
				
				responseJson.put("code", 1);
				responseJson.put("Status", "done");
			} else {
				responseJson.put("code", -1);
				responseJson.put("Status", "error");
			}
		}
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		PrintWriter out = resp.getWriter();
		out.write(responseJson.toString());
		out.flush();

	}
}
