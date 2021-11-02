package servlet.manage.order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.ServiceFactory;
import service.IOrderDetailService;
import service.IShapeDetailService;
import service.OrderDetailService;
import service.ShapeDetailService;

@WebServlet("/EditOrder")
public class EditOrderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("edited order");
		OrderDetailService orderDetailService = (OrderDetailService) ServiceFactory.get(IOrderDetailService.class);
		ShapeDetailService shapeDetailService = (ShapeDetailService) ServiceFactory.get(IShapeDetailService.class);
		String line = "";
		String stringBuffer = "";
		JSONObject respJson = new JSONObject();
		
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null) {
				stringBuffer = line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("string bufferd" + stringBuffer);
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject reqJson = (JSONObject) parser.parse(stringBuffer);
			int orderId = orderDetailService.editOrder(reqJson);
			System.out.println(orderId);
			JSONArray listShapeJson = (JSONArray) reqJson.get("listShapeInfo");
			shapeDetailService.manageShape(listShapeJson, orderId);
			
			JSONArray listDeleteShapeJson = (JSONArray) reqJson.get("listDeleteShape");
			shapeDetailService.deleteListShape(listDeleteShapeJson);
//			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		respJson.put("status", "complete request");
		respJson.put("code", 0);
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		PrintWriter out = resp.getWriter();
		out.write(respJson.toString());
		out.flush();
	}

}
