package core;

import java.util.HashMap;
import java.util.Map;

import service.IOrderDetailService;
import service.IShapeDetailService;
import service.OrderDetailService;
import service.ShapeDetailService;

public class ServiceFactory {
	private static Map<Class<?>, Object> map = new HashMap<>();
	
	static {
		init();
	}
	
	private static void init() {
		register(IOrderDetailService.class, new OrderDetailService());
		register(IShapeDetailService.class, new ShapeDetailService());
	}
	
	public static <T> void register(Class<T> clazz, T t) {
		map.put(clazz, t);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz) {
		return (T) map.get(clazz);
	}

}

