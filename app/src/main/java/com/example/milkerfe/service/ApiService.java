package com.example.milkerfe.service;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.milkerfe.model.User;
import com.example.milkerfe.model.Order;
import com.example.milkerfe.model.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private static final String BASE_URL = "https://milker.onrender.com";
    private RequestQueue requestQueue;

    public ApiService(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void registerUser(User user, Response.Listener<JSONObject> onSuccess, Response.ErrorListener onError) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BASE_URL + "/api/auth/register",
                    user.toJson(),
                    onSuccess,
                    onError
            );
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginUser(User user, Response.Listener<JSONObject> onSuccess, Response.ErrorListener onError) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BASE_URL + "/api/auth/login",
                    user.toLoginJson(),
                    onSuccess,
                    onError
            );
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createOrder(Order order, Response.Listener<JSONObject> onSuccess, Response.ErrorListener onError) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BASE_URL + "/api/orders",
                    order.toJson(),
                    onSuccess,
                    onError
            );
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getProducts(int page, Response.Listener<List<Product>> onSuccess, Response.ErrorListener onError) {
        String url = BASE_URL + "/api/products?page=" + page + "&size=10";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    List<Product> products = new ArrayList<>();
                    try {
                        JSONArray content = response.getJSONArray("content");
                        for (int i = 0; i < content.length(); i++) {
                            JSONObject json = content.getJSONObject(i);
                            Product product = new Product(
                                    json.getString("productId"),
                                    json.getString("productName"),
                                    json.getDouble("price"),
                                    json.getInt("quantity"),
                                    json.optString("image", ""),
                                    json.optString("status", ""),
                                    json.optString("description", "")
                            );
                            products.add(product);
                        }
                        onSuccess.onResponse(products);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                onError
        );
        requestQueue.add(request);
    }
}