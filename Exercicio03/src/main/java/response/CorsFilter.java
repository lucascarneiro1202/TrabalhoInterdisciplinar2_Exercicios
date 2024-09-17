package response;

import spark.Response;

public class CorsFilter {

    public static Response apply(Response res) {
	    res.header("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
	    res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	    res.header("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept, Origin, Authorization");
	    return res;
    }
}
